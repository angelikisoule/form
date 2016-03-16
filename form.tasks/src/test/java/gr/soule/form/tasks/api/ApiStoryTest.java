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
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Story;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.tasks.TasksTest;

/**
 * Tests For Api Stories
 * @author npapadopoulos
 */
public class ApiStoryTest extends TasksTest {

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
        for(int i = 0; i < 5; i++) { //Test 5 Random Entries Of The 25 In Total
			int random = generator.nextInt(25);
			AtomEntry entry = stories.get(random);
	        ArticleType result = updater.getArticleType(entry);
	        assertEquals(ArticleType.STORY, result);
		}
    }
 
    /**
     * Make Sure That You Can Persist apiStory.xml And Especially Fields That You Have Only For STORY Articles
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
	@Test
    public void persistAndFieldsTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	updater.entriesToArticles(stories, feed, 0, false);
    	sessionFactory.getCurrentSession().flush();
    	@SuppressWarnings("unchecked")
		List<Story> stories = (List<Story>)(List<?>) articleDao.getByArticleType(ArticleType.STORY, 100, null);
    	Assert.assertTrue(stories.size() >= 29); //It's Related To The maxDepth That You're Using In The Updater. With maxDepth = 1 You Have 29 Related Stories, With maxDepth = 2 You Have 30 (And So On)
    	//Make Sure That You Persist Body Fields
    	for(Story story : stories) {
    		Assert.assertNotNull(story.getBody());
    	}
    }
}