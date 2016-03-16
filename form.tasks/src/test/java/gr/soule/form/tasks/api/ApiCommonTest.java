package gr.media24.mSites.tasks.api;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import gr.media24.mSites.atom.domain.AtomEntry;
import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.dao.SectionDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.entities.Author;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Section;
import gr.media24.mSites.data.entities.Video;
import gr.media24.mSites.data.enums.EnclosureComment;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.VideoType;
import gr.media24.mSites.tasks.TasksTest;
import gr.media24.mSites.tasks.api.ApiUpdater;
import gr.media24.mSites.tasks.api.ApiUpdaterFactory;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Test Base Class For Tests Of All Content Types
 * @author npapadopoulos
 */
public class ApiCommonTest extends TasksTest {

	@Autowired private SessionFactory sessionFactory;
    @Autowired private ApiUpdater updater;
    @Autowired private ApiUpdaterFactory updaterFactory;
    @Autowired private FeedDao feedDao;
    @Autowired private SectionDao sectionDao;
    @Autowired private ArticleDao articleDao;
    
    /**
     * extractArticleEceId() Method
     */
    @Test
    public void extractArticleEceIdTest() {
    	String href = "http://www.sport24.gr/multimedia/video/telikos-eyrwligkas-2009-panathhnaikos-tsska-73-71.1479473.html";
    	String actual = updater.extractArticleEceId(href);
    	Assert.assertEquals("1479473", actual);
    	/*
    	 * If href Does Not Contain ".html" The extractBinaryEceId() Method Could Be More Appropriate 
    	 */
    	href = "http://www.sport24.gr/Columns/kaisaris/article3403647.ece";
    	actual = updater.extractArticleEceId(href);
    	Assert.assertEquals("3403647", actual);
    }
    
    /**
     * extractBinaryEceId() Method
     */
    @Test
    public void extractBinaryEceIdTest() {
    	String actual = updater.extractBinaryEceId("http://www.sport24.gr/football/article829207.ece/BINARY/w300/ab.jpg");
    	Assert.assertEquals("829207", actual);
    	/*
    	 * If String Does Not Contain ".ece" null Is The Accepted Result
    	 */
    	actual = updater.extractBinaryEceId("http://www.sport24.gr/football/article829207.html");
    	Assert.assertNull(actual);
    }
    
    /**
     * getSingleArticleFeed() Method
     */
    @Test
    public void getSingleArticleFeedTest() {
    	/*
    	 * API_SECTION Feed
    	 */
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	String actual = updater.getArticleFeed("1234567", feed);
    	String expected = "http://content.24media.gr/develop/article/?article=1234567&profile=c3BvcnQyNHJvaQ==&view=generic%2Fv2%2Farticle-atom";
    	Assert.assertEquals(expected, actual);
    	/*
    	 * API_POOL Feed
    	 */
    	feed = feedDao.getByFeedFlag(FeedFlag.API_POOL).get(0); //There's Only One
    	actual = updater.getArticleFeed("1234567", feed);
    	Assert.assertEquals(expected, actual);
    }

    /**
     * getSectionFeed() Method
     */
    @Test
    public void getSectionFeedTest() {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	String actual = updater.getSectionFeed("Style", "528", "3", feed);
    	String expected = "http://content.24media.gr/develop/articles/section/?sections=Style&publication=528&profile=c3BvcnQyNHJvaQ==&types=news,advertorial,photostory&view=generic%2Fv2%2Flatest-atom&items=3";
    	Assert.assertEquals(expected, actual);
    }

    /**
     * getTagFeed() Method
     */
    @Test
    public void setTagFeedTest() {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	String actual = updater.getTagFeed("Style", "shoes", "528", "3", feed);
    	String expected = "http://content.24media.gr/develop/articles/section/?sections=Style&tags=shoes&publication=528&profile=c3BvcnQyNHJvaQ==&types=news,advertorial,photostory&view=generic%2Fv2%2Flatest-atom&items=3";
    	Assert.assertEquals(expected, actual);
    }

