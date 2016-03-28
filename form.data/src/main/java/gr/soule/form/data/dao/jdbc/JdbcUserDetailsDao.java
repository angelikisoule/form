package gr.soule.form.data.dao.jdbc;

import gr.soule.form.data.dao.UserDetailsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * UserDetails' DAO Implementation
 * @author asoule
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
