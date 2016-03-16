package gr.media24.mSites.data.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.media24.mSites.data.entities.Video;

/**
 * @author npapadopoulos
 */
public class VideoDaoTest extends DaoTest {

	@Autowired private VideoDao videoDao;

    /**
     * Video's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Video> allVideos = videoDao.getAll();
    	Assert.assertTrue(allVideos.size() == 3);
    }
    
    /**
     * Video's RelatedArticles Test
     */
    @Test
    public void videoRelatedArticlesTest() {
    	List<Video> allVideos = videoDao.getAll();
    	Video video = allVideos.get(2);
    	Assert.assertTrue(video.getRelatedArticles().size() == 2);
    }
}