    /**
     * getArticleAuthors() Method. Irrelevant Of Article's Type
     * @throws XmlPullParserException
     * @throws IOException
     */
    @Test
    public void getArticleAuthorsTest() throws XmlPullParserException, IOException {
    	AtomEntry entry = advertorials.get(0);
        Set<Author> authors = updater.getArticleAuthors(entry);
        for(Author author : authors) {
        	Assert.assertEquals("Χάρης Σταύρου", author.getName());
        	break; //Just The First Author
        }
        /*
         * An Entry With No Authors
         */
        entry = advertorials.get(1);
        authors = updater.getArticleAuthors(entry);
        Assert.assertNotNull(authors); //Not Null
        Assert.assertTrue(authors.size() == 0); //But Has No Authors
        /*
         * Currently There Is Not A Way For The Editors To Add More Than One Author To 
         * An Article, But The List<Author> For The AtomParser Is Already In Place
         */
        entry = advertorials.get(2);
        authors = updater.getArticleAuthors(entry);
        Assert.assertEquals(authors.size(), 2);
    }

    /**
     * getArticleCategories() Method
     * @throws IOException
     * @throws XmlPullParserException 
     */
    @Test
    public void getArticleCategoriesTest() throws XmlPullParserException, IOException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	/*
         * Duplicates And Categories With null term Or label Are Ignored, Feed's Category Is Added As An Extra One
         */
    	AtomEntry entry = stories.get(0);
    	List<Category> categories = updater.getArticleCategories(entry.getCategories(), feed);
    	Assert.assertTrue(categories.size()==5);
    	Assert.assertTrue(categories.get(4).getName().equals("test") && categories.get(4).getSectionUniqueName().equals("test"));
    	/*
    	 * Names And Section Unique Names Are The Expected
    	 */
    	Assert.assertTrue(categories.get(0).getName().equals("Σπορ") && categories.get(0).getSectionUniqueName().equals("Sports"));
    	Assert.assertTrue(categories.get(1).getName().equals("Motorsport") && categories.get(1).getSectionUniqueName().equals("Motorsport"));
    	Assert.assertTrue(categories.get(2).getName().equals("Formula 1") && categories.get(2).getSectionUniqueName().equals("Formula1"));
    	Assert.assertTrue(categories.get(3).getName().equals("sport24") && categories.get(3).getSectionUniqueName().equals("ece_frontpage"));    	
    }
    
    /**
     * entriesToArticles() Method
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void entriesToArticlesTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	List<AtomEntry> entries = updaterFactory.getAtomEntries(feed.getUrl() + feed.getParams());
    	List<Article> articles = updater.entriesToArticles(entries, feed, 0, false);
    	Assert.assertNotNull(articles);
        Assert.assertTrue(articles.size() > 0);
        /*
		 * Flush Session And Call A get() Method To Ensure That The Articles Persisted
		 */
		sessionFactory.getCurrentSession().flush();
        List<Article> persisted = articleDao.getBySectionUniqueName("test", "default", 5, null);
		Assert.assertTrue(persisted.size() > 0);
    }
    
    /**
     * setArticleSections() Method
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void setArticleSectionsTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_POOL).get(0); //There's Only One
    	updater.entriesToArticles(stories, feed, 0, false);
    	/*
		 * Flush Session And Check If Section test@top2 And It's Related Articles Are Persisted
		 */
        sessionFactory.getCurrentSession().flush();
        Section section = sectionDao.getByNamePublicationName("test@top2", feed.getCategory().getPublication().getName());
        Assert.assertNotNull(section);
        Assert.assertTrue(section.getArticles().size() == 1);
    }
    
    /**
     * getArticleRelatedArticles() Method()
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void getArticleRelatedArticlesTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	AtomEntry entry = stories.get(5);
    	Article article = new Article(); //Required As A Parameter For getArticleRelatedArticles(), It Can Be null If Article Is Not Persisted Yet
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
        List<ArticleRelatedArticle> result = updater.getArticleRelatedArticles(article, entry, feed, 0);
        Assert.assertTrue(result.size() == 2);
    }
    
    /**
     * getArticleRelatedPictures() Method
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void getArticleRelatedPicturesTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	AtomEntry entry = stories.get(0);
    	Article article = new Article(); //Required As A Parameter For getArticleRelatedArticles(), It Can Be null If Article Is Not Persisted Yet
    	List<ArticleRelatedArticle> result = updater.getArticleRelatedPictures(article, entry);
    	Assert.assertTrue(result.size() == 2);
    	/*
    	 * Make Sure That You Read The enclosureComments Too
    	 */
    	Assert.assertTrue(result.get(0).getEnclosureComment().equals(EnclosureComment.MAIN));
    	Assert.assertTrue(result.get(1).getEnclosureComment().equals(EnclosureComment.INLINE));
    }
    
    /**
     * getArticleRelatedVideos() Method
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void getArticleRelatedVideosTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	updater.entriesToArticles(stories, feed, 0, false);
    	sessionFactory.getCurrentSession().flush();
    	/*
    	 * Make Sure That You Persist Videos With VideoId, VideoType And Related Photos
    	 */
    	int counter = 0;
    	Article article = articleDao.getByEceArticleId("3389260");
    	for(ArticleRelatedArticle related : article.getRelatedArticles()) {
    		if(related.getEnclosureComment().equals(EnclosureComment.EMBED)) {
    			Video video = (Video) related.getRelated();
    			Assert.assertEquals(VideoType.ADVANCEDCODE, video.getVideoType()); //All Related Videos Are ADVANCEDCODE Videos
    			Assert.assertNotNull(video.getEmbeddedCode()); //Embedded Code Is Persisted
    			if(video.getEceArticleId().equals("3389316")) {
    				//Not Likely To Happen, But If Someone Edit This Video The Following Tests Might Fail
    				Assert.assertTrue(video.getVideoId().equals("3389316")); //VideoId For ADVANCEDCODE Videos Is The eceArticleId
    				Assert.assertTrue(video.getEmbeddedCode().contains("OVU6XYJPLw8"));
    				Assert.assertTrue(video.getRelatedArticles().size() == 1); //Only 1 Photo Relation
    				for(ArticleRelatedArticle r : video.getRelatedArticles()) {
    					Assert.assertTrue(r.getEnclosureComment().equals(EnclosureComment.MAIN));
    				}
    			}
    			counter++;
    		}
    	}
    	Assert.assertTrue(counter == 11); //There Are 11 Related Videos
    }

    /**
     * Make Sure That You Can Persist Fields Not Related With One Article Type Only [ For These We Have Other Tests ]
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void articleFieldsTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	updater.entriesToArticles(stories, feed, 0, false);
    	sessionFactory.getCurrentSession().flush();
    	/*
    	 * Make Sure That You Persist Title, Supertitle, LeadText
    	 */
    	Article article = articleDao.getByEceArticleId("3391304");
    	Assert.assertEquals("Γκολάρα στο Εκουαδόρ ο Μπλάνκο", article.getTitle());
    	Assert.assertEquals("ΠΕΝΤΕ ΓΚΟΛ ΣΕ ΟΚΤΩ ΜΑΤΣ Ο ΑΡΓΕΝΤΙΝΟΣ", article.getSupertitle());
    	Assert.assertEquals("Σε δαιμονιώδη φόρμα ο Ισμαέλ Μπλάνκο πέτυχε άλλα δυο γκολ με την Μπαρτσελόνα Γουαγιακίλ με το ένα μάλιστα να είναι άκρως εντυπωσιακό (VIDEO).", article.getLeadText());
    	/*
    	 * Make Sure That You Persist The Above Fields For Teasers Too
    	 */
    	Assert.assertEquals("This Is The Teaser Title", article.getTeaserTitle());
    	Assert.assertEquals("This Is The Teaser Supertitle", article.getTeaserSupertitle());
    	Assert.assertEquals("This Is The Teaser Lead Text", article.getTeaserLeadText());
    }
    
    @Test
    public void removeOrphanArticleRelatedArticlesTest() {
    	//TODO Test: removeOrphanArticleRelatedArticlesTest
    }
}