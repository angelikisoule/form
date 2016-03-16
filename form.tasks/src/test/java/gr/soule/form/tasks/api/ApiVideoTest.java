package gr.media24.mSites.tasks.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Random;

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
import gr.media24.mSites.data.entities.Video;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.VideoType;
import gr.media24.mSites.tasks.TasksTest;

/**
 * Tests For Api Videos
 * @author npapadopoulos
 */
public class ApiVideoTest extends TasksTest {

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
    public void testGetArticleType() throws XmlPullParserException, IOException {
    	Random generator = new Random();
        for(int i = 0; i < 5; i++) { //Test 5 Random Entries Of The 10 In Total
			int random = generator.nextInt(10);
			AtomEntry entry = videos.get(random);
	        ArticleType result = updater.getArticleType(entry);
	        assertEquals(ArticleType.VIDEO, result);
		}
    }
    
    /**
     * Make Sure That You Can Persist apiMultipleTypeVideo.xml And Especially Fields That You Have Only For VIDEO Articles
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
	@Test
    public void persistAndFieldsTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	updater.entriesToArticles(videos, feed, 0, false);
    	sessionFactory.getCurrentSession().flush();
    	List<Article> articles = articleDao.getByArticleType(ArticleType.VIDEO, 100, null);
    	Assert.assertTrue(articles.size() == 10);
    	/*
    	 * Make Sure That You Persist videoType And videoId
    	 */
    	Video video = (Video) articleDao.getByEceArticleId("3391158");
    	Assert.assertTrue(video.getVideoType().equals(VideoType.YOUTUBE));
    	Assert.assertEquals("rioGL6UN9Qk", video.getVideoId());
    	/*
    	 * Make Sure That You Persist embeddedCode For ADVANCEDCODE Videos
    	 */
    	video = (Video) articleDao.getByEceArticleId("3390049");
    	Assert.assertNotNull(video.getEmbeddedCode());
    	Assert.assertTrue(video.getEmbeddedCode().contains("playbuzz.com"));
    }
}
