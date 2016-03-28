package gr.soule.form.data.dao;

/**
 * UserDetails' DAO Interface
 * @author asoule
 */
public interface UserDetailsDao {

	/**
	 * Given A Username Find User's Password Via A JDBC Call To Avoid Having Sensitive Information Hang Around With User Objects
	 * @param username User's Username
	 * @return User's Password
	 */
	String findPasswordByUsername(String username);
}
