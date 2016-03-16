package gr.media24.mSites.tasks.api;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import gr.media24.mSites.atom.domain.AtomEntry;
import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.tasks.TasksTest;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Tests For Api Advertorials
 * @author npapadopoulos
 */
public class ApiAdvertorialTest extends TasksTest {
	
	@Autowired private SessionFactory sessionFactory;
    @Autowired private ApiUpdater updater;
    @Autowired private ApiUpdaterFactory updaterFactory;
    @Autowired private FeedDao feedDao;
    @Autowired private ArticleDao articleDao;
    
    /**
     * getArticleType() Method
     * @throws XmlPullParserException
     * @throws IOException
     */
    @Test
    public void getArticleTypeTest() throws XmlPullParserException, IOException {
    	Random generator = new Random();
        for(int i = 0; i < 5; i++) { //Test 5 Random Entries Of The 10 In Total
			int random = generator.nextInt(10);
			AtomEntry entry = advertorials.get(random);
	        ArticleType result = updater.getArticleType(entry);
	        Assert.assertEquals(ArticleType.ADVERTORIAL, result);
		}
    }
    
    /**
     * Make Sure That You Can Persist apiAdvertorial.xml
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void persistTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	updater.entriesToArticles(advertorials, feed, 0, false);
    	sessionFactory.getCurrentSession().flush();
    	List<Article> articles = articleDao.getByArticleType(ArticleType.ADVERTORIAL, 100, null);
    	Assert.assertTrue(articles.size() == 10);
    }
}
