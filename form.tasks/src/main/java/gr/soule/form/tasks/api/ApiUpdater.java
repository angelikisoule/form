package gr.media24.mSites.tasks.api;

import gr.media24.mSites.atom.domain.AtomCategory;
import gr.media24.mSites.atom.domain.AtomEntry;
import gr.media24.mSites.atom.domain.AtomLink;
import gr.media24.mSites.atom.domain.AtomTag;
import gr.media24.mSites.data.dao.AdvertorialDao;
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
import gr.media24.mSites.data.dao.TagDao;
import gr.media24.mSites.data.dao.VideoDao;
import gr.media24.mSites.data.entities.Advertorial;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Author;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Photostory;
import gr.media24.mSites.data.entities.Picture;
import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.entities.Section;
import gr.media24.mSites.data.entities.Story;
import gr.media24.mSites.data.entities.Tag;
import gr.media24.mSites.data.entities.Video;
import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.EnclosureComment;
import gr.media24.mSites.data.enums.FeedStatus;
import gr.media24.mSites.data.enums.VideoType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author npapadopoulos
 */
@Component
@Transactional
public class ApiUpdater {

	private static final Logger logger = Logger.getLogger(ApiUpdater.class.getName());
	
	private static int maxDepth = 1; //Increase This Value To Read More Articles After An Initialization
	
	@Autowired private ApiUpdaterFactory contentApiUpdaterFactory;
    @Autowired private FeedDao feedDao;
    @Autowired private ArticleDao articleDao;
    @Autowired private ArticleRelatedArticleDao articleRelatedArticleDao;
    @Autowired private CategoryDao categoryDao;
    @Autowired private TagDao tagDao;
    @Autowired private StoryDao storyDao;
    @Autowired private AdvertorialDao advertorialDao;
    @Autowired private VideoDao videoDao;
    @Autowired private AuthorDao authorDao;
    @Autowired private PictureDao pictureDao;
    @Autowired private PhotostoryDao photostoryDao;
    @Autowired private NewspaperDao newspaperDao;
    @Autowired private SectionDao sectionDao;
	
	public void run() {
		Date starting = new Date();
		int counter = 0;
		List<Feed> feeds = feedDao.getByFeedFlagsAndFeedStatus(Arrays.asList(new FeedFlag[] { FeedFlag.API_SECTIONS, FeedFlag.API_POOL }), FeedStatus.ENABLED);
		for(Feed feed : feeds) {
			if(feed.getCategory()==null) {
				logger.error("@API Every Feed Must Be Related With A Category");
				throw new RuntimeException("Every Feed Must Be Related With A Category");
			}
            try {
                String urlString = feed.getUrl() + feed.getParams() + "&ts=" + starting.getTime(); //ts Parameter As A Cache-Buster
                logger.info("@API Read Feed : " + urlString);
                List<AtomEntry> entries = contentApiUpdaterFactory.getAtomEntries(urlString);
                if(entries.size()>0) { //ApiUpdaterFactory Returns An Empty List In Case Of An Exception
                	this.entriesToArticles(entries, feed, 0, false);
                	counter += entries.size();
                }
            }
            catch (Exception exception) {
                logger.error(exception.getMessage(), exception);
            }
        }
		/*
         * Log Time Taken To Complete Successfully
         */
        logger.info("@API Updater Completed. " + counter + " Entries Read In " + (new Date().getTime()-starting.getTime())/org.apache.commons.lang3.time.DateUtils.MILLIS_PER_SECOND + " seconds");
	}

