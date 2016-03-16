package gr.media24.mSites.data.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.media24.mSites.data.entities.Role;
import gr.media24.mSites.data.entities.User;

/**
 * @author npapadopoulos
 */
public class UserDaoTest extends DaoTest {

	@Autowired private UserDao userDao;
	@Autowired private RoleDao roleDao;
    
	/**
	 * User's Password-Aware Persist Or Merge Method
	 */
	@Ignore
	public void persistOrMergeTest() {
		//TODO Test: Password-Aware persistOrMergeTest
	}
	
    /**
     * User's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<User> allUsers = userDao.getAll();
    	Assert.assertTrue(allUsers.size() == 2);
    }
    
    /**
     * User's getByUsername Method
     */
    @Test
    public void getByUsernameTest() {
    	User user = userDao.getByUsername("np");
    	Assert.assertNull(user);
    	user = userDao.getByUsername("npapadopoulos");
    	Assert.assertNotNull(user);
    }
    
    /**
     * User's getByEmail Method
     */
    @Test
    public void getByEmailTest() {
    	User user = userDao.getByEmail("npapadopulos@24media.gr");
    	Assert.assertNull(user);
    	user = userDao.getByEmail("npapadopoulos@24media.gr");
    	Assert.assertNotNull(user);
    }
    
    /**
     * User's getByRole Method
     */
    @Test
    public void getByRoleTest() {
    	Role administrator = roleDao.getByName("Administrator");
    	List<User> administrators = userDao.getByRole(administrator);
    	Assert.assertTrue(administrators.size() == 2);
    }
}
