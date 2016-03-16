package gr.media24.mSites.tasks.escenic;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import gr.dsigned.atom.domain.AtomEntry;
import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.dao.SectionDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.entities.Author;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Section;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.tasks.TasksTest;
import gr.media24.mSites.tasks.escenic.EscenicUpdater;
import gr.media24.mSites.tasks.escenic.EscenicUpdaterFactory;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Test Base Class For Tests Of All Content Types
 * @author nk, npapadopoulos
 */
public class EscenicCommonTest extends TasksTest {

	@Autowired private SessionFactory sessionFactory;
    @Autowired private EscenicUpdater updater;
    @Autowired private EscenicUpdaterFactory updaterFactory;
    @Autowired private ArticleDao articleDao;
    @Autowired private SectionDao sectionDao;
    @Autowired private FeedDao feedDao;
    
    /**
     * extractArticleEceId() Method
     * @throws XmlPullParserException
     * @throws IOException
     */
    @Test
    public void extractArticleEceIdTest() throws XmlPullParserException, IOException {
    	AtomEntry entry = entries.get(0);
        String actual = updater.extractArticleEceId(entry);
        Assert.assertEquals("828112", actual);
    }
    
    /**
     * extractPictureEceId() Method
     */
    @Test
    public void extractPictureEceIdTest() {
        String url = "http://www.redplanet.gr/podosfairo/Mirallas/article829207.ece/BINARY/w300/ab.JPG";
        String actual = updater.extractPictureEceId(url);
        Assert.assertEquals("829207", actual);
    } 

    /**
     * getAlternate() Method
     * @throws IOException 
     * @throws XmlPullParserException 
     */
    @Test
    public void getArternateTest() throws XmlPullParserException, IOException {
    	AtomEntry entry = entries.get(0);
    	String expected = "http://www.redplanet.gr/photostories/eikones-apo-to-olympiakos-pao.828112.html";
    	String actual = updater.getAlternate(entry);
    	Assert.assertEquals(expected, actual);
    }
    
    /**
     * getArticleTypeTest() Method
     * @throws XmlPullParserException
     * @throws IOException
     */
    @Test
    public void getArticleTypeTest() throws XmlPullParserException, IOException {
        /*
         * Test Some Articles' Types
         */
		AtomEntry entry = entries.get(0);
        ArticleType result = updater.getArticleType(entry);
        assertEquals(ArticleType.PHOTOSTORY, result);
        
        entry = entries.get(3);
        result = updater.getArticleType(entry);
        assertEquals(ArticleType.STORY, result);
        
        entry = entries.get(6);
        result = updater.getArticleType(entry);
        assertEquals(ArticleType.VIDEO, result);
    }
    
    /**
     * getArticleAuthors() Method.  Irrelevant Of Article's Type
     * @throws IOException
     * @throws XmlPullParserException
     */
    @Test
    public void getArticleAuthorsTest() throws IOException, XmlPullParserException {
        AtomEntry entry = entries.get(1);
        Set<Author> authors = updater.getArticleAuthors(entry);
        String expectedResult = "Βασίλης Λαπατάς";
        for(Author author : authors) {
        	assertEquals(expectedResult, author.getName());
        	break; //Just One Author
        }
    }
    
    /**
     * getArticleCategories() Method
     * @throws IOException
     * @throws XmlPullParserException
     */
    @Test
    public void getArticleCategoriesTest() throws IOException, XmlPullParserException {
        AtomEntry entry = entries.get(3);
        /*
         * Test Article's Categories, Extra Category And continue When term Is NULL
         */
        Feed feed = feedDao.getByFeedFlag(FeedFlag.ESCENIC).get(0); //There's Only One
        List<Category> result = updater.getArticleCategories(entry.getCatogories(), feed);
        Assert.assertTrue(result.size()==5); //Article's Categories + default + default@main2 
        assertEquals("Paok", result.get(0).getSectionUniqueName());
        assertEquals("ece_frontpage", result.get(1).getSectionUniqueName());
        assertEquals("important", result.get(2).getSectionUniqueName());
    }
    
    /**
     * entriesToArticles() Method
     * @throws IOException 
     * @throws XmlPullParserException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @Test
    public void entriesToArticlesTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.ESCENIC).get(0); //There's Only One
    	List<AtomEntry> entries = updaterFactory.getAtomEntries(feed.getUrl() + feed.getParams());
        List<Article> articles = updater.entriesToArticles(entries, feed, 0);
        Assert.assertNotNull(articles);
        Assert.assertTrue(articles.size() > 0); //There Are Always Sport Newspapers
        /*
		 * Flush Session And Call A get() Method To Ensure That The Articles Persisted
		 */
		sessionFactory.getCurrentSession().flush();
        List<Article> persisted = articleDao.getBySectionUniqueNameArticleType("test", "default", ArticleType.NEWSPAPER, 5, null);
		Assert.assertTrue(persisted.size() > 0);
    }
    
    /**
     * entriesToSections() Method
     * @throws IOException 
     * @throws XmlPullParserException 
     */
    @Test
    public void entriesToSectionsTest() throws XmlPullParserException, IOException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.ESCENIC).get(0); //There's Only One
    	updater.entriesToSections(entries, feed);
        /*
		 * Flush Session And Check If Section test@main2 And It's Related Articles Are Persisted
		 */
        sessionFactory.getCurrentSession().flush();
        Section section = sectionDao.getByNamePublicationName("test@main2", feed.getCategory().getPublication().getName());
        Assert.assertNotNull(section);
        Assert.assertTrue(section.getArticles().size() > 0);
    }
    
    /**
     * getArticleRelatedArticles() Method
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws XmlPullParserException 
     * @throws IOException 
     */
    @Test
    public void getArticleRelatedArticlesTest() throws IOException, XmlPullParserException, InstantiationException, IllegalAccessException {
    	AtomEntry entry = entries.get(5);        
    	Article article = new Article(); //Required As A Parameter For getArticleRelatedArticles(), It Can Be null If Article Is Not Persisted Yet
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.ESCENIC).get(0); //There's Only One
        List<ArticleRelatedArticle> result = updater.getArticleRelatedArticles(article, entry, feed, 0);
        Assert.assertTrue(result.size()==3); //3 Related Articles
    }
    
    /**
     * getArticleRelatedPictures() Method
     * @throws IOException 
     * @throws XmlPullParserException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @Test
    public void getArticleRelatedPicturesTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	AtomEntry entry = entries.get(0);
        Article article = new Article(); //Required As A Parameter For getArticleRelatedPictures(), It Can Be null If Article Is Not Persisted Yet
    	/*
         * Check Number Of Related Pictures And Some Titles
         */
        List<ArticleRelatedArticle> result = updater.getArticleRelatedPictures(article, entry);
        Assert.assertEquals(10, result.size());        
        Assert.assertEquals("Ασταμάτητος ο Κουβανός κόντρα στον ΠΑΟ.", result.get(0).getRelated().getTitle());
        Assert.assertEquals("Εκπληκτικοί, όπως πάντα, οι οπαδοί του Θρύλου!", result.get(3).getRelated().getTitle());
        Assert.assertEquals("Οι παίκτες του Θρύλου πανηγυρίζουν στο τέλος του ματς.", result.get(5).getRelated().getTitle());
    }
}