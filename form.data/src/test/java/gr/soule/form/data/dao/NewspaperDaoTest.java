package gr.media24.mSites.data.dao;

import gr.media24.mSites.data.entities.Newspaper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author npapadopoulos
 */
public class NewspaperDaoTest extends DaoTest {

	@Autowired private NewspaperDao newspaperDao;
    
    /**
     * Newspaper's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Newspaper> allNewspapers = newspaperDao.getAll();
    	Assert.assertTrue(allNewspapers.size() == 1);
    }
}
