package gr.media24.mSites.core.service.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import gr.media24.mSites.atom.domain.AtomEntry;
import gr.media24.mSites.core.Settings;
import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.core.service.CategoryService;
import gr.media24.mSites.core.tags.ArticleUrlTag;
import gr.media24.mSites.core.tags.VideoUrlTag;
import gr.media24.mSites.core.utils.CustomException;
import gr.media24.mSites.core.utils.ResourceNotFoundException;
import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.dao.ArticleRelatedArticleDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.entities.Author;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Picture;
import gr.media24.mSites.data.entities.Story;
import gr.media24.mSites.data.entities.Video;
import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.EnclosureComment;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.VideoType;
import gr.media24.mSites.tasks.api.ApiUpdater;
import gr.media24.mSites.tasks.api.ApiUpdaterFactory;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Article's Service Implementation
 * @author npapadopoulos, asoule
 */
@Service
@Transactional(readOnly = true)
public class ArticleServiceImplementation implements ArticleService {

	private static Logger logger = Logger.getLogger(ArticleServiceImplementation.class.getName());
	
	@Autowired private Settings settings;
	@Autowired private ApiUpdaterFactory updaterFactory; 
	@Autowired private ApiUpdater updater;
	@Autowired private ArticleDao articleDao;
	@Autowired private ArticleRelatedArticleDao articleRelatedArticleDao;
	@Autowired private FeedDao feedDao;
	@Autowired private CategoryService categoryService;
	
	@Override
	public Article getArticleByEceArticleId(String eceArticleId) {
		return articleDao.getByEceArticleId(eceArticleId);
	}
	
	@Override
	@Transactional(readOnly = false)
	public ModelAndView getArticleModelAndView(String uri, HttpServletRequest request) throws CustomException, ResourceNotFoundException {
		if(deprecatedStructure(uri) || uri.startsWith("/category/")) throw new ResourceNotFoundException(); //404 Page
		ModelAndView model = null;
		boolean misspelled = false;
		/*
		 * For Now The Only Accepted URIs Ending With .ece Are Some Reserved Articles
		 */
		if(uri.endsWith(".ece")) {
			String reserved = null;			
			for(Map.Entry<String, String> entry : settings.getReservedArticlesMap().entrySet()) {
			    if(uri.toLowerCase().contains(entry.getKey())) {
			    	reserved = entry.getValue();
			    	break; //No Need To Continue
			    }
			}
			if(reserved!=null) {
				model = new ModelAndView();
				model.setViewName("articles/"+reserved);
				return model;
			}
			else {
				throw new CustomException("getArticleModelAndView", uri + " (.ece) Is Not Related To A Reserved Article");
			}
		}
		/*
		 * Fetch Article Or Try To Dynamically Read It From The Feed If It Does Not Exist
		 */
		String eceArticleId = getEceArticleIdFromUri(uri);
		Pattern pattern = Pattern.compile("([0-9]+)"); //eceArticleId Can Only Contain Numeric Characters
	    Matcher matcher = pattern.matcher(eceArticleId);
	    if(!matcher.matches()) throw new ResourceNotFoundException(); //404 Page
	    Article article = articleDao.getByEceArticleId(eceArticleId);
		if(article==null) { //Article Does Not Exist
			String userAgent = request.getHeader("User-Agent");
			if(userAgent!=null && (userAgent.toLowerCase().contains("googlebot") || userAgent.toLowerCase().contains("bingbot"))) { //Yoshimi Battles The Pink Robots
				throw new ResourceNotFoundException(); //404 Page
			}
			try {
				article = readArticleFromFeed(eceArticleId, false);
			}
			catch(Exception exception) {
				logger.trace(exception.getMessage()); //Throw A Custom Exception After Logging The Real One
				throw new CustomException("getArticleModelAndView", "Can Not Dynamically Parse Article With eceArticleId = '" + eceArticleId + "'");
			}
		}
		/*
		 * ARCHIVED Articles Should Lead To An Error Page
		 */
		if(article.getArticleState().equals(ArticleState.ARCHIVED)) throw new CustomException("archived", "Article With eceArticleId '" + eceArticleId + "' Is ARCHIVED");
		/*
		 * 301 Redirects In Case Of Misspelled Article URLs (Exclude ADVANCEDCODE Video Since Their Alternate Holds Just A Link To The Article Feed)
		 */
		if(article.getAlternate()!=null && !article.getAlternate().contains(uri) && !isAdvancedMediaCode(article)) misspelled = true;
		if(misspelled) {
			RedirectView redirectView = new RedirectView();
			redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
			redirectView.setUrl(ArticleUrlTag.getArticleUrl(article));
			model = new ModelAndView(redirectView);
		}
		else {
			/*
			 * Article Relations. Don't Leave The Job To The View, Iterate Over ArticleRelatedArticles And Create Lists Of Relations By ArticleType
			 */
			Hibernate.initialize(article.getCategories());
			Hibernate.initialize(article.getAuthors());
			Hibernate.initialize(article.getTags());
			SortedSet<ArticleRelatedArticle> sortedRelatedArticles = new TreeSet<ArticleRelatedArticle>(new Comparator<ArticleRelatedArticle>() {
	    		public int compare(ArticleRelatedArticle related1, ArticleRelatedArticle related2) {
	    			return Long.valueOf(related1.getId()).compareTo(related2.getId());
	    		}
	    	});
	    	sortedRelatedArticles.addAll(article.getRelatedArticles()); //You Have To Preserve ArticleRelatedArticles Ordering
			List<Story> relatedStories = new ArrayList<Story>();
			List<Picture> relatedPictures = new ArrayList<Picture>();
			List<Video> relatedVideos = new ArrayList<Video>();
			for(ArticleRelatedArticle related : sortedRelatedArticles) {
				switch(related.getRelated().getArticleType()) {
					case STORY:
						relatedStories.add((Story) related.getRelated());
						break;
					case PICTURE:
						relatedPictures.add((Picture) related.getRelated());
						break;
					case VIDEO:
						relatedVideos.add((Video) related.getRelated());
						break;
					default: //Do Nothing
						break;
				}
			}
			model = new ModelAndView();
			model.addObject("article", article);
			switch(article.getArticleType()) {
				case STORY:
					model.addObject("relatedStories", relatedStories);
					model.addObject("relatedPictures", relatedPictures);
					model.addObject("relatedVideos", relatedVideos);
					if(uri.contains("shopping_list")) {
						model.addObject("selectCategory", categoryService.getSelectShoppingMap());
						model.setViewName("templates/product");
					}
					else {
						model.setViewName("templates/story");
					}
					break;
				case PHOTOSTORY:
					model.addObject("relatedPictures", relatedPictures);
					model.setViewName("templates/gallery"); //Do Not Override RedirectView's URL
					break;
				case VIDEO:
					model.addObject("videoId", ((Video) article).getVideoId());
					model.addObject("videoType", ((Video) article).getVideoType());
					model.addObject("embeddedCode", ((Video) article).getEmbeddedCode());
					model.addObject("videoUrl", VideoUrlTag.getVideoUrl(article, true));
					model.setViewName("templates/video"); //Do Not Override RedirectView's URL
					break;
				default:
					throw new CustomException("getArticleModelAndView", "Article With eceArticleId '" + eceArticleId + "' Does Not Have Acceptable ArticleType");
			}
		}
        return model;
	}
	
