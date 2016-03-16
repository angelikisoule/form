package gr.media24.mSites.data.dao.hibernate;

import gr.media24.mSites.data.dao.PictureDao;
import gr.media24.mSites.data.entities.Picture;

import org.springframework.stereotype.Repository;

/**
 * Picture's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernatePictureDao extends HibernateAbstractDao<Picture> implements PictureDao {

}
