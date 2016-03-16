package gr.media24.mSites.data.dao;

import gr.media24.mSites.data.entities.Advertorial;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author npapadopoulos
 */
public class AdvertorialDaoTest extends DaoTest {

	@Autowired private AdvertorialDao advertorialDao;
	
    /**
     * Advertorial's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Advertorial> allAdvertorials = advertorialDao.getAll();
    	Assert.assertTrue(allAdvertorials.size() == 1);
    }
}
