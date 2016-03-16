package gr.media24.mSites.data.dao.hibernate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.media24.mSites.data.dao.RoleDao;
import gr.media24.mSites.data.entities.Role;

/**
 * Role's DAO Implementation
 * @author npapadopoulos
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
