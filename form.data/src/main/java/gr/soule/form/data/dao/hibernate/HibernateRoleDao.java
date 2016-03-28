package gr.soule.form.data.dao.hibernate;

import gr.soule.form.data.dao.RoleDao;
import gr.soule.form.data.entities.Role;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;


/**
 * Role's DAO Implementation
 * @author asoule
 */
@Repository
public class HibernateRoleDao extends HibernateAbstractDao<Role> implements RoleDao {
	
	@Override
	public Role getByName(String name) {
		Query query = getSession().getNamedQuery("getRoleByName");
		query.setParameter("name", name);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (Role) query.uniqueResult();
	}
}
