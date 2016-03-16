package gr.media24.mSites.data.dao.hibernate;

import gr.media24.mSites.data.dao.PublicationDao;
import gr.media24.mSites.data.entities.Publication;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Publication's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernatePublicationDao extends HibernateAbstractDao<Publication> implements PublicationDao {

	@Override
	public Publication getByName(String name) {
		Query query = getSession().getNamedQuery("getPublicationByName");
		query.setParameter("name", name);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (Publication) query.uniqueResult();
	}
}
