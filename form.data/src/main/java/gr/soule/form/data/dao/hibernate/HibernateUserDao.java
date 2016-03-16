package gr.media24.mSites.data.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import gr.media24.mSites.data.dao.UserDao;
import gr.media24.mSites.data.entities.Role;
import gr.media24.mSites.data.entities.User;

/**
 * User's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateUserDao extends HibernateAbstractDao<User> implements UserDao {

	private static final String UPDATE_PASSWORD_QUERY = "UPDATE user SET password = ? WHERE username = ?";
	
	@Autowired private JdbcTemplate jdbcTemplate;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void persistOrMerge(User user, String password) {
		if(user.getId()==null) persist(user); else merge(user);
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		jdbcTemplate.update(UPDATE_PASSWORD_QUERY, encodedPassword, user.getUsername()); //JDBC Call
	}
	
	@Override
	public User getByUsername(String username) {
		Query query = getSession().getNamedQuery("getUserByUsername");
		query.setParameter("username", username);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (User) query.uniqueResult();
	}
	
	@Override
	public User getByEmail(String email) {
		Query query = getSession().getNamedQuery("getUserByEmail");
		query.setParameter("email", email);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (User) query.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getByRole(Role role) {
		Query query = getSession().getNamedQuery("getUsersByRoleName");
		query.setParameter("roleName", role.getName());
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (List<User>) query.list();
	}
}
