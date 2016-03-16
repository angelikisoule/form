package gr.media24.mSites.core.service.implementation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gr.media24.mSites.core.Settings;
import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.core.service.CategoryService;
import gr.media24.mSites.core.service.NewspaperService;
import gr.media24.mSites.core.tags.ArticleUrlTag;
import gr.media24.mSites.core.utils.NewspaperView;
import gr.media24.mSites.core.utils.ResourceNotFoundException;
import gr.media24.mSites.data.dao.NewspaperDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Newspaper;
import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * Newspapers's Service Implementation
 * Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live
 * @author nk, tk, npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class NewspaperServiceImplementation implements NewspaperService {

	@Autowired private Settings settings;
	@Autowired private CategoryService categoryService;
	@Autowired private ArticleService articleService;
	@Autowired private NewspaperDao newspaperDao;
	
	@Override
	public ModelAndView getNewspaperModelAndView(String uri, String publicationName, boolean isCategory, String datePublished) throws ResourceNotFoundException {
		ModelAndView model = null;
		Map<String, String> newspapersMap = settings.getNewspapersMap();
		List<String> dailyNewspapers = settings.getDailyNewspapers();
		if(!isCategory) { //Article View
			Article article = articleService.getArticleByEceArticleId(articleService.getEceArticleIdFromUri(uri));
			List<Article> newspapers = new ArrayList<Article>();
			/*
			 * 301 Redirects In Case Of Misspelled Article URLs
			 */
			if(!article.getAlternate().contains(uri)) {
        		RedirectView redirectView = new RedirectView();
    			redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
    			redirectView.setUrl(ArticleUrlTag.getArticleUrl(article));
    			model = new ModelAndView(redirectView);
        	}
        	else {
        		model = new ModelAndView();
        		model.setViewName("templates/newspaper"); //Singular
        	}
			/*
			 * Add Newspaper View (Newspaper Article And List Of Articles In The Same Category) To The Model
			 */
			List<String> priorityList = getPriority(uri, isCategory).getList();
			for(String sectionUniqueName : priorityList) {
				List<Article> articlesList = null;
				if(dailyNewspapers.contains(sectionUniqueName.toUpperCase())) {
					articlesList = articleService.getDailyArticlesBySectionUniqueNameArticleType(sectionUniqueName, publicationName, ArticleType.NEWSPAPER, 1);
				}
				else {
					articlesList = articleService.getArticlesBySectionUniqueNameArticleType(sectionUniqueName, publicationName, ArticleType.NEWSPAPER, 1, null);
				}
				if(articlesList!=null) newspapers.addAll(articlesList);
			}
			model.addObject("newspaper", new NewspaperView(article, newspapers));
			model.addObject("recent", getCalendarMap(newspaperDao.recentNewspapers(article.getCategories().get(0)))); //Most Recent Article Of This Newspaper
		}
		else {
			Category category = categoryService.getCategoryBySectionUniqueNamePublicationName(getCategoryUniqueName(uri, isCategory), publicationName);
			Map<Category, List<Article>> newspapers = new LinkedHashMap<Category, List<Article>>();
			List<String> recent = new ArrayList<String>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
			/*
			 * Parse Selected Date Just Once (In Case A Date Is Selected)
			 */
			Calendar fromCalendar = Calendar.getInstance();
			Calendar toCalendar = Calendar.getInstance();
			if(datePublished!=null) { //Date Published Selected
				try {
					fromCalendar.setTime(dateFormat.parse(datePublished));
					fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
					fromCalendar.set(Calendar.MINUTE, 0);
					fromCalendar.set(Calendar.SECOND, 0);
					
					toCalendar.setTime(dateFormat.parse(datePublished));
					toCalendar.set(Calendar.HOUR_OF_DAY, 23);
					toCalendar.set(Calendar.MINUTE, 59);
					toCalendar.set(Calendar.SECOND, 0);
				}
				catch(ParseException exception) {
					throw new ResourceNotFoundException(); //404 Page
				}
			}
			/*
			 * Build A Priorities Map With All Values Of NewspapersMap If The Root Category Requested (/newspapers)
			 * Or Just The Priorities Of A Single Category If A Child Category Requested (i.e. /newspapers/sunday_papers)
			 */
			Map<String, List<String>> prioritiesMap = new LinkedHashMap<String, List<String>>();
			if(uri.equals("/newspapers/") || uri.equals("/newspapers")) { //All Categories
				for(Map.Entry<String, String> entry : newspapersMap.entrySet()) {
				    prioritiesMap.put(entry.getKey(), new Priority(entry.getValue()).getList());
				}
			}
			else { //Only One Category
				prioritiesMap.put(getCategoryUniqueName(uri, isCategory), getPriority(uri, isCategory).getList());
			}
			/*
			 * Iterate Priorities Map And Fetch Newspaper Articles (Or Daily Newspaper Articles) Along With The Published Dates Needed For The View's Calendar
			 */
			for(Map.Entry<String, List<String>> entry : prioritiesMap.entrySet()) {
				Category newspapersKey = categoryService.getCategoryBySectionUniqueNamePublicationName(entry.getKey(), publicationName);
				if(newspapersKey!=null) { //Only If NewspapersMap Key Is An Existing Category
					List<Article> newspapersValue = new ArrayList<Article>();
					for(String sectionUniqueName : entry.getValue()) {
						List<Article> current = new ArrayList<Article>();
						if(datePublished!=null) { //Date Published Selected
							current = articleService.getDateArticlesBySectionUniqueNameArticleType(sectionUniqueName, publicationName, ArticleType.NEWSPAPER, fromCalendar, toCalendar, 1);
						}
						else if(dailyNewspapers.contains(newspapersKey.getSectionUniqueName().toUpperCase())) { //Daily Newspaper Category
							current = articleService.getDailyArticlesBySectionUniqueNameArticleType(sectionUniqueName, publicationName, ArticleType.NEWSPAPER, 1);
						}
						else {
							current = articleService.getArticlesBySectionUniqueNameArticleType(sectionUniqueName, publicationName, ArticleType.NEWSPAPER, 1, null);
						}
						if(current!=null && current.size()>0) { //Add Newspaper To The Result
							newspapersValue.add(current.get(0));
						}
						/*
						 * Get A Number Of Current Newspaper's Previous Editions And Add Their Published Date To The Calendar's Available Dates
						 */
						List<Article> previous = articleService.getArticlesBySectionUniqueNameArticleType(sectionUniqueName, publicationName, ArticleType.NEWSPAPER, 10, null);
						if(previous!=null) { //null Is A Possible Return Result
							for(Article a : previous) {
								String date = dateFormat.format(a.getDatePublished().getTime());
								if(!recent.contains(date)) recent.add(date);
							}
						}
					}
					newspapers.put(newspapersKey, newspapersValue);
				}
			}
			model = new ModelAndView();
			model.addObject("category", category); //This Can Be Null
			model.addObject("selectCategory", getSelectCategoryMap(newspapersMap));
			model.addObject("newspapersMap", newspapers);
			model.addObject("recent", recent);
			model.setViewName("templates/newspapers"); //Plural
		}
        return model;
	}
	
	@Override
	public boolean isNewspaperUrl(String uri) {
		return !categoryService.getSectionUniqueNameFromUri(uri).equals("homepage"); //'homepage' Is The Default Value Returned 
	}
	
	@Override
	@Transactional(readOnly = false)
	public void mergeNewspaper(Newspaper newspaper) {
		//Only Certain Fields Are Exposed To The Editing Form So Get A Fresh Copy, Update Only These Fields And Merge
		Newspaper existing = getNewspaper(newspaper.getId());
		existing.setArticleState(ArticleState.EDIT);
		existing.setTitle(newspaper.getTitle());
		newspaperDao.merge(existing);
	}

	@Override
	public Newspaper getNewspaper(Long id) {
		Newspaper newspaper = newspaperDao.get(id);
		Hibernate.initialize(newspaper.getCategories());
		Hibernate.initialize(newspaper.getAuthors());
		return newspaper;
	}
	
	/**
	 * Get A Map Of Newspaper Categories SectionUniqueNames And Names Given The Newspapers Map Read From The Setting XML File
	 * @param newspapersMap Newspapers Map
	 * @return Map Of SectionUniqueNames And Names
	 */
	private Map<String, String> getSelectCategoryMap(Map<String, String> newspapersMap) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		for(Map.Entry<String, String> entry : newspapersMap.entrySet()) {
			Category category = categoryService.getCategoryBySectionUniqueNamePublicationName(entry.getKey(), settings.getDefaultPublicationName());
			if(category!=null) {
				result.put(category.getSectionUniqueName(), category.getName());
			}
		}
		return result;
	}
	
	/**
	 * Get A Map Of Newspapers' Published Dates An Mobile Site Alternates Given A List Of Newspaper Articles 
	 * @param articles Newspaper Articles
	 * @return Map Of Newspapers' Published Dates And Alternates
	 */
	private Map<String, String> getCalendarMap(List<Article> articles) {
		Map<String, String> result = new HashMap<String, String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
		for(Article article : articles) { //Format Returned Published Dates
			result.put(dateFormat.format(article.getDatePublished().getTime()), ArticleUrlTag.getArticleUrl(article));
		}
		return result;
	}
	
	/**
	 * Get Category's Section Unique Name From The Requested URI Depending On Whether The URI Is A Newspaper Category Or A Newspaper Article 
	 * @param uri Requested URI
	 * @param isCategory TRUE If URI Is A Newspaper Category, FALSE If It Is An Article
	 * @return Category's Section Unique Name
	 */
	private String getCategoryUniqueName(String uri, boolean isCategory) {
		if(isCategory) {
			return categoryService.getSectionUniqueNameFromUri(uri).toUpperCase();
		}
		else {
			return getNewspaperCategory(uri);
		}
	}
	
	/**
	 * Get Parent Category's Section Unique Name From The Newspaper Map, For A Given Newspaper Article URI 
	 * @param uri Requested Newspaper Article URI
	 * @return Category's Section Unique Name
	 */
	private String getNewspaperCategory(String uri) {
		Article article = articleService.getArticleByEceArticleId(articleService.getEceArticleIdFromUri(uri));
		String newspaperKey = null;
		for(Category category : article.getCategories()) {
			String key = category.getSectionUniqueName().toUpperCase();
			if(!StringUtils.isEmpty(settings.getNewspapersMap().get(key))) {
				newspaperKey = key;
				break;
			}
		}
		return newspaperKey;
	}

	/**
	 * Get Newspapers' Priority List From The Newspaper Map (Map Value), Given A Category Section Unique Name (Map's Key) 
	 * @param uri Requested URI
	 * @param isCategory TRUE If URI Is A Newspaper Category, FALSE If It Is An Article
	 * @return Priority Class
	 */
	private Priority getPriority(String uri, boolean isCategory) {
		return new Priority(settings.getNewspapersMap().get(getCategoryUniqueName(uri, isCategory)));
	}
	
	/**
	 * Newspapers' Priority Class
	 * @author nk, tk
	 */
	public static class Priority {

		private String priorityString;

		public Priority(String priorityString) {
			this.priorityString = priorityString;
		}

		public List<String> getList() {
			if(priorityString == null) return Lists.newArrayList();
			Collection<String> collection = Collections2.transform(Arrays.asList(priorityString.split(",")), new Function<String, String>() {
				public String apply(final String input) {
					return input == null ? "" : input.trim();
				}
			});
			return new ArrayList<String>(collection);
		}
	}
}