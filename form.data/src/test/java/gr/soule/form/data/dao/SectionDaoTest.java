package gr.media24.mSites.data.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.media24.mSites.data.entities.Section;

/**
 * @author npapadopoulos
 */
public class SectionDaoTest extends DaoTest  {

	@Autowired private SectionDao sectionDao;

    /**
     * Section's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Section> allSections = sectionDao.getAll();
    	Assert.assertTrue(allSections.size() == 2);
    }
    
    /**
     * Section's getByNamePublicationName Method
     */
    @Test
    public void getByName() {
    	//newsletter@topStories2-ladylike Does Not Exist
    	Section section = sectionDao.getByNamePublicationName("newsletter@topStories2", "ladylike");
    	Assert.assertNull(section);
    	//newsletter@topStories1 Exists And Has 3 Articles
    	section = sectionDao.getByNamePublicationName("newsletter@topStories1", "ladylike");
    	sectionDao.refresh(section);
    	Assert.assertNotNull(section);
    	Assert.assertTrue(section.getArticles().size() == 3);
    }
    
    /**
     * Section's getByNameLikePublicationName Method
     */
    @Test
    public void getByNameLikePublicationNameTest() {
    	List<Section> sections = sectionDao.getByNameLikePublicationName("newsletter", "ladylike");
    	Assert.assertTrue(sections.size() == 2);
    	sections = sectionDao.getByNameLikePublicationName("NewsLetter", "ladylike"); //Case Insensitive
    	Assert.assertTrue(sections.size() == 2);
    }
    
    /**
     * Section's sectionExists Method
     */
    @Ignore
    public void sectionExistsTest() {
    	//TODO Test: sectionExists()
    }
}
