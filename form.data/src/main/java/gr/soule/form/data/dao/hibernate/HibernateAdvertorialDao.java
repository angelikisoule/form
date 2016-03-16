package gr.media24.mSites.data.dao.hibernate;

import gr.media24.mSites.data.dao.AdvertorialDao;
import gr.media24.mSites.data.entities.Advertorial;

import org.springframework.stereotype.Repository;

/**
 * Advertorial's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateAdvertorialDao extends HibernateAbstractDao<Advertorial> implements AdvertorialDao {

}