	/**
	 * Given An Atom Feed, Read And Persist Atom Entries Read 
	 * @param entries List Of Atom Entries
	 * @param feed Feed Object
	 * @param depth This Parameter In Relation With maxDepth Is A Way To Avoid Endless Reading Of Entry Relationships
	 * @param forced true If You Want To Force Update Of Content Without Taking Into Account dateLastUpdated Values
	 * @return List Of Articles
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public List<Article> entriesToArticles(List<AtomEntry> entries, Feed feed, int depth, boolean forced) throws InstantiationException, IllegalAccessException, IOException, XmlPullParserException {
		/*
		 * For An API_POOL Feed Create A Map Of All Existing Sections With No Articles Related To Them
		 */
		HashMap<Section, List<Article>> sectionArticles = new HashMap<Section, List<Article>>();
		if(feed.getFeedFlag().equals(FeedFlag.API_POOL) && depth==0) {
			String name = feed.getCategory().getSectionUniqueName() + "@";
			List<Section> existingSections = sectionDao.getByNameLikePublicationName(name, feed.getCategory().getPublication().getName());
			for(Section section : existingSections) {
				sectionArticles.put(section, new ArrayList<Article>());
			}
		}
		/*
		 * Process Atom Entries
		 */
		List<Article> articles = new ArrayList<Article>();
        for(AtomEntry entry : entries) {
        	String eceArticleId = entry.getId();
        	List<ArticleRelatedArticle> relatedArticles = new ArrayList<ArticleRelatedArticle>();
        	/*
        	 * It May Not Look That Elegant But By Executing A Scalar Query For dateLastUpdated Of Every Feed Entry We Can
        	 * Avoid Loading (And Processing) Articles That Did Not Changed Since Last Task Execution. The Only Critical Point
        	 * Here Is That We Have To Call The setArticleSections() Method For All Articles Otherwise We'll End Up With A
        	 * Section (Or Some Sections) Including Only The Updated Articles Since Last Task Execution (Or No Articles At All) 
        	 */
        	boolean newOrUpdated = forced ? true : articleDao.newOrUpdated(eceArticleId, entry.getUpdated());
        	switch(getArticleType(entry)) {
        		case STORY:
        			/*
        			 * Possible Relations: Teaser, Photo, Story, Photostory, Video
        			 */
        			if(!newOrUpdated && depth==0 && !feed.getFeedFlag().equals(FeedFlag.API_POOL)) { //No Changes Since Last Task Execution
        				break;
        			}
        			Story story = (Story) articleDao.articleExists(eceArticleId, Story.class);
        			if(newOrUpdated) {
        				if(story.getArticleState()!=null && !story.getArticleState().equals(ArticleState.NEW)) { //Do Not Update A Story With State = 'EDIT'
        					break;
	        			}
        				if(depth < maxDepth) { //Relations Are Updated Only For The Root Feeds
	        				relatedArticles.addAll(getArticleRelatedArticles(story, entry, feed, depth+1));
	        				relatedArticles.addAll(getArticleRelatedPictures(story, entry));
	        				relatedArticles.addAll(getArticleRelatedVideos(story, entry, feed));
	        				removeOrphanArticleRelatedArticles(story, relatedArticles);
	        				story.addRelatedArticle(relatedArticles);
	        			}
	        			story.setEceArticleId(eceArticleId);
	                    story.setArticleType(ArticleType.STORY);
	        			story.setArticleState(ArticleState.NEW);
	                    story.setAuthors(getArticleAuthors(entry));
	                    story.setCategories(getArticleCategories(entry.getCategories(), feed));
	                    story.setTags(getArticleTags(entry.getTags(), feed));
	                    story.setDateLastUpdated(entry.getUpdated());
	                    story.setDatePublished(entry.getPublished());
	                    if(entry.getTitle()!=null && !entry.getTitle().isEmpty()) story.setTitle(entry.getTitle());
	                    if(entry.getSupertitle()!=null && !entry.getSupertitle().isEmpty()) story.setSupertitle(entry.getSupertitle());
	                    if(entry.getSummary()!=null && !entry.getSummary().isEmpty()) story.setLeadText(entry.getSummary());
	                    if(entry.getTeaserTitle()!=null && !entry.getTeaserTitle().isEmpty()) story.setTeaserTitle(entry.getTeaserTitle());
	                    if(entry.getTeaserSupertitle()!=null && !entry.getTeaserSupertitle().isEmpty()) story.setTeaserSupertitle(entry.getTeaserSupertitle());
	                    if(entry.getTeaserSummary()!=null && !entry.getTeaserSummary().isEmpty()) story.setTeaserLeadText(entry.getTeaserSummary());
	                    if(entry.getContent()!=null && !entry.getContent().isEmpty()) story.setBody(entry.getContent());
	                    if(entry.getAlternate()!=null && !entry.getAlternate().isEmpty()) story.setAlternate(entry.getAlternate());
	                    //Story Fields Existing Only On Product Articles
	                    if(entry.getPrice()!=null && !entry.getPrice().isEmpty()) story.setPrice(entry.getPrice());
	                    if(entry.getLink()!=null && !entry.getLink().isEmpty()) story.setLink(entry.getLink());
	                    storyDao.persistOrMerge(story);
	                    logger.info("@API STORY Updated, eceArticleId : " + eceArticleId);
        			}
        			if(feed.getFeedFlag().equals(FeedFlag.API_POOL) && depth==0) setArticleSections(sectionArticles, story, entry, feed); //Add Story To It's Sections
                    articles.add(story);
                    break;
        		case ADVERTORIAL:
        			/*
        			 * Possible Relations: Teaser, Photo [Theoretically There Are Story, Photostory, Video Too, But We Don't Read Them ]
        			 */
        			if(!newOrUpdated && depth==0 && !feed.getFeedFlag().equals(FeedFlag.API_POOL)) { //No Changes Since Last Task Execution
        				break;
        			}
                    Advertorial advertorial = (Advertorial) articleDao.articleExists(eceArticleId, Advertorial.class);
                    if(newOrUpdated) {
                    	if(advertorial.getArticleState()!=null && !advertorial.getArticleState().equals(ArticleState.NEW)) { //Do Not Update An Advertorial With State = 'EDIT'
	        				break;
	        			}
                    	if(depth < maxDepth) { //Relations Are Updated Only For The Root Feeds
	                    	relatedArticles.addAll(getArticleRelatedPictures(advertorial, entry));
	                    	removeOrphanArticleRelatedArticles(advertorial, relatedArticles);
	        				advertorial.addRelatedArticle(relatedArticles);
	        			}
	                    advertorial.setEceArticleId(eceArticleId);
	                    advertorial.setArticleType(ArticleType.ADVERTORIAL);
	                    advertorial.setArticleState(ArticleState.NEW);
	                    advertorial.setAuthors(getArticleAuthors(entry));
	                    advertorial.setCategories(getArticleCategories(entry.getCategories(), feed));
	                    advertorial.setTags(getArticleTags(entry.getTags(), feed));
	                    advertorial.setDateLastUpdated(entry.getUpdated());
	                    advertorial.setDatePublished(entry.getPublished());
	                    if(entry.getTitle()!=null && !entry.getTitle().isEmpty()) advertorial.setTitle(entry.getTitle());
	                    if(entry.getSupertitle()!=null && !entry.getSupertitle().isEmpty()) advertorial.setSupertitle(entry.getSupertitle());
	                    if(entry.getSummary()!=null && !entry.getSummary().isEmpty()) advertorial.setLeadText(entry.getSummary());
	                    if(entry.getTeaserTitle()!=null && !entry.getTeaserTitle().isEmpty()) advertorial.setTeaserTitle(entry.getTeaserTitle());
	                    if(entry.getTeaserSupertitle()!=null && !entry.getTeaserSupertitle().isEmpty()) advertorial.setTeaserSupertitle(entry.getTeaserSupertitle());
	                    if(entry.getTeaserSummary()!=null && !entry.getTeaserSummary().isEmpty()) advertorial.setTeaserLeadText(entry.getTeaserSummary());
	                    if(entry.getAlternate()!=null && !entry.getAlternate().isEmpty()) advertorial.setAlternate(entry.getAlternate());
	                    advertorialDao.persistOrMerge(advertorial);
	                    logger.info("@API ADVERTORIAL Updated, eceArticleId : " + eceArticleId);
                    }
                    if(feed.getFeedFlag().equals(FeedFlag.API_POOL) && depth==0) setArticleSections(sectionArticles, advertorial, entry, feed); //Add Advertorial To It's Sections
                    articles.add(advertorial);
        			break;
        		case PHOTOSTORY:
        			/*
        			 * Possible Relations: Teaser, Photo [Theoretically There Are Story, Photostory Too, But We Don't Read Them ]
        			 */
        			if(!newOrUpdated && depth==0) { //No Changes Since Last Task Execution
        				break;
        			}
        			Photostory photostory = (Photostory) articleDao.articleExists(eceArticleId, Photostory.class);
        			if(newOrUpdated) {
        				if(photostory.getArticleState()!=null && !photostory.getArticleState().equals(ArticleState.NEW)) { //Do Not Update A Photostory With State = 'EDIT'
	        				break;
	        			}
	        			if(depth < maxDepth) { //Relations Are Updated Only For The Root Feeds
	        				relatedArticles.addAll(getArticleRelatedPictures(photostory, entry));
	        				removeOrphanArticleRelatedArticles(photostory, relatedArticles);
	        				photostory.addRelatedArticle(relatedArticles);
	                    }
	        			photostory.setEceArticleId(eceArticleId);
	        			photostory.setArticleType(ArticleType.PHOTOSTORY);
	        			photostory.setArticleState(ArticleState.NEW);
	        			photostory.setAuthors(getArticleAuthors(entry));
	        			photostory.setCategories(getArticleCategories(entry.getCategories(), feed));
	        			photostory.setTags(getArticleTags(entry.getTags(), feed));
	        			photostory.setDateLastUpdated(entry.getUpdated());
	        			photostory.setDatePublished(entry.getPublished());
	        			if(entry.getTitle()!=null && !entry.getTitle().isEmpty()) photostory.setTitle(entry.getTitle());
	        			if(entry.getSupertitle()!=null && !entry.getSupertitle().isEmpty()) photostory.setSupertitle(entry.getSupertitle());
	        			if(entry.getSummary()!=null && !entry.getSummary().isEmpty()) photostory.setLeadText(entry.getSummary());
	        			if(entry.getAlternate()!=null && !entry.getAlternate().isEmpty()) photostory.setAlternate(entry.getAlternate());
	                    photostoryDao.persistOrMerge(photostory);
	                    logger.info("@API PHOTOSTORY Updated, eceArticleId : " + eceArticleId);
        			}
                    articles.add(photostory);
        			break;
        		case PICTURE:
        			/*
        			 * Pictures Have No Relations. Normally There Is No Reason To Read A Feed With Pictures, They're Read As Relations To Other Entries
        			 * You Can Use It Though To Get Fields That You Can Not Read From Photo Relations Like Authors, Categories, Dates et al.
        			 */
        			if(!newOrUpdated && depth==0) { //No Changes Since Last Task Execution
        				break;
        			}
        			Picture picture = (Picture) articleDao.articleExists(eceArticleId, Picture.class);
        			if(picture.getArticleState()!=null && !picture.getArticleState().equals(ArticleState.NEW)) { //Do Not Update A Picture With State = 'EDIT'
        				break;
        			}
        			picture.setEceArticleId(eceArticleId);
        			picture.setArticleType(ArticleType.PICTURE);
        			picture.setArticleState(ArticleState.NEW);
        			picture.setAuthors(getArticleAuthors(entry));
        			picture.setCategories(getArticleCategories(entry.getCategories(), feed));
        			picture.setDateLastUpdated(entry.getUpdated());
        			picture.setDatePublished(entry.getPublished());
        			if(entry.getTitle()!=null && !entry.getTitle().isEmpty()) picture.setTitle(entry.getTitle()); else picture.setTitle("Related Picture");
                    if(entry.getCaption()!=null && !entry.getCaption().isEmpty()) picture.setCaption(entry.getCaption());
        			if(entry.getCredits()!=null && !entry.getCredits().isEmpty()) picture.setCredits(entry.getCredits());
        			if(entry.getAlternate()!=null && !entry.getAlternate().isEmpty()) picture.setAlternate(entry.getAlternate());
        			pictureDao.persistOrMerge(picture);
                    logger.info("@API PICTURE Updated, eceArticleId : " + eceArticleId);
    				articles.add(picture);
        			break;
        		case VIDEO:
        			/*
        			 * Possible Relations: Teaser, Photo. The Logic Is Same As Above. Normally There Is No Reason To Read A Feed With Videos
        			 */
        			if(!newOrUpdated && depth==0) { //No Changes Since Last Task Execution
        				break;
        			}
        			Video video = (Video) articleDao.articleExists(eceArticleId, Video.class);
        			if(video.getArticleState()!=null && !video.getArticleState().equals(ArticleState.NEW)) { //Do Not Update A Video With State = 'EDIT'
        				break;
        			}
        			if(depth < maxDepth) { //Relations Are Updated Only For The Root Feeds
        				relatedArticles.addAll(getArticleRelatedPictures(video, entry));
        				removeOrphanArticleRelatedArticles(video, relatedArticles);
        				video.addRelatedArticle(relatedArticles);
                    }
        			video.setEceArticleId(eceArticleId);
        			video.setArticleType(ArticleType.VIDEO);
        			video.setArticleState(ArticleState.NEW);
        			video.setAuthors(getArticleAuthors(entry));
        			video.setCategories(getArticleCategories(entry.getCategories(), feed));
        			video.setDateLastUpdated(entry.getUpdated());
        			video.setDatePublished(entry.getPublished());
        			if(entry.getTitle()!=null && !entry.getTitle().isEmpty()) video.setTitle(entry.getTitle());
        			if(entry.getSupertitle()!=null && !entry.getSupertitle().isEmpty()) video.setSupertitle(entry.getSupertitle());
        			if(entry.getSummary()!=null && !entry.getSummary().isEmpty()) video.setLeadText(entry.getSummary());
        			if(entry.getAlternate()!=null && !entry.getAlternate().isEmpty()) video.setAlternate(entry.getAlternate());
        			for(AtomLink link : entry.getLinks()) { //Video Type And Code From enclosure Link
        				if(link.getRel().equalsIgnoreCase("enclosure") && link.getComment().equalsIgnoreCase("embed")) {
        	                if(link.getType()!=null) video.setVideoType(VideoType.valueOf(link.getType().trim().toUpperCase()));
        	                if(link.getCode()!=null) video.setVideoId(link.getCode());
        				}
        			}
        			if(video.getVideoType()!=null && video.getVideoType().equals(VideoType.ADVANCEDCODE)) video.setEmbeddedCode(entry.getContent());
        			videoDao.persistOrMerge(video);
        			logger.info("@API VIDEO Updated, eceArticleId : " + eceArticleId);
    				articles.add(video);
        			break;
        		case NEWSPAPER: //We're Reading NEWSPAPERS With Escenic's Feeds
        		default:
        			break;
        	}
        }
        /*
         * Persist Sections And Their Related Articles In Case Of API_POOL Feeds
         */
        if(feed.getFeedFlag().equals(FeedFlag.API_POOL) && depth==0) {
        	 for(Section section : sectionArticles.keySet()) {
                 List<Article> a = sectionArticles.get(section);
                 section.setArticles(a);
                 sectionDao.persistOrMerge(section);
                 logger.trace("@API Section " + section.getName() + " Updated");
             }
        }
        return articles;
	}
	
    /**
     * Extract Article's eceArticleId From Link's href [ i.e. http://ladylike.gr/celebrities/rihanna-nude-photos.3374249.html Returns 3374249 ] 
     * @param url Link's href
     * @return Article's eceArticleId
     */
    public String extractArticleEceId(String href) {
    	try {
    		String result = href.substring(0, href.lastIndexOf(".html"));
    		result = result.substring(result.lastIndexOf(".") + 1);
    		return result;
    	}
    	catch(StringIndexOutOfBoundsException exception) {
        	return extractBinaryEceId(href); //If href Does Not Contain ".html" Try To Catch ".ece"
    	}
    }
	
    /**
     * Extract Picture's eceArticleId From A Given URL [ i.e. http://www.sport24.gr/football/article829207.ece/BINARY/w300/ab.jpg Returns 829207 ]
     * @param url Picture's URL
     * @return Picture's eceArticleId
     */
    public String extractBinaryEceId(String url) {
        Pattern pattern = Pattern.compile(".*article([0-9]*)\\.ece.*");
        Matcher matcher = pattern.matcher(url);
        if(matcher.matches()) {
            try {
                return matcher.group(1);
            }
            catch(Exception exception) {
                logger.info("@API Can Not Extract eceArticleId From : " + url);
            }
        }
        return null;
    }
    
    /**
     * Get The Feed URL For A Single Article Given The Article's eceArticleId
     * @param eceArticleId Article's eceArticleId
     * @param feed Feed Object
     * @return Article's Feed URL
     */
    public String getArticleFeed(String eceArticleId, Feed feed) {
    	return feed.getUrl()
    			.replaceAll("articles", "article")
    			.replaceAll("pool", "article") 
    			+ "?article=" + eceArticleId 
    			+ "&profile=" + feed.getProfile() 
    			+ "&view=generic%2Fv2%2Farticle-atom"; 
    }
    
    /**
     * Get The Feed URL For A Section Given The Section's Unique Name
     * @param sectionUniqueName Section's Unique Name
     * @param publication Publication's Id As Read From The Configuration XML
     * @param items Maximum Number Of Articles As Read From The Configuration XML
     * @param feed Feed Object
     * @return Section's Feed URL
     */
    public String getSectionFeed(String sectionUniqueName, String publication, String items, Feed feed) {
    	return feed.getUrl()
    			.replaceAll("articles", "articles/section")
    			.replaceAll("pool", "articles/section") 
    			+ "?sections=" + sectionUniqueName
    			+ "&publication=" + publication
    			+ "&profile=" + feed.getProfile()
    			+ "&types=news,advertorial,photostory"
    			+ "&view=generic%2Fv2%2Flatest-atom"
    			+ "&items=" + items;
    }
    
    /**
     * Get The Feed URL For A Tag Given The Tag's Name
     * @param sectionUniqueName Section's Unique Name
     * @param tagName Tag's Name
     * @param publication Publication's Id As Read From The Configuration XML
     * @param items Maximum Number Of Articles As Read From The Configuration XML
     * @param feed Feed Object
     * @return Tag's Feed URL
     */
    public String getTagFeed(String sectionUniqueName, String tagName, String publication, String items, Feed feed) {
    	return feed.getUrl()
    			.replaceAll("articles", "articles/section")
    			.replaceAll("pool", "articles/section") 
    			+ "?sections=" + sectionUniqueName
    			+ "&tags=" + tagName
    			+ "&publication=" + publication
    			+ "&profile=" + feed.getProfile()
    			+ "&types=news,advertorial,photostory"
    			+ "&view=generic%2Fv2%2Flatest-atom"
    			+ "&items=" + items;
    }
    
    /**
     * Get Article's Type Given An Atom Entry
     * @param entry Atom Entry
     * @return Article's Type
     */
	public ArticleType getArticleType(AtomEntry entry) {
		if(entry.getType().equals("picture")) {
			return ArticleType.PICTURE;
		}
		else if(entry.getType().equals("multipleTypeVideo")) {
			return ArticleType.VIDEO;
		}
		else if(entry.getType().equals("advertorial")) {
			return ArticleType.ADVERTORIAL;
		}
		else if(entry.getType().equals("photostory")) {
			return ArticleType.PHOTOSTORY;
		}
		else if(entry.getType().equals("advertorial")) {
			return ArticleType.NEWSPAPER;
		}
		else {
			return ArticleType.STORY;
		}
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
            String label = atomCategory.getLabel();
        	if(term==null || term.isEmpty() || label==null || label.isEmpty()) continue;
            Category category = null;
            if(term.startsWith("@")) { //Pool Feed
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
     * Transform Atom Entry's Tags To Article Tags
     * @param entryTags Tags Read From Atom Entry
     * @param feed Feed Object
     * @return Set Of Article Tags
     */
    public Set<Tag> getArticleTags(List<AtomTag> entryTags, Feed feed) {
    	Set<Tag> result = new HashSet<Tag>();
    	for(AtomTag atomTag : entryTags) {
    		String name = atomTag.getName();
    		String displayName = atomTag.getDisplayName();
    		if(name==null || name.isEmpty() || displayName==null || displayName.isEmpty()) continue;
    		Tag tag = tagDao.tagExists(name, displayName, feed.getCategory().getPublication());
    		if(tag!=null && !result.contains(tag)) {
    			result.add(tag);
    		}
    	}
    	return result;
    }
    
	/**
	 * Add Persisted Articles To sectionArticles Map For API_POOL Feeds
	 * @param sectionArticles sectionArticles Map
	 * @param article Persisted Article To Be Added To The Map
	 * @param entry An Article May Exist In More Than One API_POOL Feeds, So You Need To Read The Atom Entry's Categories And Not The Article Categories
	 * @param feed Feed Object Needed To Get Sections' Publication
	 */
    public void setArticleSections(HashMap<Section, List<Article>> sectionArticles, Article article, AtomEntry entry, Feed feed) {
    	for(AtomCategory atomCategory : entry.getCategories()) {
    		String term = atomCategory.getTerm();
            if(term==null || term.isEmpty()) continue;
            if(term.startsWith("@")) { //Only Categories With A Term
            	Category feedCategory = feed.getCategory();
            	Category articleCategory = categoryDao.categoryExists(feedCategory.getName(), feedCategory.getSectionUniqueName(), term, feedCategory.getPublication());
    			String name = articleCategory.getSectionUniqueName() + articleCategory.getGroupName();
    			Section section = sectionDao.sectionExists(name, feed.getCategory().getPublication());
    			List<Article> articles = sectionArticles.get(section);
    			if(articles == null) articles = new ArrayList<Article>();
    			if(!articles.contains(article)) articles.add(article);
    			sectionArticles.put(section, articles);
            }
    	}
    }
    
    /**
     * Given An Article And The Atom Entry That Represent It, Persist All Story Relations And Add The Relationships To The Returned List Of ArticleRelatedArticles 
     * @param article Article Object
     * @param entry Article's Atom Entry
     * @param feed Feed Object Needed To Construct The Article Feed So That You Can Read More Info About The Story
     * @param depth Current Feed's Loop Depth
     * @return List Of Article's ArticleRelatedArticle Objects
     * @throws IOException
     * @throws XmlPullParserException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<ArticleRelatedArticle> getArticleRelatedArticles(Article article, AtomEntry entry, Feed feed, int depth) throws IOException, XmlPullParserException, InstantiationException, IllegalAccessException {
    	if(depth > maxDepth) {
    		logger.error("@API Infinite Loop");
    		throw new RuntimeException("Infinite Loop");
    	}
        List<ArticleRelatedArticle> result = new ArrayList<ArticleRelatedArticle>();
        /*
         * Links Representing Story Relations Have rel="related" And type="text/html"
         */
        for(AtomLink link : entry.getLinks()) {
            if(link.getRel().equalsIgnoreCase("related") && link.getType().equalsIgnoreCase("text/html")) {
            	String eceArticleId = extractArticleEceId(link.getHref());
            	if(eceArticleId.equals(entry.getId())) continue; //An Article Can Not Have Itself As A Relation
            	try {
            		List<Article> relatedArticles = entriesToArticles(contentApiUpdaterFactory.getAtomEntries(getArticleFeed(eceArticleId, feed)), feed, depth, false); //Only One Result Here
            		logger.trace("@API STORY Relations Updated, eceArticleId : " + eceArticleId + ", relatedArticleId : " + entry.getId());
            		/*
                     * Article-Story Relationship
                     */
                	for(Article relatedArticle : relatedArticles) {
                		ArticleRelatedArticle related = articleRelatedArticleDao.articleRelatedArticleExists(article, relatedArticle, null);
                		result.add(related);
                	}
            	}
            	catch(IOException exception) {
            		logger.error("@API IOException While Parsing Related Article, Link's href : " + link.getHref());
            		continue;
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
        	/*
        	 * Links Representing Picture Relations Have rel="enclosure" AND type="image/jpeg" | type="image/png"
        	 */
            if(link.getRel().equalsIgnoreCase("enclosure") && (link.getType().equalsIgnoreCase("image/jpeg") || link.getType().equalsIgnoreCase("image/png"))) {  	
            	String pictureEceId = extractBinaryEceId(link.getHref());
            	if(pictureEceId==null) continue; //Can Not Extract pictureEceId
            	Picture picture = (Picture) articleDao.articleExists(pictureEceId, Picture.class);
            	EnclosureComment enclosureComment = null;
            	if(link.getComment()!=null) {
            		if(article instanceof Video) {
            			enclosureComment = EnclosureComment.MAIN; //You Can Safely Define All Video Teaser Pictures As Main
            		}
            		else {
            			enclosureComment = EnclosureComment.valueOf(link.getComment().trim().toUpperCase());
            		}
            	}
            	picture.setArticleType(ArticleType.PICTURE);
                picture.setArticleState(ArticleState.NEW);
                picture.setEceArticleId(pictureEceId);
                if(link.getHref()!=null && !link.getHref().isEmpty()) picture.setAlternate(link.getHref());
                if(link.getTitle()!=null && !link.getTitle().isEmpty()) picture.setTitle(link.getTitle()); else picture.setTitle("Related Picture");
                if(link.getCredits()!=null && !link.getCredits().isEmpty()) picture.setCredits(link.getCredits());
                if(link.getCaption()!=null && !link.getCaption().isEmpty()) picture.setCaption(link.getCaption());
                pictureDao.persistOrMerge(picture);
                logger.trace("@API PICTURE Relation Updated, ecePictureId : " + picture.getEceArticleId() + ", articleId : " + entry.getId());
                /*
                 * Article-Picture Relationship
                 */
                ArticleRelatedArticle related = articleRelatedArticleDao.articleRelatedArticleExists(article, picture, enclosureComment);
                result.add(related);
            }
        }
        return result;
    }
        
    /**
     * Given An Article And The Atom Entry That Represent It, Persist All Video Relations And Add The Relationships To The Returned List Of ArticleRelatedArticles 
     * @param article Article Object
     * @param entry Article's Atom Entry
     * @param feed Feed Object Needed To Construct The Article Feed So That You Can Read More Info About The Video
     * @return List Of Article's ArticleRelatedArticle Objects
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<ArticleRelatedArticle> getArticleRelatedVideos(Article article, AtomEntry entry, Feed feed) throws InstantiationException, IllegalAccessException {
    	List<ArticleRelatedArticle> result = new ArrayList<ArticleRelatedArticle>();
    	for(AtomLink link : entry.getLinks()) {
    		/*
    		 * Links Representing Video Relations Have rel="related" And comment="embed"
    		 */
    		if(link.getRel()!=null && link.getRel().equalsIgnoreCase("related") & link.getComment()!=null && link.getComment().equalsIgnoreCase("embed")) {
    			VideoType videoType = VideoType.valueOf(link.getType().trim().toUpperCase());
    			String videoHref = link.getHref();
    			String videoEceId = videoType.equals(VideoType.ADVANCEDCODE) ? link.getCode() : extractArticleEceId(videoHref);
    			Video video = (Video) articleDao.articleExists(videoEceId, Video.class);
    			video.setArticleType(ArticleType.VIDEO);
    			video.setArticleState(ArticleState.NEW);
    			video.setEceArticleId(videoEceId);
    			if(videoHref!=null && !videoHref.isEmpty()) video.setAlternate(videoHref);
    			if(link.getTitle()!=null && !link.getTitle().isEmpty()) video.setTitle(link.getTitle()); else video.setTitle("Related Video");
                if(link.getType()!=null) video.setVideoType(videoType);
                if(link.getCode()!=null) video.setVideoId(link.getCode());
                try { //Get Video Relation's Body [ Only For ADVANCEDCODE ] And Related Pictures
            		String videoFeed = video.getVideoType().equals(VideoType.ADVANCEDCODE) ? videoHref : getArticleFeed(videoEceId, feed);
            		List<AtomEntry> entries = contentApiUpdaterFactory.getAtomEntries(videoFeed);
                	for(AtomEntry e : entries) { //Just One Entry Here
                		if(video.getVideoType().equals(VideoType.ADVANCEDCODE)) video.setEmbeddedCode(e.getContent());
                		video.addRelatedArticle(getArticleRelatedPictures(video, e));
                	}
            	}
            	catch(XmlPullParserException exception) { //No Big Deal, Continue Reading The Other Relations
            		logger.error("@API XmlPullParserException While Parsing VIDEO Body & Relations, videoEceId : " + videoEceId + ", href : " + videoHref);
            		continue;
            	}
            	catch(IOException exception) {
            		logger.error("@API IOException While Parsing VIDEO Body & Relations, videoEceId : " + videoEceId + ", href : " + videoHref);
            		continue;
            	}
                videoDao.persistOrMerge(video);
                logger.trace("@API VIDEO Relation Updated, eceVideoId : " + video.getEceArticleId() + ", articleId : " + entry.getId());
                /*
                 * Article-Video Relationship
                 */
                ArticleRelatedArticle related = articleRelatedArticleDao.articleRelatedArticleExists(article, video, EnclosureComment.EMBED);
                result.add(related);
    		}
    	}
    	return result;
    }
    
	/**
	 * Special Handling Is Needed To Remove ArticleRelatedArticles That Do Not Exist Anymore In The ArticleRelatedArticles Association
	 * @param existing Existing ArticleRelatedArticles Of The Article
	 * @param parsed ArticlesRelatedArticles Read For The Article By The Current Updater Execution
	 */
	public void removeOrphanArticleRelatedArticles(Article article, List<ArticleRelatedArticle> parsed) {
		Set<ArticleRelatedArticle> existing = article.getRelatedArticles();
		if(existing.size()>0) { //No Need To Proceed If The Article Is A New One
			List<Article> articles = new ArrayList<Article>();
			for(ArticleRelatedArticle related : parsed) {
				articles.add(related.getRelated());
			}
			Iterator<ArticleRelatedArticle> iterator = existing.iterator();
			while(iterator.hasNext()) {
				ArticleRelatedArticle related = iterator.next();
				if(!articles.contains(related.getRelated())) {
					iterator.remove();
				}
			}
		}
	}
}