package gr.soule.form.core.service.implementation;


import gr.soule.form.core.service.UserService;
import gr.soule.form.data.dao.UserDetailsDao;
import gr.soule.form.data.entities.User;
import gr.soule.form.data.entities.UserDetailsAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserDetails' Service Implementation
 * @author asoule
 */
@Service("userDetailsServiceAdapter")
@Transactional(readOnly = true)
public class UserDetailsServiceAdapter implements UserDetailsService {

	@Autowired private UserService userService;
	@Autowired private UserDetailsDao userDetailsDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("No such user: " + username);
		}
		else if(user.getRole() == null) {
			throw new UsernameNotFoundException("User: " + username + " has no authorities");
		}
		UserDetailsAdapter userAdapter = new UserDetailsAdapter(user);
		userAdapter.setPassword(userDetailsDao.findPasswordByUsername(username));
		return userAdapter;
	}
}
