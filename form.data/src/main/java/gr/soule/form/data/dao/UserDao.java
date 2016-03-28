package gr.soule.form.data.dao;

import gr.soule.form.data.entities.Role;
import gr.soule.form.data.entities.User;

import java.util.List;


/**
 * User's DAO Interface
 * @author asoule
 */
public interface UserDao extends AbstractDao<User> {

	/**
	 * Password-Aware persistOrMerge Method For User Object
	 * @param user User Object
	 * @param password User's Password
	 */
	void persistOrMerge(User user, String password);
	
	/**
	 * Get User By The Given Username
	 * @param username User's Username
	 * @return User Object
	 */
	User getByUsername(String username);
	
	/**
	 * Get User By The Given Email
	 * @param email User's Email
	 * @return User Object
	 */
	User getByEmail(String email);
	
	/**
	 * Get Users By The Give Role
	 * @param role User's Role
	 * @return List Of User Objects
	 */
	List<User> getByRole(Role role);
}
