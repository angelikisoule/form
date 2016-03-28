package gr.soule.form.core.service;

import gr.soule.form.data.entities.User;

import java.util.List;

import org.springframework.validation.Errors;

/**
 * User's Service Interface
 * @author asoule
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
