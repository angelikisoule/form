package gr.media24.mSites.data.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.media24.mSites.data.dao.AuthorDao;
import gr.media24.mSites.data.entities.Author;

/**
 * Author's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateAuthorDao extends HibernateAbstractDao<Author> implements AuthorDao {

	@Override
    public Author authorExists(String name) {
    	Author author = getByName(name);
    	if(author == null) {
    		author = new Author();
    		author.setName(name);
    		persist(author);
    	}
    	return author;
    }
	
	@Override
	public Author getByName(String name) {
		Query query = getSession().getNamedQuery("getAuthorByName");
		query.setParameter("name", (name!=null) ? name.toLowerCase() : null);
		query.setCacheable(true);
		return (Author) query.uniqueResult();
	}

	@Override
	public List<Author> getAll(int maxAuthors) {
		return getAll(maxAuthors, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Author> getAll(int maxAuthors, int offset) {
		Query query = getSession().createQuery("FROM Author authors ORDER BY authors.name ASC");
		query.setFirstResult(offset);
		query.setMaxResults(maxAuthors);
		query.setCacheable(true);
		return (List<Author>) query.list();
	}
}
