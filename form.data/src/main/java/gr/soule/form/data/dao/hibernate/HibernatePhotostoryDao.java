package gr.media24.mSites.data.dao.hibernate;

import gr.media24.mSites.data.dao.PhotostoryDao;
import gr.media24.mSites.data.entities.Photostory;

import org.springframework.stereotype.Repository;

/**
 * Photostory's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernatePhotostoryDao extends HibernateAbstractDao<Photostory> implements PhotostoryDao {

}
