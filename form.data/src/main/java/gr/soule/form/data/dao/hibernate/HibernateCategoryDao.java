package gr.media24.mSites.data.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import gr.media24.mSites.data.dao.CategoryDao;
import gr.media24.mSites.data.dao.PublicationDao;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Publication;

/**
 * Category's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateCategoryDao extends HibernateAbstractDao<Category> implements CategoryDao {

	@Autowired private PublicationDao publicationDao;
	
	@Override
	public List<Category> getAll(int maxCategories) {
		return getAll(maxCategories, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll(int maxCategories, int offset) {
		Query query = getSession().createQuery("FROM Category categories ORDER BY categories.publication.name ASC, categories.name ASC, categories.groupName ASC");
		query.setFirstResult(offset);
		query.setMaxResults(maxCategories);
		query.setCacheable(true);
		return (List<Category>) query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> getByNullGroupName() {
		Query query = getSession().getNamedQuery("getByNullGroupName");
		return (List<Category>) query.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Category> getBySectionUniqueNameLikePublicationName(String term, String publicationName) {
		Query query = getSession().getNamedQuery("getCategoryBySectionUniqueNameLikePublicationName");
		query.setParameter("sectionUniqueName", "%" + term + "%");
		query.setParameter("publicationName", publicationName);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (List<Category>) query.list();
	}
	
	@Override
	public Category getBySectionUniqueNameGroupNamePublicationName(String sectionUniqueName, String groupName, String publicationName) {
		Query query = getSession().getNamedQuery("getCategoryBySectionUniqueNameGroupNamePublicationName");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("groupName", groupName);
		query.setParameter("publicationName", publicationName);
		query.setCacheable(true);
		return (Category) query.uniqueResult();
	}

	@Override
	public Category getBySectionUniqueNamePublicationName(String sectionUniqueName, String publicationName) {
		Query query = getSession().getNamedQuery("getCategoryBySectionUniqueNamePublicationName");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("publicationName", publicationName);
		query.setCacheable(true);
		return (Category) query.uniqueResult();
	}
	
	@Override
    public Category categoryExists(String name, String sectionUniqueName, String groupName, Publication publication) {
       	Category category;
        if(groupName!=null) {
            category = getBySectionUniqueNameGroupNamePublicationName(sectionUniqueName, groupName, publication.getName());
        }
        else {
            category = getBySectionUniqueNamePublicationName(sectionUniqueName, publication.getName());
        }
        if(category==null) {
        	category = new Category();
        	category.setName(name);
        	category.setSectionUniqueName(sectionUniqueName);
        	category.setGroupName(groupName);
        	category.setPublication(publication);
        	persistOrMerge(category); //Persist
        }
        else {
        	if(!name.equals(category.getName())) { //Category Name May Have Change
                category.setName(name);
                persistOrMerge(category); //Merge
        	}
        }
        return category;
    }
}
