package gr.media24.mSites.data.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.media24.mSites.data.dao.SectionDao;
import gr.media24.mSites.data.entities.Publication;
import gr.media24.mSites.data.entities.Section;

/**
 * Section's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateSectionDao extends HibernateAbstractDao<Section> implements SectionDao  {

	@Override
	public List<Section> getAll(int maxSections) {
		return getAll(maxSections, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Section> getAll(int maxSections, int offset) {
		Query query = getSession().createQuery("FROM Section sections ORDER BY sections.name ASC");
		query.setFirstResult(offset);
		query.setMaxResults(maxSections);
		query.setCacheable(true);
		return (List<Section>) query.list();
	}
	
	@Override
	public Section getByNamePublicationName(String name, String publicationName) {
		Query query = getSession().getNamedQuery("getSectionByNamePublicationName");
		query.setParameter("name", name);
		query.setParameter("publicationName", publicationName);
		query.setCacheable(true);
		return (Section) query.uniqueResult();
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public List<Section> getByNameLikePublicationName(String term, String publicationName) {
		Query query = getSession().getNamedQuery("getSectionByNameLikePublicationName");
		query.setParameter("name", term.toLowerCase() + "%");
		query.setParameter("publicationName", publicationName);
		query.setCacheable(true);
		return (List<Section>) query.list();
	}
	
	@Override
    public Section sectionExists(String name, Publication publication) {
    	Section section = getByNamePublicationName(name, publication.getName());
    	if(section == null) {
    		section = new Section();
    		section.setName(name);
    		section.setPublication(publication);
    		persist(section);
    	}
    	return section;
    }
}