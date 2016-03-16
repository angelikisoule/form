package gr.media24.mSites.data.dao;

import gr.media24.mSites.data.entities.Role;

/**
 * Role's DAO Interface
 * @author npapadopoulos
 */
public interface RoleDao extends AbstractDao<Role> {
	
	/**
	 * Get Role By The Given Name
	 * @param name Role's Name
	 * @return Role Object
	 */
	Role getByName(String name);
}
