package gr.media24.mSites.tasks.escenic;

import gr.dsigned.atom.domain.AtomCategory;
import gr.dsigned.atom.domain.AtomEntry;
import gr.dsigned.atom.domain.AtomLink;
import gr.media24.mSites.atom.utils.DateUtils;
import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.dao.ArticleRelatedArticleDao;
import gr.media24.mSites.data.dao.AuthorDao;
import gr.media24.mSites.data.dao.CategoryDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.dao.NewspaperDao;
import gr.media24.mSites.data.dao.PhotostoryDao;
import gr.media24.mSites.data.dao.PictureDao;
import gr.media24.mSites.data.dao.SectionDao;
import gr.media24.mSites.data.dao.StoryDao;
import gr.media24.mSites.data.dao.VideoDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.entities.Author;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Newspaper;
import gr.media24.mSites.data.entities.Photostory;
import gr.media24.mSites.data.entities.Picture;
import gr.media24.mSites.data.entities.Section;
import gr.media24.mSites.data.entities.Story;
import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.FeedStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

/**
 * A Massive Rewrite Following The Logic Of The Content API Updater. Present Here Mostly For Historical Purposes
 * @author nk, tk, npapadopoulos
 */
@Component
@Transactional
public class EscenicUpdater {

	private static final Logger logger = Logger.getLogger(EscenicUpdater.class.getName());
	
	private static int maxDepth = 1; //Increase This Value To Read More Articles After An Initialization
	
	@Autowired private EscenicUpdaterFactory updaterFactory;
    @Autowired private FeedDao feedDao;
    @Autowired private ArticleDao articleDao;
    @Autowired private ArticleRelatedArticleDao articleRelatedArticleDao;
    @Autowired private CategoryDao categoryDao;
    @Autowired private StoryDao storyDao;
    @Autowired private VideoDao videoDao;
    @Autowired private AuthorDao authorDao;
    @Autowired private PictureDao pictureDao;
    @Autowired private PhotostoryDao photostoryDao;
    @Autowired private NewspaperDao newspaperDao;
    @Autowired private SectionDao sectionDao;
    
    public void run() {
    	Date starting = new Date();
		int counter = 0;
		List<Feed> feeds = feedDao.getByFeedFlagsAndFeedStatus(Arrays.asList(new FeedFlag[] { FeedFlag.ESCENIC }), FeedStatus.ENABLED);
        for(Feed feed : feeds) {
			if(feed.getCategory()==null) { 
				logger.error("@ECE Every Feed Must Be Related With A Category");
				throw new RuntimeException("Every Feed Must Be Related With A Category");
			}
            try {
                String urlString = feed.getUrl() + feed.getParams() + "&ts=" + new Date().getTime();  //ts Parameter As A Cache-Buster
                logger.info("@ECE Read Feed : " + urlString);
                List<AtomEntry> entries = updaterFactory.getAtomEntries(urlString);
                this.entriesToArticles(entries, feed, 0);
                /*
                 * No Reason To Read Groups For A NEWSPAPER Feed
                 */
            	ArticleType type = getArticleType(entries.get(0));
            	if(!type.equals(ArticleType.NEWSPAPER)) {
            		this.entriesToSections(entries, feed);
            	}
                counter += entries.size();
            }
            catch (Exception exception) {
                logger.error(exception.getMessage(), exception);
            }
        }
		/*
         * Log Time Taken To Complete Successfully
         */
        logger.info("@ECE EscenicUpdater Completed. " + counter + " Entries Read In " + (new Date().getTime()-starting.getTime())/org.apache.commons.lang3.time.DateUtils.MILLIS_PER_SECOND + " seconds");
    }
    
