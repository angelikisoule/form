package gr.media24.mSites.core.service.implementation;

import gr.media24.mSites.core.service.UserService;
import gr.media24.mSites.data.dao.UserDetailsDao;
import gr.media24.mSites.data.entities.User;
import gr.media24.mSites.data.entities.UserDetailsAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserDetails' Service Implementation
 * @author npapadopoulos
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