	@Override
	public List<Article> getArticles(int maxArticles) {
		return articleDao.getAll(maxArticles, 0);
	}
	
	@Override
	public List<Article> getArticles(int maxArticles, int offset) {
		return articleDao.getAll(maxArticles, offset);
	}
	
	@Override
	public Long countArticles() {
		return articleDao.countAll();
	}
	
	@Override
	public List<Article> getArticlesBySectionUniqueName(String sectionUniqueName, String publicationName, int maxArticles, String orderBy) {
		return articleDao.getBySectionUniqueName(sectionUniqueName, publicationName, maxArticles, orderBy);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueName(String sectionUniqueName, String publicationName, int maxArticles, int offset, String orderBy) {
		return articleDao.getBySectionUniqueName(sectionUniqueName, publicationName, maxArticles, offset, orderBy);
	}
	
	@Override
	public Long countArticlesBySectionUniqueName(String sectionUniqueName, String publicationName) {
		return articleDao.countBySectionUniqueName(sectionUniqueName, publicationName);
	}

	@Override
	public List<Article> getArticlesByArticleType(ArticleType articleType, int maxArticles, String orderBy) {
		return articleDao.getByArticleType(articleType, maxArticles, orderBy);
	}

	@Override
	public List<Article> getArticlesByArticleType(List<ArticleType> articleTypes, int maxArticles, String orderBy) {
		return articleDao.getByArticleType(articleTypes, maxArticles, orderBy);
	}
	
	@Override
	public List<Article> getArticlesByArticleType(ArticleType articleType, int maxArticles, int offset, String orderBy) {
		return articleDao.getByArticleType(articleType, maxArticles, offset, orderBy);
	}
	
	@Override
	public List<Article> getArticlesByArticleType(List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy) {
		return articleDao.getByArticleType(articleTypes, maxArticles, offset, orderBy);
	}
	
	@Override
	public Long countArticlesByArticleType(ArticleType articleType) {
		return articleDao.countByArticleType(articleType);
	}
	
	@Override
	public Long countArticlesByArticleType(List<ArticleType> articleTypes) {
		return articleDao.countByArticleType(articleTypes);
	}
	
	@Override
	public List<Article> getArticlesByArticleState(ArticleState articleState, int maxArticles) {
		return articleDao.getByArticleState(articleState, maxArticles);
	}
	
	@Override
	public List<Article> getArticlesByArticleState(ArticleState articleState, int maxArticles, int offset) {
		return articleDao.getByArticleState(articleState, maxArticles, offset);
	}
	
	@Override
	public Long countArticlesByArticleState(ArticleState articleState) {
		return articleDao.countByArticleState(articleState);
	}
	
	@Override
	public List<Article> getArticlesBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName, int maxArticles, String orderBy) {
		return articleDao.getBySectionUniqueNameGroupName(sectionUniqueName, groupName, publicationName, maxArticles, orderBy);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName, int maxArticles, int offset, String orderBy) {
		return articleDao.getBySectionUniqueNameGroupName(sectionUniqueName, groupName, publicationName, maxArticles, offset, orderBy);
	}

