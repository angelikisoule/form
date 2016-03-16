package gr.media24.mSites.tasks.api;

import java.io.IOException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xmlpull.v1.XmlPullParserException;

import gr.media24.mSites.atom.domain.AtomEntry;
import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.tasks.TasksTest;

/**
 * Tests For Api Photostories
 * @author npapadopoulos
 */
public class ApiPhotostoryTest extends TasksTest {

	@Autowired private SessionFactory sessionFactory;
    @Autowired private ApiUpdater updater;
    @Autowired private ArticleDao articleDao;
    @Autowired private FeedDao feedDao;
    
    /**
     * getArticleType() Method
     * @throws XmlPullParserException
     * @throws IOException
     */
    @Test
    public void getArticleTypeTest() throws XmlPullParserException, IOException {
    	AtomEntry entry = photostories.get(0);
        ArticleType result = updater.getArticleType(entry);
        Assert.assertEquals(ArticleType.PHOTOSTORY, result);
    }
    
    /**
     * Make Sure That You Can Persist apiPhotostory.xml
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void persistTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	updater.entriesToArticles(photostories, feed, 0, false);
    	sessionFactory.getCurrentSession().flush();
    	List<Article> articles = articleDao.getByArticleType(ArticleType.PHOTOSTORY, 5, null);
    	Assert.assertTrue(articles.size() == 1);
    	Assert.assertTrue(articles.get(0).getRelatedArticles().size() == 19);
    } 
}