    /**
     * @param entries Articles As Atom Entries
     * @param feed Feed Entity
     * @param depth Depth For Related Articles
     * @return List Of Articles Read From Feed
     * @throws IOException
     * @throws XmlPullParserException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<Article> entriesToArticles(List<AtomEntry> entries, Feed feed, int depth) throws IOException, XmlPullParserException, InstantiationException, IllegalAccessException {
        List<Article> articles = new ArrayList<Article>();
        for(AtomEntry entry : entries) {
        	if(DateUtils.inRange(entry.getPublishedString()) && DateUtils.inRange(entry.getUpdatedString())) { //Valid Published And Updated Date
    	        String eceArticleId = extractArticleEceId(entry);
    	        /*
    	         * There's A Comment On The API Updater Class That Explains Why We Need The newOrUpdated() Method. The Escenic Updater
    	         * Does Not Need Special Consideration For Sections Since They're Handled By The entriesToSections() Method. This May Be
    	         * More Demanding Performance-Wise But In The entriesToArticles() Method We Can Easily 'break' If Article Is Not newOrUpdated
    	         */
    	        boolean newOrUpdated = articleDao.newOrUpdated(eceArticleId, DateUtils.dateToCalendar(entry.getUpdated()));
    	        switch(getArticleType(entry)) {
	                case STORY:
	                	if(!newOrUpdated && depth==0) { //No Changes Since Last Task Execution
	        				break;
	        			}
	                    Story story = (Story) articleDao.articleExists(eceArticleId, Story.class);
	                    story.setEceArticleId(eceArticleId);
	                    if(depth < maxDepth) {
	                        story.addRelatedArticle(getArticleRelatedArticles(story, entry, feed, depth+1));
	                        story.addRelatedArticle(getArticleRelatedPictures(story, entry));
	                    }
	                    if(story.getArticleState()!=null && !story.getArticleState().equals(ArticleState.NEW)) { //Do Not Update Story Fields
	                        storyDao.persistOrMerge(story);
	                        logger.info("@ECE STORY Updated, eceArticleId : " + eceArticleId);
	        				break;
	                    }
	                    story.setArticleType(ArticleType.STORY);
	                    story.setArticleState(ArticleState.NEW);
	                    story.setAuthors(getArticleAuthors(entry));
	                    story.setCategories(getArticleCategories(entry.getCatogories(), feed));
	                    story.setDateLastUpdated(DateUtils.dateToCalendar(entry.getUpdated()));
	                    story.setDatePublished(DateUtils.dateToCalendar(entry.getPublished()));
	                    if(entry.getTitle()!=null && !entry.getTitle().isEmpty()) story.setTitle(entry.getTitle());
	                    if(entry.getSummary()!=null && !entry.getSummary().isEmpty()) story.setLeadText(entry.getSummary());
	                    if(entry.getContent()!=null && !entry.getContent().isEmpty()) story.setBody(entry.getContent());
	                    if(getAlternate(entry)!=null) story.setAlternate(getAlternate(entry));
	                    //No Available Data For Teaser Title, Supertitle, LeadText etc.
	                    storyDao.persistOrMerge(story);
	                    logger.info("@ECE STORY Updated, eceArticleId : " + eceArticleId);
        				articles.add(story);
	                    break;
	                case PHOTOSTORY:
	                	if(!newOrUpdated && depth==0) { //No Changes Since Last Task Execution
	        				break;
	        			}
	                    Photostory photostory = (Photostory) articleDao.articleExists(eceArticleId, Photostory.class);
	                    photostory.setEceArticleId(eceArticleId);
	                    if(depth < maxDepth) {
	                        photostory.addRelatedArticle(getArticleRelatedPictures(photostory, entry));
	                    }
	                    if(photostory.getArticleState()!=null && !photostory.getArticleState().equals(ArticleState.NEW)) { //Do Not Update Photostory Fields
	                        photostoryDao.persistOrMerge(photostory);
	                        logger.info("@ECE PHOTOSTORY Updated, eceArticleId : " + eceArticleId);
	        				break;
	                    }
	                    photostory.setArticleType(ArticleType.PHOTOSTORY);
	        			photostory.setArticleState(ArticleState.NEW);
	        			photostory.setAuthors(getArticleAuthors(entry));
	        			photostory.setCategories(getArticleCategories(entry.getCatogories(), feed));
	        			photostory.setDateLastUpdated(DateUtils.dateToCalendar(entry.getUpdated()));
	                    photostory.setDatePublished(DateUtils.dateToCalendar(entry.getPublished()));
	                    if(entry.getTitle()!=null && !entry.getTitle().isEmpty()) photostory.setTitle(entry.getTitle());
	                    if(entry.getSummary()!=null && !entry.getSummary().isEmpty()) photostory.setLeadText(entry.getSummary());
	                    if(getAlternate(entry)!=null) photostory.setAlternate(getAlternate(entry));
	                    photostoryDao.persistOrMerge(photostory);
	                    logger.info("@ECE PHOTOSTORY Updated, eceArticleId : " + eceArticleId);
	                    articles.add(photostory);
	                    break;
	                case NEWSPAPER:
	            		/*
	            		 * No Relations For NEWSPAPER Articles. Be Careful How You'll Handle The Cover enclosure : NEWSPAPER And The Cover Enclosure 
	            		 * Have The Same eceArticleId So You'll Get A Duplicate Entry Exception If You'll Try To Persist The Cover As A PICTURE
	            		 */
	                	if(!newOrUpdated && depth==0) { //No Changes Since Last Task Execution
	        				break;
	        			}
	                    Newspaper newspaper = (Newspaper) articleDao.articleExists(eceArticleId, Newspaper.class);
	                    if(newspaper.getArticleState()!=null && !newspaper.getArticleState().equals(ArticleState.NEW)) { //Do Not Update Newspaper
	                        break;
	                    }
	                    newspaper.setEceArticleId(eceArticleId);
	                    newspaper.setArticleType(ArticleType.NEWSPAPER);
	                    newspaper.setArticleState(ArticleState.NEW);
	                    newspaper.setAuthors(getArticleAuthors(entry));
	                    newspaper.setCategories(getArticleCategories(entry.getCatogories(), feed));
	                    newspaper.setDateLastUpdated(DateUtils.dateToCalendar(entry.getUpdated()));
	                    newspaper.setDatePublished(DateUtils.dateToCalendar(entry.getPublished()));
	                    if(entry.getTitle()!=null && !entry.getTitle().isEmpty()) newspaper.setTitle(entry.getTitle());
	                    if(getAlternate(entry)!=null) newspaper.setAlternate(getAlternate(entry));
	                    for(AtomLink link : entry.getLinks()) {
	                        if(link.getRel().equalsIgnoreCase("enclosure") && link.getType().equalsIgnoreCase("image/jpeg")) {
	                            newspaper.setLink(link.getHref());
	                            break; //Only One enclosure
	                        }
	                    }
	                    newspaperDao.persistOrMerge(newspaper);
	                    logger.info("@ECE NEWSPAPER Updated, eceArticleId : " + eceArticleId);
	                    articles.add(newspaper);
	                    break;
	                case PICTURE: //PICTURE Articles Are Read Only As Relationships Of Other Types
	                case VIDEO: //Modern Life And Escenic VIDEO Feeds Are Rubbish
	                case ADVERTORIAL: //Escenic Does Not Provide Feeds For ADVERTORIAL Articles
	                default:
	                	break;
	            }
        	}
	        else { //Invalid Published Date
	        	logger.info("@ECE Article Excluded From Processing Due To Invalid Published Date : " + entry.getPublishedString());
	        }
        }
        return articles;
    }    
    
    /**
     * @param entries Articles As Atom Entries
     * @param feed Feed Object Needed To Get Sections' Publication
     * @return HashMap Of Sections And Related Articles Read From Feed 
     */
    public HashMap<Section, List<Article>> entriesToSections(List<AtomEntry> entries, Feed feed) {
        HashMap<String, Section> sectionMap = new HashMap<String, Section>();
        HashMap<Section, List<Article>> sectionArticlesMap = new HashMap<Section, List<Article>>();
        for(AtomEntry entry : entries) {
        	if(DateUtils.inRange(entry.getPublishedString()) && DateUtils.inRange(entry.getUpdatedString())) { //Valid Published And Updated Date
        		String eceArticleId = extractArticleEceId(entry);
	            Article article = articleDao.getByEceArticleId(eceArticleId);
	            for(AtomCategory atomCategory : entry.getCatogories()) {
	                if(atomCategory.getTerm()==null || !atomCategory.getTerm().startsWith("@")) { //We Care Only For Categories With term Starting With '@'
	                    continue;
	                }
	                /*
	                 * Get Section For Map
	                 */
	                String name = feed.getCategory().getSectionUniqueName() + atomCategory.getTerm();
	                Section section = sectionMap.get(name);
	                if(section==null) {
	                	section = sectionDao.sectionExists(name, feed.getCategory().getPublication());
	                    sectionMap.put(name, section);
	                }
	                /*
	                 * Get Articles From sectionArticlesMap
	                 */
	                List<Article> articlesSection = sectionArticlesMap.get(section);
	                if(articlesSection==null) { //If There Are No Articles Initialize
	                    articlesSection = new ArrayList<Article>();
	                }
	                articlesSection.add(article);
	                sectionArticlesMap.put(section, articlesSection);
	            }
        	}
        	else { //Invalid Published Date
        		logger.info("@ECE Article Excluded From Section Processing Due To Invalid Published Date : " + entry.getPublishedString());
        	}
        }
        /*
         * Persist Sections With List<Article> Order
         */
        for(Section section : sectionArticlesMap.keySet()) {
            List<Article> articlesSection = sectionArticlesMap.get(section);
            section.setArticles(articlesSection);
            sectionDao.persistOrMerge(section);
        }
        return sectionArticlesMap;
    }

    /**
     * Extract Article's eceArticleId From Atom Entry's Id Field [ i.e. http://ladylike.gr/3338915 Returns 3338915]
     * @param Atom Entry
     * @return Article's eceArticleId
     */
    public String extractArticleEceId(AtomEntry entry) {
    	return entry.getId().replaceAll(".*/", "");
    }
    
    /**
     * Extract Picture's eceArticleId From A Given URL [ i.e. http://www.sport24.gr/football/article829207.ece/BINARY/w300/ab.jpg Returns 829207 ]
     * @param url Picture's URL
     * @return Picture's eceArticleId
     */
    public String extractPictureEceId(String url) {
        Pattern pattern = Pattern.compile(".*article([0-9]*)\\.ece.*");
        Matcher matcher = pattern.matcher(url);
        if(matcher.matches()) {
            try {
                return matcher.group(1);
            }
            catch(Exception exception) {
            	logger.info("@ECE Can Not Extract pictureEceId From : " + url);
            }
        }
        return "0";
    }    
    
    /**
     * Get Article's Article Given An Atom Entry's Links
     * @param entry Article's Atom Entry
     * @return Article's Alternate
     */
    public String getAlternate(AtomEntry entry) {
        for(AtomLink link : entry.getLinks()) {
        	if(link.getRel().equalsIgnoreCase("alternate")) {
                return link.getHref();
            }
        }
        return null;
    }
      
    /**
     * Get Article's Type Given An Atom Entry. Escenic Feeds Don't Have A type Tag So You Have To Use Your Imagination
     * [Will Never Return ArticleType.PICTURE Because Images Are Only Contained As An Enclosure Within The Atom Feed ]
     * @entry Article As Atom Entry
     * @return Article's ArticleType
     */
    public ArticleType getArticleType(AtomEntry entry) {
        if(entry.getLinks()!=null && entry.getLinks().size() > 0) {
            for(AtomLink link : entry.getLinks()) {
                if(link.getRel().equalsIgnoreCase("via")) {
                    return ArticleType.VIDEO;
                }
                else if(link.getRel().equalsIgnoreCase("enclosure") && link.getHref().contains("/newspapers/")) {
                    return ArticleType.NEWSPAPER;
                }
            }
        }
        if(entry.getCatogories()!=null && entry.getCatogories().size() > 0) {
            for(AtomCategory category : entry.getCatogories()) {
                if(category.getTerm()!=null && (category.getTerm().equalsIgnoreCase("photostories") || category.getTerm().equalsIgnoreCase("thephotostories"))) {
                	return ArticleType.PHOTOSTORY;
                }
            }
        }
        return ArticleType.STORY;
    }    
    
    /**
     * Get Atom Entry's Authors
     * @param Atom Entry
     * @return Set Of Authors
     */
    public Set<Author> getArticleAuthors(AtomEntry entry) {
        Set<Author> result = new HashSet<Author>();
        for(String name : entry.getAuthors()) {
        	if(name!=null && !name.isEmpty()) {
        		Author author = authorDao.authorExists(name);
        		result.add(author);
        	}
        }
        return result;
    }

    /**
     * Transform Atom Entry's Categories To Article Categories
     * @param entryCategories Categories Read For Atom Entry
     * @param feed Feed Object
     * @return List Of Article Categories
     */
    public List<Category> getArticleCategories(List<AtomCategory> entryCategories, Feed feed) {
        List<Category> result = new ArrayList<Category>();
        Category feedCategory = feed.getCategory();
        for(AtomCategory atomCategory : entryCategories) {
            String term = atomCategory.getTerm();
        	String label = (atomCategory.getLabel()!=null && !atomCategory.getLabel().isEmpty()) ? atomCategory.getLabel() : null;
            if(term==null || term.isEmpty()) continue;
            Category category = null;
            if(term.startsWith("@")) {
            	category = categoryDao.categoryExists(feedCategory.getName(), feedCategory.getSectionUniqueName(), term, feedCategory.getPublication());
            }
            else {
            	category = categoryDao.categoryExists(label, term, null, feedCategory.getPublication());
            }
            if(category!=null && !result.contains(category)) {
                result.add(category);
            }
        }
        if(feedCategory!=null && !result.contains(feedCategory)) { //If Feed's Category Is Not Already In Entry's Categories Add It As An Extra One
            result.add(feedCategory);
        }
        return result;
    }
    
    /**
     * Given An Article And The Atom Entry That Represent It, Persist All Story Relations And Add The Relationships To The Returned List Of ArticleRelatedArticles 
     * @param article Article Object
     * @param entry Article's Atom Entry
     * @param feed Feed Object
     * @param depth Current Feed's Loop Depth
     * @return List Of Article's ArticleRelatedArticle Objects
     * @throws IOException
     * @throws XmlPullParserException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<ArticleRelatedArticle> getArticleRelatedArticles(Article article, AtomEntry entry, Feed feed, int depth) throws IOException, XmlPullParserException, InstantiationException, IllegalAccessException {
    	if(depth > maxDepth) {
    		logger.error("@ECE Infinite Loop");
    		throw new RuntimeException("Infinite Loop");
    	}
        List<ArticleRelatedArticle> result = new ArrayList<ArticleRelatedArticle>();
        for(AtomLink link : entry.getLinks()) {
            if(link.getRel().equalsIgnoreCase("related") && link.getType().equalsIgnoreCase("text/html")) {
                String urlString = link.getHref() + feed.getParams();
                List<Article> relatedArticles = new ArrayList<Article>();
                relatedArticles = entriesToArticles(updaterFactory.getAtomEntries(urlString), feed, depth);
                logger.trace("@ECE STORY Relations Updated, eceArticleId : " + article.getEceArticleId() + ", relatedArticleId : " + entry.getId());
                /*
                 * Article-Story Relationship
                 */
            	for(Article relatedArticle : relatedArticles) {
            		ArticleRelatedArticle related = articleRelatedArticleDao.articleRelatedArticleExists(article, relatedArticle, null);
            		result.add(related);
            	}
            }
        }
        return result;
    }
    
    /**
     * Given An Article And The Atom Entry That Represent It, Persist All Picture Relations And Add The Relationships To The Returned List Of ArticleRelatedArticles 
     * @param article Article Object
     * @param entry Article's Atom Entry
     * @return List Of Article's ArticleRelatedArticle Objects
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<ArticleRelatedArticle> getArticleRelatedPictures(Article article, AtomEntry entry) throws InstantiationException, IllegalAccessException {
        List<ArticleRelatedArticle> result = new ArrayList<ArticleRelatedArticle>();
        for(AtomLink link : entry.getLinks()) {
            if(link.getRel().equalsIgnoreCase("enclosure") && link.getType().equalsIgnoreCase("image/jpeg")) {
                Picture picture = (Picture) articleDao.articleExists(extractPictureEceId(link.getHref()), Picture.class);
                if(picture.getAlternate()!=null && !picture.getAlternate().equals(entry.getUrl()) && (!entry.getTitle().equals(picture.getTitle())) || entry.getTitle().trim().isEmpty()) {
                    continue;
                }
                picture.setArticleType(ArticleType.PICTURE);
                picture.setArticleState(ArticleState.NEW);
                picture.setEceArticleId(extractPictureEceId(link.getHref()));
                if(link.getHref()!=null && !link.getHref().isEmpty()) picture.setAlternate(link.getHref());
                if(link.getTitle()!=null && !link.getTitle().isEmpty()) picture.setTitle(link.getTitle()); else picture.setTitle("Related Picture");
                pictureDao.persistOrMerge(picture);
                logger.trace("@ECE PICTURE Relation Updated, ecePictureId : " + picture.getEceArticleId() + ", articleId : " + entry.getId());
                /*
                 * Article-Picture Relationship
                 */
                ArticleRelatedArticle related = articleRelatedArticleDao.articleRelatedArticleExists(article, picture, null); //No Enclosure Comments Available 
                result.add(related);
            }
        }
        return result;
    }

    /**
     * Used For Unit Testing
     * @param updaterFactory
     */
    public void setUpdaterFactory(EscenicUpdaterFactory updaterFactory) {
        this.updaterFactory = updaterFactory;
    }
}