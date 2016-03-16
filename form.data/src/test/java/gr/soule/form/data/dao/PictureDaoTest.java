package gr.media24.mSites.data.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.media24.mSites.data.entities.Picture;

/**
 * @author npapadopoulos
 */
public class PictureDaoTest extends DaoTest {

	@Autowired private PictureDao pictureDao;
    
    /**
     * Picture's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Picture> allPictures = pictureDao.getAll();
    	Assert.assertTrue(allPictures.size() == 4);
    	pictureDao.refresh(allPictures.get(0));
    	Assert.assertEquals("3429416", allPictures.get(0).getEceArticleId());
    }
}
