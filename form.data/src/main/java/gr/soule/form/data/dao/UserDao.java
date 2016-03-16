package gr.media24.mSites.data.dao;

import java.util.List;

import gr.media24.mSites.data.entities.Role;
import gr.media24.mSites.data.entities.User;

/**
 * User's DAO Interface
 * @author npapadopoulos
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
