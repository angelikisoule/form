package gr.media24.mSites.data.dao.hibernate;

import java.util.List;

import gr.media24.mSites.data.dao.TagDao;
import gr.media24.mSites.data.entities.Publication;
import gr.media24.mSites.data.entities.Tag;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Tag's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateTagDao extends HibernateAbstractDao<Tag> implements TagDao {

	@Override
	public List<Tag> getAll(int maxTags) {
		return getAll(maxTags, 0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Tag> getAll(int maxTags, int offset) {
		Query query = getSession().createQuery("FROM Tag tags ORDER BY tags.name ASC");
		query.setFirstResult(offset);
		query.setMaxResults(maxTags);
		query.setCacheable(true);
		return (List<Tag>) query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Tag> getByNamePublicationName(String name, String publicationName) {
		Query query = getSession().getNamedQuery("getTagByNamePublicationName");
		query.setParameter("name", name);
		query.setParameter("publicationName", publicationName);
		query.setCacheable(true);
		return (List<Tag>) query.list();
	}
	
	@Override
	public Tag getByNameDisplayNamePublicationName(String name, String displayName, String publicationName) {
		Query query = getSession().getNamedQuery("getTagByNameDisplayNamePublicationName");
		query.setParameter("name", name);
		query.setParameter("displayName", displayName);
		query.setParameter("publicationName", publicationName);
		query.setCacheable(true);
		return (Tag) query.uniqueResult();
	}

	@Override
	public Tag tagExists(String name, String displayName, Publication publication) {
		Tag tag = getByNameDisplayNamePublicationName(name, displayName, publication.getName());
		if(tag==null) {
			tag = new Tag();
			tag.setName(name);
			tag.setDisplayName(displayName);
			tag.setPublication(publication);
			persistOrMerge(tag); //Persist	
		}
		return tag;
	}
}
