package gr.media24.mSites.data.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import gr.media24.mSites.data.dao.UserDetailsDao;

/**
 * UserDetails' DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class JdbcUserDetailsDao implements UserDetailsDao {

	@Autowired private JdbcTemplate jdbcTemplate;
	
	private static final String FIND_PASSWORD_QUERY = "SELECT password FROM user WHERE username = ?";
	
	@Override
	public String findPasswordByUsername(String username) {
		return jdbcTemplate.queryForObject(FIND_PASSWORD_QUERY, new Object[] { username }, String.class);
	}
}
