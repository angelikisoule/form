package gr.media24.mSites.core.service;

import java.util.List;

import org.springframework.validation.Errors;

import gr.media24.mSites.data.entities.User;

/**
 * User's Service Interface
 * @author npapadopoulos
 */
public interface UserService {
	
	/**
	 * Save User Object
	 * @param user User Object
	 * @param password User's Password
	 * @param errors BindingResult Errors Of User Form
	 */
	void saveUser(User user, String password, Errors errors);
	
	/**
	 * Get User Object Given It's Id
	 * @param id User's Id
	 * @return User Object
	 */
	User getUser(Long id);
	
	/**
	 * Get User Object Given It's Username
	 * @param username User's Username
	 * @return User Object
	 */
	User getUserByUsername(String username);
	
	/**
	 * Get All User Objects
	 * @return List Of Users
	 */
	List<User> getUsers();
	
	/**
	 * Delete User Object Given It's Id
	 * @param id User's Id
	 */
	void deleteUser(Long id);
}
