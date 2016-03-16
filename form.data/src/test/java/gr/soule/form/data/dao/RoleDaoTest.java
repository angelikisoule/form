package gr.media24.mSites.data.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.media24.mSites.data.entities.Role;

/**
 * @author npapadopoulos
 */
public class RoleDaoTest extends DaoTest {

	@Autowired private RoleDao roleDao;
    
    /**
     * Role's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Role> allRoles = roleDao.getAll();
    	Assert.assertTrue(allRoles.size() == 2);
    }
    
    /**
     * Role's getByName Method
     */
    @Test
    public void getByNameTest() {
    	Role role = roleDao.getByName("Admin");
    	Assert.assertNull(role);
    	
    	role = roleDao.getByName("Administrator");
    	roleDao.refresh(role);
    	Assert.assertNotNull(role);
    	Assert.assertTrue(role.getUsers().size() == 2); //Users Properly Persisted
    }
}
