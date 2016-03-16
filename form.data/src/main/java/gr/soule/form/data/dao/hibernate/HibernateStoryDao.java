package gr.media24.mSites.data.dao.hibernate;

import gr.media24.mSites.data.dao.StoryDao;
import gr.media24.mSites.data.entities.Story;

import org.springframework.stereotype.Repository;

/**
 * Story's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateStoryDao extends HibernateAbstractDao<Story> implements StoryDao {

}
