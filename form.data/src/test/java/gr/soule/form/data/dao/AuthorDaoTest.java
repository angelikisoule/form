package gr.media24.mSites.data.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.media24.mSites.data.entities.Author;

/**
 * @author npapadopoulos
 */
public class AuthorDaoTest extends DaoTest {

	@Autowired private AuthorDao authorDao;

    /**
     * Author's getAll Method
     */
    @Test
    public void getAllTest() {	
    	//There Are Two Authors
    	List<Author> allAuthors = authorDao.getAll();
    	Assert.assertTrue(allAuthors.size() == 2);
    	//Articles Properly Persisted
    	Author author = allAuthors.get(0);
    	authorDao.refresh(author);
    	Assert.assertTrue(author.getArticles().size() == 1);
    	//Testing maxAuthors and Offset Attributes
    	allAuthors = authorDao.getAll(1);
    	Assert.assertTrue(allAuthors.size() == 1);
    	allAuthors = authorDao.getAll(1, 2);
    	Assert.assertTrue(allAuthors.size() == 0);
    }
	
    /**
     * Author's getByName Method
     */
	@Test
	public void getByNameTest() {
		//Method Should Be Case Insensitive
		String authorNameOriginal = "Νίκος Παπαδόπουλος";
		String authorName = "ΝίΚος ΠαΠαδόΠουλος";
		Author author = authorDao.getByName(authorName);
		Assert.assertNotNull(author.getId());
		Assert.assertEquals(author.getName(), authorNameOriginal);
		author = authorDao.getByName(authorName.toLowerCase());
		Assert.assertNotNull(author.getId());
		Assert.assertEquals(author.getName(), authorNameOriginal);
		author = authorDao.getByName(authorName.toUpperCase());
		Assert.assertNotNull(author.getId());
		Assert.assertEquals(author.getName(), authorNameOriginal);
		//Articles Properly Persisted
		authorDao.refresh(author);
		Assert.assertTrue(author.getArticles().size() == 1);
	}
	
	/**
	 * Author's authorExists Method
	 */
	@Ignore
	public void authorExistsTest() {
		//TODO Test: authorExists()
	}
}
