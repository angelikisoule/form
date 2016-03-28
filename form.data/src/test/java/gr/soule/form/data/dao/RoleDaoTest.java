package gr.soule.form.data.dao;

import gr.soule.form.data.entities.Role;

import java.util.List ;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author asoule
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
