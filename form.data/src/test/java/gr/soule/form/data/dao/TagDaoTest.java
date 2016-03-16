package gr.media24.mSites.data.dao;

import gr.media24.mSites.data.entities.Tag;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author npapadopoulos
 */
public class TagDaoTest extends DaoTest {

	@Autowired private TagDao tagDao;
	
	/**
     * Tag's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Tag> allTags = tagDao.getAll();
    	Assert.assertTrue(allTags.size() == 2);
    	//No Results If You Set An offset Larger Than The Size
    	allTags = tagDao.getAll(1, 3);
    	Assert.assertTrue(allTags.size() == 0);
    }

    /**
     * Tag's getByNamePublicationName Method
     */
    @Test
    public void getByNamePublicationNameTest() {
    	List<Tag> tag = tagDao.getByNamePublicationName("ronaldo", "sport24");
    	Assert.assertNotNull(tag);
    	//The Same Tag Does Not Exist In 'ladylike' Publication
    	tag = tagDao.getByNamePublicationName("ronaldo", "ladylike");
    }

    /**
     * Tag's getByNameDisplayNamePublicationName Method
     */
    @Test
    public void getByNameDisplayNamePublicationNameTest() {
    	Tag tag = tagDao.getByNameDisplayNamePublicationName("rihanna", "Rihanna", "ladylike");
    	Assert.assertNotNull(tag);
    }
    
    /**
     * Tag's tagExists Method
     */
    @Ignore
    public void tagExistsTest() {
    	//TODO Test: tagExists()
    }
}
