package gr.media24.mSites.core.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import gr.media24.mSites.core.service.UserService;
import gr.media24.mSites.data.dao.UserDao;
import gr.media24.mSites.data.entities.User;

/**
 * User's Service Implementation
 * @author npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImplementation implements UserService {

	@Autowired private UserDao userDao;

	@Override
	@Transactional(readOnly = false)
	public void saveUser(User user, String password, Errors errors) {
		validateForm(user, errors);
		if(!errors.hasErrors()) userDao.persistOrMerge(user, password);
	}

	@Override
	public User getUser(Long id) {
		return userDao.get(id);
	}
	
	@Override
	public User getUserByUsername(String username) {
		return userDao.getByUsername(username);
	}
	
	@Override
	public List<User> getUsers() {
		return (List<User>) userDao.getAll();
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteUser(Long id) {
		userDao.deleteById(id);
	}
	
	/**
	 * Custom Validations For Form Fields
	 * @param user User Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateForm(User user, Errors errors) {
		validateUsername(user, errors);
		validateEmail(user, errors);
	}
	
	/**
	 * Validate Uniqueness Of User's Username
	 * @param user User Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateUsername(User user, Errors errors) {
		User existing = userDao.getByUsername(user.getUsername());
		if(existing!=null && !existing.getId().equals(user.getId())) { //Duplicate Username
			errors.rejectValue("username", "error.duplicate");
		}
	}
	
	/**
	 * Validate Uniqueness Of User's Email
	 * @param user User Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateEmail(User user, Errors errors) {
		User existing = userDao.getByEmail(user.getEmail());
		if(existing!=null && !existing.getId().equals(user.getId())) { //Duplicate Email
			errors.rejectValue("email", "error.duplicate");
		}
	}
}
