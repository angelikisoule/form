package gr.soule.form.data.dao;

import gr.soule.form.data.entities.Role;

/**
 * Role's DAO Interface
 * @author asoule
 */
public interface RoleDao extends AbstractDao<Role> {
	
	/**
	 * Get Role By The Given Name
	 * @param name Role's Name
	 * @return Role Object
	 */
	Role getByName(String name);
}
