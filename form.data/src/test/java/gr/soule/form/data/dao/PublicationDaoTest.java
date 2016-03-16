package gr.media24.mSites.data.dao;

import gr.media24.mSites.data.entities.Publication;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author npapadopoulos
 */
public class PublicationDaoTest extends DaoTest {

	@Autowired private PublicationDao publicationDao;
	
	/**
	 * Publication's getAll Method
	 */
	@Test
	public void getAllTest() {
		List<Publication> allPublications = publicationDao.getAll();
		Assert.assertTrue(allPublications.size() == 2);
	}
	
	/**
	 * Publication's getByName Method
	 */
	@Test
	public void getByNameTest() {
		Publication publication = publicationDao.getByName("ladylike");
		Assert.assertNotNull(publication);
		publication = publicationDao.getByName("ladygaga");
		Assert.assertNull(publication);
	}
}
