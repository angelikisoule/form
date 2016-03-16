package gr.media24.mSites.data.dao.hibernate;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.EnclosureComment;

/**
 * Article's DAO Implementation
 * @author npapadopoulos, asoule
 */
@Repository
public class HibernateArticleDao extends HibernateAbstractDao<Article> implements ArticleDao {
	
	@Override
	public Article getByEceArticleId(String eceArticleId) {
		Query query = getSession().getNamedQuery("getArticleByEceArticleId");
		query.setParameter("eceArticleId", eceArticleId);
		query.setCacheable(true);
		return (Article) query.uniqueResult();
	}
	
	@Override
	public List<Article> getAll(int maxArticles) {
		return getAll(maxArticles, 0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getAll(int maxArticles, int offset) {
		List<Long> ids = getAllPagination(maxArticles, offset);
		if(ids.size()>0) {
			Session session = getSession();
			session.enableFilter("homeCategory"); //Eagerly FETCH Only Home Categories
			Query query = session.createQuery(	"SELECT DISTINCT(articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN FETCH articles.categories categories " +
												"WHERE articles.id IN (:ids) " +
												"ORDER BY articles.datePublished DESC");
			query.setParameterList("ids", ids);
			query.setCacheable(false); //Can Not Cache Due To JOIN FETCH
			return (List<Article>) query.list();
		}
		else {
			return null;
		}
	}

	@Override
	public Long countAll() {
		//No Need To FETCH Associations Or Enable A Filter For Count Queries
		Query query = getSession().createQuery("SELECT COUNT(articles) FROM Article articles");
		query.setCacheable(true);
		return (Long) query.uniqueResult();
	}

	/*
	 * The Effect Of Applying setMaxResults() or setFirstResult() To A Query Involving Fetch Joins Over Collections Is Undefined. It Varies 
	 * From Database To Database, But Hibernate Usually Does The Paging In Memory Instead Of At The Database Query Level. So, Since This Is 
	 * Something We Don't Want, We Use A Separate Query To Get The Ids Of The Desired Objects, And We Pass These Ids To The Fetch Join Query
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getAllPagination(int maxArticles, int offset) {
		Query query = getSession().createQuery("SELECT articles.id FROM Article articles ORDER BY articles.datePublished DESC");
		query.setFirstResult(offset);
		query.setMaxResults(maxArticles);
		query.setCacheable(true);
		return (List<Long>) query.list();
	}

	@Override
	public List<Article> getBySectionUniqueName(String sectionUniqueName, String publicationName, int maxArticles, String orderBy) {
		return getBySectionUniqueName(sectionUniqueName, publicationName, maxArticles, 0, orderBy);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getBySectionUniqueName(String sectionUniqueName, String publicationName, int maxArticles, int offset, String orderBy) {
		List<Long> ids = getBySectionUniqueNamePagination(sectionUniqueName, publicationName, maxArticles, offset, orderBy);
		if(ids.size()>0) {
			Session session = getSession();
			session.enableFilter("relatedByEnclosureComment").setParameter("enclosureComment", EnclosureComment.MAIN.ordinal()); //Eagerly FETCH authors And relatedArticles Filtered By enclosureComment = MAIN
			Query query = session.createQuery(	"SELECT DISTINCT(articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"LEFT OUTER JOIN FETCH articles.relatedArticles related " +
												"LEFT OUTER JOIN FETCH articles.authors authors " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"AND articles.id IN (:ids) " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
			query.setParameter("sectionUniqueName", sectionUniqueName);
			query.setParameter("publicationName", publicationName);
			query.setParameter("articleState", ArticleState.ARCHIVED);
			query.setParameterList("ids", ids);
			query.setCacheable(false); //Can Not Cache Due To JOIN FETCH
			return (List<Article>) query.list();
		}
		else {
			return null;
		}
	}
	
	@Override
	public Long countBySectionUniqueName(String sectionUniqueName, String publicationName) {
		//No Need To FETCH Associations Or Enable A Filter For Count Queries
		Query query = getSession().createQuery(	"SELECT COUNT(DISTINCT articles) " +
												"FROM Article articles " +
                								"LEFT OUTER JOIN articles.categories categories " +
                								"LEFT OUTER JOIN categories.publication publication " +
                								"WHERE categories.sectionUniqueName=:sectionUniqueName " +
                								"AND publication.name=:publicationName " +
                								"AND articles.articleState<>:articleState");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setCacheable(true);
		return (Long) query.uniqueResult();
	}
	
	/*
	 * The Effect Of Applying setMaxResults() or setFirstResult() To A Query Involving Fetch Joins Over Collections Is Undefined. It Varies 
	 * From Database To Database, But Hibernate Usually Does The Paging In Memory Instead Of At The Database Query Level. So, Since This Is 
	 * Something We Don't Want, We Use A Separate Query To Get The Ids Of The Desired Objects, And We Pass These Ids To The Fetch Join Query
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getBySectionUniqueNamePagination(String sectionUniqueName, String publicationName, int maxArticles, int offset, String orderBy) {
		Query query = getSession().createQuery(	"SELECT articles.id " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setFirstResult(offset);
		query.setMaxResults(maxArticles);
		query.setCacheable(true);
		return (List<Long>) query.list();
	}
	
	@Override
	public List<Article> getByArticleType(ArticleType articleType, int maxArticles, String orderBy) {
		List<ArticleType> articleTypes = Arrays.asList(articleType);
		return getByArticleType(articleTypes, maxArticles, 0, orderBy);
	}
	
	@Override
	public List<Article> getByArticleType(List<ArticleType> articleTypes, int maxArticles, String orderBy) {
		return getByArticleType(articleTypes, maxArticles, 0, orderBy);
	}
	
	@Override
	public List<Article> getByArticleType(ArticleType articleType, int maxArticles, int offset, String orderBy) {
		List<ArticleType> articleTypes = Arrays.asList(articleType);
		return getByArticleType(articleTypes, maxArticles, offset, orderBy);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getByArticleType(List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy) {
		List<Long> ids = getByArticleTypePagination(articleTypes, maxArticles, offset, orderBy);
		if(ids.size()>0) {
			Session session = getSession();
			//Eagerly FETCH categories (Filter Only Home, Although It Might Be Too Strict), authors And relatedArticles Filtered By enclosureComment = MAIN
			session.enableFilter("homeCategory");
			session.enableFilter("relatedByEnclosureComment").setParameter("enclosureComment", EnclosureComment.MAIN.ordinal());
			Query query = session.createQuery(	"SELECT DISTINCT(articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN FETCH articles.categories categories " +
												"LEFT OUTER JOIN FETCH articles.relatedArticles related " +
												"LEFT OUTER JOIN FETCH articles.authors authors " +
												"WHERE articles.articleState<>:articleState " +
												"AND articles.articleType IN (:articleType) " +
												"AND articles.id IN (:ids) " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
			query.setParameter("articleState", ArticleState.ARCHIVED);
			query.setParameterList("articleType", articleTypes);
			query.setParameterList("ids", ids);
			query.setCacheable(false); //Can Not Cache Due To JOIN FETCH
			return (List<Article>) query.list();
		}
		else {
			return null;
		}
	}

	@Override
	public Long countByArticleType(ArticleType articleType) {
		List<ArticleType> articleTypes = Arrays.asList(articleType);
		return countByArticleType(articleTypes);
	}
	
	@Override
	public Long countByArticleType(List<ArticleType> articleTypes) {
		//No Need To FETCH Associations Or Enable A Filter For Count Queries
		Query query = getSession().createQuery(	"SELECT COUNT(DISTINCT articles) " +
												"FROM Article articles " +
												"WHERE articles.articleState<>:articleState " +
												"AND articles.articleType IN (:articleType)");
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setParameterList("articleType", articleTypes);
		query.setCacheable(true);
		return (Long) query.uniqueResult();
	}
	
	/*
	 * The Effect Of Applying setMaxResults() or setFirstResult() To A Query Involving Fetch Joins Over Collections Is Undefined. It Varies 
	 * From Database To Database, But Hibernate Usually Does The Paging In Memory Instead Of At The Database Query Level. So, Since This Is 
	 * Something We Don't Want, We Use A Separate Query To Get The Ids Of The Desired Objects, And We Pass These Ids To The Fetch Join Query
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getByArticleTypePagination(List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy) {
		Query query = getSession().createQuery(	"SELECT articles.id " +
												"FROM Article articles " +
												"WHERE articles.articleState<>:articleState " +
												"AND articles.articleType IN (:articleType) " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setParameterList("articleType", articleTypes);
		query.setFirstResult(offset);
		query.setMaxResults(maxArticles);
		query.setCacheable(true);
		return (List<Long>) query.list();
	}
	
	@Override
	public List<Article> getByArticleState(ArticleState articleState, int maxArticles) {
		return getByArticleState(articleState, maxArticles, 0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getByArticleState(ArticleState articleState, int maxArticles, int offset) {
		List<Long> ids = getByArticleStatePagination(articleState, maxArticles, offset);
		if(ids.size()>0) {
			Session session = getSession();
			//Eagerly FETCH categories (Filter Only Home, Although It Might Be Too Strict), authors And relatedArticles Filtered By enclosureComment = MAIN
	    	session.enableFilter("homeCategory");
			session.enableFilter("relatedByEnclosureComment").setParameter("enclosureComment", EnclosureComment.MAIN.ordinal());
			Query query = session.createQuery(	"SELECT DISTINCT(articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN FETCH articles.categories categories " +
												"LEFT OUTER JOIN FETCH articles.relatedArticles related " +
												"LEFT OUTER JOIN FETCH articles.authors authors " +
												"WHERE articles.articleState=:articleState " +
												"AND articles.id IN (:ids) " +
												"ORDER BY articles.datePublished DESC");
			query.setParameter("articleState", articleState);
			query.setParameterList("ids", ids);
			query.setCacheable(false); //Can Not Cache Due To JOIN FETCH
			return (List<Article>) query.list();
		}
		else {
			return null;
		}
	}
	
	@Override
	public Long countByArticleState(ArticleState articleState) {
		//No Need To FETCH Associations Or Enable A Filter For Count Queries
		Query query = getSession().createQuery(	"SELECT COUNT(DISTINCT articles) " +
												"FROM Article articles " +
												"WHERE articles.articleState=:articleState");
		query.setParameter("articleState", articleState);
		query.setCacheable(true);
		return (Long) query.uniqueResult();
	}

	/*
	 * The Effect Of Applying setMaxResults() or setFirstResult() To A Query Involving Fetch Joins Over Collections Is Undefined. It Varies 
	 * From Database To Database, But Hibernate Usually Does The Paging In Memory Instead Of At The Database Query Level. So, Since This Is 
	 * Something We Don't Want, We Use A Separate Query To Get The Ids Of The Desired Objects, And We Pass These Ids To The Fetch Join Query
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getByArticleStatePagination(ArticleState articleState, int maxArticles, int offset) {
		Query query = getSession().createQuery(	"SELECT articles.id " +
												"FROM Article articles " +
												"WHERE articles.articleState=:articleState " +
												"ORDER BY articles.datePublished DESC");
		query.setParameter("articleState", articleState);
		query.setFirstResult(offset);
		query.setMaxResults(maxArticles);
		query.setCacheable(true);
		return (List<Long>) query.list();
	}

	@Override
	public List<Article> getBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName, int maxArticles, String orderBy) {
		return getBySectionUniqueNameGroupName(sectionUniqueName, groupName, publicationName, maxArticles, 0, orderBy);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName, int maxArticles, int offset, String orderBy) {
		List<Long> ids = getBySectionUniqueNameGroupNamePagination(sectionUniqueName, groupName, publicationName, maxArticles, offset, orderBy);
		if(ids.size()>0) {
			Session session = getSession();
			//Eagerly FETCH authors And relatedArticles Filtered By enclosureComment = MAIN
	    	session.enableFilter("relatedByEnclosureComment").setParameter("enclosureComment", EnclosureComment.MAIN.ordinal());
			Query query = session.createQuery(	"SELECT DISTINCT(articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"LEFT OUTER JOIN FETCH articles.relatedArticles related " +
												"LEFT OUTER JOIN FETCH articles.authors authors " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND categories.groupName=:groupName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"AND articles.id IN (:ids) " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
			query.setParameter("sectionUniqueName", sectionUniqueName);
			query.setParameter("groupName", groupName);
			query.setParameter("publicationName", publicationName);
			query.setParameter("articleState", ArticleState.ARCHIVED);
			query.setParameterList("ids", ids);
			query.setCacheable(false); //Can Not Cache Due To JOIN FETCH
			return (List<Article>) query.list();
		}
		else {
			return null;
		}
	}
	
	@Override
	public Long countBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName) {
		//No Need To FETCH Associations Or Enable A Filter For Count Queries
		Query query = getSession().createQuery( "SELECT COUNT(DISTINCT articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND categories.groupName=:groupName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("groupName", groupName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setCacheable(true);
		return (Long) query.uniqueResult();
	}
	
	/*
	 * The Effect Of Applying setMaxResults() or setFirstResult() To A Query Involving Fetch Joins Over Collections Is Undefined. It Varies 
	 * From Database To Database, But Hibernate Usually Does The Paging In Memory Instead Of At The Database Query Level. So, Since This Is 
	 * Something We Don't Want, We Use A Separate Query To Get The Ids Of The Desired Objects, And We Pass These Ids To The Fetch Join Query
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getBySectionUniqueNameGroupNamePagination(String sectionUniqueName, String groupName, String publicationName, int maxArticles, int offset, String orderBy) {
		Query query = getSession().createQuery(	"SELECT articles.id " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND categories.groupName=:groupName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("groupName", groupName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setFirstResult(offset);
		query.setMaxResults(maxArticles);
		query.setCacheable(true);
		return (List<Long>) query.list();
	}

	@Override
	public LinkedHashSet<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, boolean lazy) {
		List<ArticleType> articleTypes = Arrays.asList(articleType);
		return getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleTypes, maxArticles, lazy);
	}

	/*
	 * Returns The Latest maxArticles Of A Section Without Fetching Related Associations And Without Using The DISTINCT Keyword. 
	 * The Query Is Cacheable (No JOIN FETCH) And We Can Use It Whenever We Need Only The Basic Fields Of The Latest Catergory Articles.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public LinkedHashSet<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, boolean lazy) {
		Session session = getSession();
		List<Long> ids = getBySectionUniqueNameArticleTypePagination(sectionUniqueName, publicationName, articleTypes, maxArticles, 0, null);
		if(ids.size()>0) {
			Query query = session.createQuery(	"SELECT articles " +
												"FROM Article articles " +
								    			"LEFT OUTER JOIN articles.categories categories " +
								    			"LEFT OUTER JOIN categories.publication publication " +
								    			"WHERE categories.sectionUniqueName=:sectionUniqueName " +
								    			"AND publication.name=:publicationName " +
								    			"AND articles.articleState<>:articleState " +
								    			"AND articles.articleType IN (:articleType) " +
								    			"AND articles.id IN (:ids) " +
								    			"ORDER BY articles.datePublished DESC");
			query.setParameter("sectionUniqueName", sectionUniqueName);
			query.setParameter("publicationName", publicationName);
			query.setParameter("articleState", ArticleState.ARCHIVED);
			query.setParameterList("articleType", articleTypes);
			query.setParameterList("ids", ids);
			query.setCacheable(true);
			return new LinkedHashSet<Article>(query.list()); //A Trick To Get Distinct Articles Without Using DISTINCT Keyword In The HQL
		}
		else
			return null;
	}

	@Override
	public List<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, String orderBy) {
		List<ArticleType> articleTypes = Arrays.asList(articleType);
		return getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleTypes, maxArticles, 0, orderBy);
	}

	@Override
	public List<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, String orderBy) {
		return getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleTypes, maxArticles, 0, orderBy);
	}
	
	@Override
	public List<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, int offset, String orderBy) {
		List<ArticleType> articleTypes = Arrays.asList(articleType);
		return getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleTypes, maxArticles, offset, orderBy);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy) {
		List<Long> ids = getBySectionUniqueNameArticleTypePagination(sectionUniqueName, publicationName, articleTypes, maxArticles, offset, orderBy);
		if(ids.size()>0) {
			Session session = getSession();
			//Eagerly FETCH authors And relatedArticles Filtered By enclosureComment = MAIN
	    	session.enableFilter("relatedByEnclosureComment").setParameter("enclosureComment", EnclosureComment.MAIN.ordinal());
	    	Query query = session.createQuery(	"SELECT DISTINCT(articles) " +
	    										"FROM Article articles " +
								    			"LEFT OUTER JOIN articles.categories categories " +
								    			"LEFT OUTER JOIN categories.publication publication " +
								    			"LEFT OUTER JOIN FETCH articles.relatedArticles related " +
								    			"LEFT OUTER JOIN FETCH articles.authors authors " +
								    			"WHERE categories.sectionUniqueName=:sectionUniqueName " +
								    			"AND publication.name=:publicationName " +
								    			"AND articles.articleState<>:articleState " +
								    			"AND articles.articleType IN (:articleType) " +
								    			"AND articles.id IN (:ids) " +
								    			"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
	    	query.setParameter("sectionUniqueName", sectionUniqueName);
	    	query.setParameter("publicationName", publicationName);
	    	query.setParameter("articleState", ArticleState.ARCHIVED);
	    	query.setParameterList("articleType", articleTypes);
	    	query.setParameterList("ids", ids);
	    	query.setCacheable(false); //Can Not Cache Due To JOIN FETCH
	    	return (List<Article>) query.list();
		}
		else {
			return null;
		}
	}
	
	@Override
	public Long countBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType) {
		List<ArticleType> articleTypes = Arrays.asList(articleType);
		return countBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleTypes);
	}
	
	@Override
	public Long countBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes) {
		//No Need To FETCH Associations Or Enable A Filter For Count Queries
		Query query = getSession().createQuery(	"SELECT COUNT(DISTINCT articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"AND articles.articleType IN (:articleType)");
    	query.setParameter("sectionUniqueName", sectionUniqueName);
    	query.setParameter("publicationName", publicationName);
    	query.setParameter("articleState", ArticleState.ARCHIVED);
    	query.setParameterList("articleType", articleTypes);
    	query.setCacheable(true);
    	return (Long) query.uniqueResult();
	}
	
	/*
	 * The Effect Of Applying setMaxResults() or setFirstResult() To A Query Involving Fetch Joins Over Collections Is Undefined. It Varies 
	 * From Database To Database, But Hibernate Usually Does The Paging In Memory Instead Of At The Database Query Level. So, Since This Is 
	 * Something We Don't Want, We Use A Separate Query To Get The Ids Of The Desired Objects, And We Pass These Ids To The Fetch Join Query
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getBySectionUniqueNameArticleTypePagination(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy) {
    	Query query = getSession().createQuery(	"SELECT articles.id " +
												"FROM Article articles " +
								    			"LEFT OUTER JOIN articles.categories categories " +
								    			"LEFT OUTER JOIN categories.publication publication " +
								    			"WHERE categories.sectionUniqueName=:sectionUniqueName " +
								    			"AND publication.name=:publicationName " +
								    			"AND articles.articleState<>:articleState " +
								    			"AND articles.articleType IN (:articleType) " +
    											"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setParameterList("articleType", articleTypes);
		query.setFirstResult(offset);
		query.setMaxResults(maxArticles);
		query.setCacheable(true);
		return (List<Long>) query.list();
	}

	@Override
	public List<Article> getBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType, int maxArticles, String orderBy) {
		List<ArticleType> articleTypes = Arrays.asList(articleType);
		return getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleTypes, maxArticles, 0, orderBy);
	}

	@Override
	public List<Article> getBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes, int maxArticles, String orderBy) {
		return getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleTypes, maxArticles, 0, orderBy);
	}
	
	@Override
	public List<Article> getBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType, int maxArticles, int offset, String orderBy) {
		List<ArticleType> articleTypes = Arrays.asList(articleType);
		return getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleTypes, maxArticles, offset, orderBy);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy) {
		List<Long> ids = getBySectionUniqueNameGroupNameArticleTypePagination(sectionUniqueName, groupName, publicationName, articleTypes, maxArticles, offset, orderBy);
		if(ids.size()>0) {
			Session session = getSession();
	    	session.enableFilter("relatedByEnclosureComment").setParameter("enclosureComment", EnclosureComment.MAIN.ordinal()); //Eagerly FETCH authors And relatedArticles Filtered By enclosureComment = MAIN
			Query query = session.createQuery(	"SELECT DISTINCT(articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"LEFT OUTER JOIN FETCH articles.relatedArticles related " +
												"LEFT OUTER JOIN FETCH articles.authors authors " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND categories.groupName=:groupName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"AND articles.articleType IN (:articleType) " +
												"AND articles.id IN (:ids) " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
			query.setParameter("sectionUniqueName", sectionUniqueName);
			query.setParameter("groupName", groupName);
			query.setParameter("publicationName", publicationName);
			query.setParameter("articleState", ArticleState.ARCHIVED);
			query.setParameterList("articleType", articleTypes);
			query.setParameterList("ids", ids);
			query.setCacheable(false); //Can Not Cache Due To JOIN FETCH
			return (List<Article>) query.list();
		}
		else {
			return null;
		}
	}

	@Override
	public Long countBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType) {
		List<ArticleType> articleTypes = Arrays.asList(articleType);
		return countBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleTypes);
	}
	
	@Override
	public Long countBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes) {
		//No Need To FETCH Associations Or Enable A Filter For Count Queries
		Query query = getSession().createQuery(	"SELECT COUNT(DISTINCT articles) " +
												"FROM Article articles " +
    											"LEFT OUTER JOIN articles.categories categories " +
    											"LEFT OUTER JOIN categories.publication publication " + 
    											"WHERE categories.sectionUniqueName=:sectionUniqueName " +
    											"AND categories.groupName=:groupName " +
    											"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"AND articles.articleType IN (:articleType)");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("groupName", groupName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setParameterList("articleType", articleTypes);
		query.setCacheable(true);
		return (Long) query.uniqueResult();
	}

	/*
	 * The Effect Of Applying setMaxResults() or setFirstResult() To A Query Involving Fetch Joins Over Collections Is Undefined. It Varies 
	 * From Database To Database, But Hibernate Usually Does The Paging In Memory Instead Of At The Database Query Level. So, Since This Is 
	 * Something We Don't Want, We Use A Separate Query To Get The Ids Of The Desired Objects, And We Pass These Ids To The Fetch Join Query
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getBySectionUniqueNameGroupNameArticleTypePagination(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy) {
		Query query = getSession().createQuery(	"SELECT articles.id " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND categories.groupName=:groupName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"AND articles.articleType IN (:articleType) " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("groupName", groupName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setParameterList("articleType", articleTypes);
		query.setFirstResult(offset);
		query.setMaxResults(maxArticles);
		query.setCacheable(true);
		return (List<Long>) query.list();
	}

	@Override
	public List<Article> getByTagName(String tagName, String publicationName, int maxArticles, String orderBy) {
		return getByTagName(tagName, publicationName, maxArticles, 0, orderBy);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getByTagName(String tagName, String publicationName, int maxArticles, int offset, String orderBy) {
		List<Long> ids = getByTagNamePagination(tagName, publicationName, maxArticles, offset, orderBy);
		if(ids.size()>0) {
			Session session = getSession();
	    	session.enableFilter("relatedByEnclosureComment").setParameter("enclosureComment", EnclosureComment.MAIN.ordinal()); //Eagerly FETCH authors And relatedArticles Filtered By enclosureComment = MAIN
			Query query = session.createQuery(	"SELECT DISTINCT(articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.tags tags " +
												"LEFT OUTER JOIN tags.publication publication " +
												"LEFT OUTER JOIN FETCH articles.relatedArticles related " +
												"LEFT OUTER JOIN FETCH articles.authors authors " +
												"WHERE tags.name=:tagName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"AND articles.id IN (:ids) " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
			query.setParameter("tagName", tagName);
			query.setParameter("publicationName", publicationName);
			query.setParameter("articleState", ArticleState.ARCHIVED);
			query.setParameterList("ids", ids);
			query.setCacheable(false); //Can Not Cache Due To JOIN FETCH
			return (List<Article>) query.list();
		}
		else {
			return null;
		}
	}
	
	@Override
	public Long countByTagName(String tagName, String publicationName) {
		//No Need To FETCH Associations Or Enable A Filter For Count Queries
		Query query = getSession().createQuery(	"SELECT COUNT(DISTINCT articles) " +
												"FROM Article articles " +
    											"LEFT OUTER JOIN articles.tags tags " +
    											"LEFT OUTER JOIN tags.publication publication " + 
    											"WHERE tags.name=:tagName " +
    											"AND publication.name=:publicationName " +
    											"AND articles.articleState<>:articleState");
		query.setParameter("tagName", tagName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setCacheable(true);
		return (Long) query.uniqueResult();
	}
	
	/*
	 * The Effect Of Applying setMaxResults() or setFirstResult() To A Query Involving Fetch Joins Over Collections Is Undefined. It Varies 
	 * From Database To Database, But Hibernate Usually Does The Paging In Memory Instead Of At The Database Query Level. So, Since This Is 
	 * Something We Don't Want, We Use A Separate Query To Get The Ids Of The Desired Objects, And We Pass These Ids To The Fetch Join Query
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getByTagNamePagination(String tagName, String publicationName, int maxArticles, int offset, String orderBy) {
		Query query = getSession().createQuery(	"SELECT articles.id " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.tags tags " +
												"LEFT OUTER JOIN tags.publication publication " +
												"WHERE tags.name=:tagName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
		query.setParameter("tagName", tagName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setFirstResult(offset);
		query.setMaxResults(maxArticles);
		query.setCacheable(true);
		return (List<Long>) query.list();
	}

	@Override
	public List<Article> getBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName, int maxArticles, String orderBy) {
		return getBySectionUniqueNameTagName(sectionUniqueName, tagName, publicationName, maxArticles, 0, orderBy);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName, int maxArticles, int offset, String orderBy) {
		List<Long> ids = getBySectionUniqueNameTagNamePagination(sectionUniqueName, tagName, publicationName, maxArticles, offset, orderBy);
		if(ids.size()>0) {
			Session session = getSession();
	    	session.enableFilter("relatedByEnclosureComment").setParameter("enclosureComment", EnclosureComment.MAIN.ordinal()); //Eagerly FETCH authors And relatedArticles Filtered By enclosureComment = MAIN
			Query query = session.createQuery(	"SELECT DISTINCT(articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN articles.tags tags " +
												"LEFT OUTER JOIN tags.publication publication " +
												"LEFT OUTER JOIN FETCH articles.relatedArticles related " +
												"LEFT OUTER JOIN FETCH articles.authors authors " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND tags.name=:tagName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"AND articles.id IN (:ids) " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
			query.setParameter("sectionUniqueName", sectionUniqueName);
			query.setParameter("tagName", tagName);
			query.setParameter("publicationName", publicationName);
			query.setParameter("articleState", ArticleState.ARCHIVED);
			query.setParameterList("ids", ids);
			query.setCacheable(false); //Can Not Cache Due To JOIN FETCH
			return (List<Article>) query.list();
		}
		else {
			return null;
		}
	}

	@Override
	public Long countBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName) {
		//No Need To FETCH Associations Or Enable A Filter For Count Queries
		Query query = getSession().createQuery(	"SELECT COUNT(DISTINCT articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
    											"LEFT OUTER JOIN articles.tags tags " +
    											"LEFT OUTER JOIN tags.publication publication " + 
    											"WHERE categories.sectionUniqueName=:sectionUniqueName " +
    											"AND tags.name=:tagName " +
    											"AND publication.name=:publicationName " +
    											"AND articles.articleState<>:articleState");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("tagName", tagName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setCacheable(true);
		return (Long) query.uniqueResult();
	}
	
	/*
	 * The Effect Of Applying setMaxResults() or setFirstResult() To A Query Involving Fetch Joins Over Collections Is Undefined. It Varies 
	 * From Database To Database, But Hibernate Usually Does The Paging In Memory Instead Of At The Database Query Level. So, Since This Is 
	 * Something We Don't Want, We Use A Separate Query To Get The Ids Of The Desired Objects, And We Pass These Ids To The Fetch Join Query
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getBySectionUniqueNameTagNamePagination(String sectionUniqueName, String tagName, String publicationName, int maxArticles, int offset, String orderBy) {
		Query query = getSession().createQuery(	"SELECT articles.id " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN articles.tags tags " +
												"LEFT OUTER JOIN tags.publication publication " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND tags.name=:tagName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleState<>:articleState " +
												"ORDER BY " + (orderBy!=null ? "articles."+orderBy : "articles.datePublished") + " DESC");
		query.setParameter("sectionUniqueName", sectionUniqueName);
		query.setParameter("tagName", tagName);
		query.setParameter("publicationName", publicationName);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setFirstResult(offset);
		query.setMaxResults(maxArticles);
		query.setCacheable(true);
		return (List<Long>) query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public LinkedHashSet<Article> getBySection(String sectionName, String publicationName, boolean lazy) {
		Session session = getSession();
		if(lazy) { //No Need To FETCH Any Related Article Associations
			Query query = session.createQuery(	"SELECT articles " +
												"FROM Section section " +
												"LEFT OUTER JOIN section.publication publication " +
												"LEFT OUTER JOIN section.articles articles " +
												"WHERE section.name=:sectionName " +
												"AND publication.name=:publicationName");
			query.setParameter("sectionName", sectionName);
			query.setParameter("publicationName", publicationName);
			query.setCacheable(true);
			return new LinkedHashSet<Article>(query.list()); //A Trick To Get Distinct Articles Without Using DISTINCT Keyword In The HQL
		}
		else { //Eagerly FETCH articles With Their Categories, Authors, Related Articles ( Filtered By enclosureComment = MAIN | TEASER )
	    	List<Integer> comments = Arrays.asList(EnclosureComment.MAIN.ordinal(), EnclosureComment.TEASER.ordinal());
			session.enableFilter("relatedByEnclosureComments").setParameterList("enclosureComments", comments);
			Query query = session.createQuery(	"SELECT articles " +
												"FROM Section section " +
												"LEFT OUTER JOIN section.publication publication " +
												"LEFT OUTER JOIN section.articles articles " +
												"LEFT OUTER JOIN FETCH articles.categories categories " +
												"LEFT OUTER JOIN FETCH articles.authors authors " +
												"LEFT OUTER JOIN FETCH articles.relatedArticles related " +
												"WHERE section.name=:sectionName " +
												"AND publication.name=:publicationName");
			query.setParameter("sectionName", sectionName);
			query.setParameter("publicationName", publicationName);
			query.setCacheable(false); //Can Not Cache Due To JOIN FETCH
			return new LinkedHashSet<Article>(query.list()); //A Trick To Get Distinct Articles Without Using DISTINCT Keyword In The HQL
		}
	}

	@Override
	public Long countBySection(String sectionName, String publicationName) {
		//No Need To Enable A Filter For Count Queries
		Query query = getSession().createQuery(	"SELECT COUNT(DISTINCT articles) " +
												"FROM Section section " +
												"LEFT OUTER JOIN section.publication publication " +
												"LEFT OUTER JOIN section.articles articles " +
												"WHERE section.name=:sectionName " +
												"AND publication.name=:publicationName");
		query.setParameter("sectionName", sectionName);
		query.setParameter("publicationName", publicationName);
		query.setCacheable(true);
		return (Long) query.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Article> getDailyBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, Calendar fromDate, Calendar toDate, int maxArticles) {
		//No Need To FETCH Associations Or Enable A Filter Here (The Method Is Currently Used Only For Newspapers That Do Not Have Relations)
		Query query = getSession().createQuery(	"SELECT DISTINCT(articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleType=:articleType " +
												"AND articles.datePublished >=:fromDate " +
												"AND articles.datePublished <=:toDate " + 
												"ORDER BY articles.datePublished DESC");
    	query.setParameter("sectionUniqueName", sectionUniqueName);
    	query.setParameter("publicationName", publicationName);
    	query.setParameter("articleType", articleType);
    	query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setMaxResults(maxArticles);
        query.setCacheable(true);
        return (List<Article>) query.list();
	}

	@Override
    public Article articleExists(String eceArticleId, Class<? extends Article> articleClass) throws InstantiationException, IllegalAccessException {
    	Article article = getByEceArticleId(eceArticleId);
    	if(article == null) {
    		article = articleClass.newInstance();
    	}
    	return article;
    }
    
	@Override
	public boolean newOrUpdated(String eceArticleId, Calendar dateLastUpdated) {
		Query query = getSession().createQuery("SELECT a.dateLastUpdated FROM Article a WHERE a.eceArticleId =:eceArticleId");
		query.setParameter("eceArticleId", eceArticleId);
		query.setCacheable(false); //It's Critical For The Updater To Not Cache This Query
		Calendar date = (Calendar) query.uniqueResult();
		if(date==null || !date.equals(dateLastUpdated)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> searchByAttributesLike(String term) {
		Query query = getSession().createQuery(	"SELECT CONCAT(a.eceArticleId, ': ', a.title) FROM Article a " +
												"WHERE a.eceArticleId LIKE :eceArticleId " +
												"OR a.title LIKE :title " +
												"ORDER BY a.title ASC");
		query.setParameter("eceArticleId", "%" + term + "%");
		query.setParameter("title", "%" + term + "%");
		query.setMaxResults(5);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (List<String>) query.list();
	}
	
	/*
	 * Unfortunately, Due To Escenic's Mass Publishing Functionality, It's Common For Articles To Have The Same Published Date
	 * (Especially For Categories Like /shopping_list). In Order To Avoid An Endless Loop Between 2 Articles You Must Not Use 
	 * '>=' Or '<=' Comparators For The Date Published. Use '>' And '<' And Be Aware Of The Fact That If Some Articles Have Exactly
	 * The Same Published Date You Will Not See Them While Navigating To Next | Previous (Or Alternatively Use The byEceArticleId Method)
	 */
	@Override
	public String previousOrNextByDatePublished(Article article, boolean next) {
    	Query query = getSession().createQuery(	"SELECT articles.alternate " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleType=:articleType " +
												"AND articles.articleState<>:articleState " +
												"AND articles.datePublished" + (next ? ">" : "<") + ":datePublished " +
												"ORDER BY articles.datePublished " + (next ? "ASC" : "DESC"));
		query.setParameter("sectionUniqueName", article.getCategories().get(0).getSectionUniqueName());
		query.setParameter("publicationName", article.getCategories().get(0).getPublication().getName());
		query.setParameter("articleType", ArticleType.STORY);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setParameter("datePublished", article.getDatePublished());
		query.setMaxResults(1);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (String) query.uniqueResult();
	}

	@Override
	public String previousOrNextByEceArticleId(Article article, boolean next) {
    	Query query = getSession().createQuery(	"SELECT articles.alternate " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"LEFT OUTER JOIN categories.publication publication " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND publication.name=:publicationName " +
												"AND articles.articleType=:articleType " +
												"AND articles.articleState<>:articleState " +
												"AND articles.eceArticleId" + (next ? ">" : "<") + ":eceArticleId " +
												"ORDER BY articles.eceArticleId " + (next ? "ASC" : "DESC"));
		query.setParameter("sectionUniqueName", article.getCategories().get(0).getSectionUniqueName());
		query.setParameter("publicationName", article.getCategories().get(0).getPublication().getName());
		query.setParameter("articleType", ArticleType.STORY);
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setParameter("eceArticleId", article.getEceArticleId());
		query.setMaxResults(1);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (String) query.uniqueResult();
	}
}