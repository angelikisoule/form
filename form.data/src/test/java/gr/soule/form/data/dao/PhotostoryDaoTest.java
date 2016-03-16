package gr.media24.mSites.data.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.media24.mSites.data.entities.Photostory;

/**
 * @author npapadopoulos
 */
public class PhotostoryDaoTest extends DaoTest {

	@Autowired private PhotostoryDao photostoryDao;
    
    /**
     * Photostory's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Photostory> allPhotostories = photostoryDao.getAll();
    	Assert.assertTrue(allPhotostories.size() == 1);
    }
 
    /**
     * Photostory's Related Pictures Test
     */
    @Test
    public void photostoryPictureTest() {
    	List<Photostory> allPhotostories = photostoryDao.getAll();
    	Photostory photostory = allPhotostories.get(0);
    	Assert.assertTrue(photostory.getRelatedArticles().size() == 1);
    }
}