	@Override
	public Long countArticlesBySectionUniqueNameGroupName(String sectionUniqueName,	String groupName, String publicationName) {
		return articleDao.countBySectionUniqueNameGroupName(sectionUniqueName, groupName, publicationName);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, String orderBy) {
		return articleDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType, maxArticles, orderBy);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, String orderBy) {
		return articleDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleTypes, maxArticles, orderBy);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, int offset, String orderBy) {
		return articleDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType, maxArticles, offset, orderBy);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy) {
		return articleDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleTypes, maxArticles, offset, orderBy);
	}

	@Override
	public LinkedHashSet<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, boolean lazy) {
		if(lazy) {
			return articleDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType, maxArticles, lazy);
		}
		else { //Just As A Fallback In Case lazy Is Set false
			return new LinkedHashSet<Article>(articleDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType, maxArticles, null));
		}
	}

	@Override
	public LinkedHashSet<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, boolean lazy) {
		if(lazy) {
			return articleDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleTypes, maxArticles, lazy);	
		}
		else { //Just As A Fallback In Case lazy Is Set false
			return new LinkedHashSet<Article>(articleDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleTypes, maxArticles, null));
		}
	}

	@Override
	public Long countArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType) {
		return articleDao.countBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType);
	}

	@Override
	public Long countArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes) {
		return articleDao.countBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleTypes);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType, int maxArticles, String orderBy) {
		return articleDao.getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleType, maxArticles, orderBy);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes, int maxArticles, String orderBy) {
		return articleDao.getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleTypes, maxArticles, orderBy);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType, int maxArticles, int offset, String orderBy) {
		return articleDao.getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleType, maxArticles, offset, orderBy);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy) {
		return articleDao.getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleTypes, maxArticles, offset, orderBy);
	}

	@Override
	public Long countArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType) {
		return articleDao.countBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleType);
	}

	@Override
	public Long countArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes) {
		return articleDao.countBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleTypes);
	}

	@Override
	public List<Article> getArticlesByTagName(String tagName, String publicationName, int maxArticles, String orderBy) {
		return articleDao.getByTagName(tagName, publicationName, maxArticles, orderBy);
	}
	
	@Override
	public List<Article> getArticlesByTagName(String tagName, String publicationName, int maxArticles, int offset, String orderBy) {
		return articleDao.getByTagName(tagName, publicationName, maxArticles, offset, orderBy);
	}

	@Override
	public Long countArticlesByTagName(String tagName, String publicationName) {
		return articleDao.countByTagName(tagName, publicationName);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName, int maxArticles, String orderBy) {
		return articleDao.getBySectionUniqueNameTagName(sectionUniqueName, tagName, publicationName, maxArticles, orderBy);
	}

	@Override
	public List<Article> getArticlesBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName, int maxArticles, int offset, String orderBy) {
		return articleDao.getBySectionUniqueNameTagName(sectionUniqueName, tagName, publicationName, maxArticles, offset, orderBy);
	}
	
	@Override
	public Long countArticlesBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName) {
		return articleDao.countBySectionUniqueNameTagName(sectionUniqueName, tagName, publicationName);
	}

	@Override
	public LinkedHashSet<Article> getArticlesBySection(String sectionName, String publicationName, boolean lazy) {
		return articleDao.getBySection(sectionName, publicationName, lazy);
	}

	@Override
	public List<Article> getDailyArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles) {
		/*
		 * Generate fromCalendar And toCalendar Dates Depending On Current HOUR_OF_DAY
		 */
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Calendar fromCalendar = null;
		Calendar toCalendar = null;
		if(calendar.get(Calendar.HOUR_OF_DAY) > 7) { //After 07:00 We Can Return Today's Articles
			calendar.set(Calendar.HOUR_OF_DAY, 7);
			fromCalendar = calendar;
			toCalendar = (Calendar) calendar.clone();
			toCalendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		else { //Before 07:00 We Should Return Yesterday's Articles
			calendar.set(Calendar.HOUR_OF_DAY, 7);
			toCalendar = calendar;
			fromCalendar = (Calendar) calendar.clone();
			fromCalendar.add(Calendar.DAY_OF_YEAR, -1);
		}
		return articleDao.getDailyBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType, fromCalendar, toCalendar, maxArticles);
	}
	
	@Override
	public List<Article> getDateArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, Calendar fromCalendar, Calendar toCalendar, int maxArticles) {
		return articleDao.getDailyBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType, fromCalendar, toCalendar, maxArticles);
	}
	
	@Override
	public List<String> searchArticlesByAttributesLike(String term) {
		return articleDao.searchByAttributesLike(term);
	}
	
	@Transactional(readOnly = false)
	@Override
	public void archiveArticle(Long id) {
		Article article = articleDao.get(id);
		article.setArticleState(ArticleState.ARCHIVED);
		articleDao.merge(article);		
	}
	
	@Override
	public String getEceArticleIdFromUri(String uri) {
		String result = uri.substring(0, uri.indexOf(".html"));
		result = result.substring(result.lastIndexOf(".") + 1);
		return result;
	}
	
	@Transactional(readOnly = false)
	@Override
	public boolean deleteArticleAuthor(Long articleId, Long authorId) {
		Article article = articleDao.get(articleId);
		Hibernate.initialize(article.getCategories());
		Hibernate.initialize(article.getRelatedArticles());
		Set<Author> authors = article.getAuthors();
		if(authors.size()>1) {
			Iterator<Author> iterator = authors.iterator();
			while(iterator.hasNext()) {
				Author author = iterator.next();
			    if(author.getId().equals(authorId)) {
			        iterator.remove();
			    }
			}
			article.setAuthors(authors); //Minus One
			article.setArticleState(ArticleState.EDIT);
			articleDao.merge(article);
			return true;
		}
		else { //If Article Has Only One Author, Deletion Is Not Allowed
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	@Override
	public void deleteArticleCategory(Long articleId, Long categoryId) {
		Article article = articleDao.get(articleId);
		Hibernate.initialize(article.getAuthors());
		Hibernate.initialize(article.getRelatedArticles());
		List<Category> categories = article.getCategories();
		List<Category> result = new ArrayList<Category>();
		for(Category category : categories) {
			if(!category.getId().equals(categoryId)) {
				result.add(category);
			}
		}
		article.setCategories(result); //Minus One
		article.setArticleState(ArticleState.EDIT);
		articleDao.merge(article);
	}
	
	@Transactional(readOnly = false)
	@Override
	public void deleteArticleRelatedArticle(Long articleId, Long relatedArticleId, String enclosureComment) {
		EnclosureComment comment = (enclosureComment!=null && !enclosureComment.isEmpty()) ? EnclosureComment.valueOf(enclosureComment) : null;
		Article article = articleDao.get(articleId);
		Hibernate.initialize(article.getCategories());
		Hibernate.initialize(article.getAuthors());
		Set<ArticleRelatedArticle> relatedArticles = article.getRelatedArticles();
		Iterator<ArticleRelatedArticle> iterator = relatedArticles.iterator();
		while(iterator.hasNext()) {
			ArticleRelatedArticle relatedArticle = iterator.next();
		    if(relatedArticle.getRelated().getId().equals(relatedArticleId) && relatedArticle.getEnclosureComment().equals(comment)) {
		    	iterator.remove();
		    }
		}
		article.setRelatedArticles(relatedArticles); //Minus One
		article.setArticleState(ArticleState.EDIT);
		articleDao.merge(article);
	}
	
	@Override
	public List<Category> getArticleCategories(String eceArticleId) {
		Article article = getArticleByEceArticleId(eceArticleId);
		Hibernate.initialize(article.getCategories());
		return article.getCategories();
	}
	
	@Override
	public Picture getArticlePictureByCaption(String eceArticleId, String caption) {
		Article article = getArticleByEceArticleId(eceArticleId);
		for(ArticleRelatedArticle related : article.getRelatedArticles()) {
			if(related.getRelated().getArticleType().equals(ArticleType.PICTURE)) {
				Picture picture = (Picture) related.getRelated();
				if(picture.getCaption()!=null && picture.getCaption().equals(caption)) {
					return picture; //Just Return The First One
				}
			}
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public Map<String,String> getPhotostoryPictures(String eceArticleId) {
		Map<String,String> result = new LinkedHashMap<String, String>();
		Article article = getArticleByEceArticleId(eceArticleId);
		if(article==null) {
			try {
				article = readArticleFromFeed(eceArticleId, false);
			}
			catch(Exception exception) {
				logger.error("Can Not Dynamically Parse Article With eceArticleId = '" + eceArticleId + "'");
			}
		}
		for(ArticleRelatedArticle related : article.getRelatedArticles()) {
			Article r = related.getRelated();
			if(r.getArticleType().equals(ArticleType.PICTURE)) {
				result.put(r.getAlternate().replaceAll("/w[0-9]{2,3}/", "/w540/").replace("/BINARY/", "/ALTERNATES/"), ((Picture) r).getCaption()); //Get The ALTERNATE Versions In Order To Have A Fixed Carousel Height
			}
		}
		return result;
	}

	@Override
	public String previousOrNextUrlByDatePublished(Article article, boolean next) {
		return articleDao.previousOrNextByDatePublished(article, next);
	}

	@Override
	public String previousOrNextUrlByEceArticleId(Article article, boolean next) {
		return articleDao.previousOrNextByEceArticleId(article, next);
	}

	@Override
	@Transactional(readOnly = false)
	public Article forcedUpdate(String eceArticleId) {
		Article article = null;
		try {
			article = readArticleFromFeed(eceArticleId, true);
			articleDao.merge(article);
		}
		catch(Exception exception) { //Just Log The Exception
			logger.error("Failed Forced Update Of Article With eceArticleId = '" + eceArticleId + "'");
		}
		return article;
	}

	/**
	 * If A Request For An Article That Does Not Exist Is Made, Try To Avoid The 404 Error By Dynamically Reading The Article From It's Feed 
	 * @param eceArticleId Requested Article's eceArticleId
	 * @param forced true If You Want To Force Update Of Article Without Taking Into Account It's dateLastUpdated Value
	 * @return Article Object
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Article readArticleFromFeed(String eceArticleId, boolean forced) throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
		logger.info((forced ? "Forced Update Of Article, eceArticleId = '" : "A Not Existing Article Requested, eceArticleId = '") + eceArticleId + "'");
		String defaultPublication = settings.getDefaultPublicationName();
		Feed randomFeed = feedDao.getRandomByPublicationNameAndFeedFlags(defaultPublication, Arrays.asList(FeedFlag.API_SECTIONS));
		String articleFeed = updater.getArticleFeed(eceArticleId, randomFeed);
		if(forced) { //Cache-Buster In Case Of Forced Update
			Date buster = new Date();
			articleFeed += "&ts=" + buster.getTime(); 
		}
		List<AtomEntry> entries = updaterFactory.getAtomEntries(articleFeed);
		List<Article> articles = updater.entriesToArticles(entries, randomFeed, 0, forced);
		return articles.get(0); //Only One Result Since We Read Entries Via getSingleResultArticleFeed
	}

	/**
	 * Check If The Given Article Is A Video With Type ADVANCEDCODE
	 * @param article Article Object
	 * @return true If Article Is An ADVANCEDCODE Video, Otherwise false
	 */
	private boolean isAdvancedMediaCode(Article article) {
		if(article.getArticleType().equals(ArticleType.VIDEO)) {
			if(((Video)article).getVideoType().equals(VideoType.ADVANCEDCODE)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Unfortunately Mr. Google Will Be Sending Us A Lot Of The Old Mobile Sites' URIs For A While. There's No Need
	 * To Do All The getArticleModelAndView() Method's Processing. We Can Directly Send The User To The 404 Page.
	 * @param uri Requested URI
	 * @return true If The Requested Article URI Is Deprecated, Otherwise false
	 */
	private boolean deprecatedStructure(String uri) {		
		Pattern pattern = Pattern.compile("\\/article\\/[0-9]{7}\\/");
	    Matcher matcher = pattern.matcher(uri);
	    if(matcher.find()) {
	    	return true;
	    }
		return false;
	}
}
