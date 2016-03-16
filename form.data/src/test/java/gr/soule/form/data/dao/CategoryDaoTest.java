package gr.media24.mSites.data.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.media24.mSites.data.entities.Category;

/**
 * @author npapadopoulos
 */
public class CategoryDaoTest extends DaoTest {

	@Autowired private CategoryDao categoryDao;

    /**
     * Category's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Category> allCategories = categoryDao.getAll();
		Assert.assertTrue(allCategories.size() == 4);
		allCategories = categoryDao.getAll(1);
		Assert.assertTrue(allCategories.size() == 1);
		allCategories = categoryDao.getAll(1, 4);
		Assert.assertTrue(allCategories.size() == 0);
    }
    
    /**
     * Category's getByNullGroupName Method
     */
    @Test
    public void getByNullGroupNameTest() {
    	List<Category> categories = categoryDao.getByNullGroupName();
    	Assert.assertTrue(categories.size() == 2);
    }
    
    /**
     * Category's getBySectionUniqueNameGroupNamePublicationName Method
     */
    @Test
    public void getBySectionUniqueNameGroupNamePublicationNameTest() {
    	//celebrities-@main1-ladylike Exists
    	Category category = categoryDao.getBySectionUniqueNameGroupNamePublicationName("celebrities", "@main1", "ladylike");
    	Assert.assertNotNull(category);
    	//celebrities-@main1-sport24 Does Not Exist
    	category = categoryDao.getBySectionUniqueNameGroupNamePublicationName("celebrities", "@main1", "sport24");
    	Assert.assertNull(category);
    }
    
    /**
     * Category's getBySectionUniqueNamePublicationName Method
     */
    @Test
    public void getBySectionUniqueNamePublicationNameTest() {
    	//football-NULL-sport24 Exists
    	Category category = categoryDao.getBySectionUniqueNamePublicationName("football", "sport24");
    	Assert.assertNotNull(category);
    	//celebrities-NULL-sport24 Does Not Exist
    	category = categoryDao.getBySectionUniqueNamePublicationName("celebrities", "sport24");
    	Assert.assertNull(category);
    }
    
    /**
     * Category's categoryExists Method
     */
    @Ignore
    public void categoryExistsTest() {
    	//TODO Test: categoryExists()
    }
}