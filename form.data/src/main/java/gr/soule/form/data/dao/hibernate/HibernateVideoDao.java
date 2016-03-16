package gr.media24.mSites.data.dao.hibernate;

import org.springframework.stereotype.Repository;

import gr.media24.mSites.data.dao.VideoDao;
import gr.media24.mSites.data.entities.Video;

/**
 * Video's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateVideoDao extends HibernateAbstractDao<Video> implements VideoDao {

}
