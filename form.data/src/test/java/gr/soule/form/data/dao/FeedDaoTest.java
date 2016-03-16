package gr.media24.mSites.data.dao;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.FeedStatus;

/**
 * @author npapadopoulos
 */
public class FeedDaoTest extends DaoTest {

	@Autowired private FeedDao feedDao;

    /**
     * Feed's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Feed> allFeeds = feedDao.getAll();
    	Assert.assertTrue(allFeeds.size() == 3);
    }
    
    @Test
    public void getByUrlParamsTest() {
    	String url = "http://content.iballs.gr/develop/articles";
        String params = "?sections=3671&profile=bWljcm9zb2Z0IGZlZWRz&types=news&view=microsoft%2Fv1%2Flatest-atom";
        Feed feed = feedDao.getByUrlParams(url, "");
    	Assert.assertNull(feed);
    	feed = feedDao.getByUrlParams(url, params);
    	feedDao.refresh(feed);
    	Assert.assertNotNull(feed);
    	Assert.assertNotNull(feed.getCategory()); //Category Properly Persisted
    }
    
    @Test
    public void getRandomByPublicationNameAndFeedFlagsTest() {
    	Feed feed = feedDao.getRandomByPublicationNameAndFeedFlags("ladylike", Arrays.asList(FeedFlag.ESCENIC));
    	Assert.assertNotNull(feed);
    	
    	feed = feedDao.getRandomByPublicationNameAndFeedFlags("sport24", Arrays.asList(FeedFlag.ESCENIC));
    	Assert.assertNull(feed);
    }
    
    @Test
    public void getByFeedFlagTest() {
    	List<Feed> feeds = feedDao.getByFeedFlag(FeedFlag.ESCENIC);
    	Assert.assertTrue(feeds.size() == 2);
    }
    
    @Test
    public void getByFeedFlagsAndFeedStatusTest() {
    	List<Feed> feeds = feedDao.getByFeedFlagsAndFeedStatus(Arrays.asList(FeedFlag.ESCENIC, FeedFlag.API_SECTIONS), FeedStatus.ENABLED);
    	Assert.assertTrue(feeds.size() == 3);
    }
}
