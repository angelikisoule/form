package gr.media24.mSites.data.dao.hibernate;

import java.util.List;

import gr.media24.mSites.data.dao.NewspaperDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Newspaper;
import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Newspapers's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateNewspaperDao extends HibernateAbstractDao<Newspaper> implements NewspaperDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Article> recentNewspapers(Category category) {
		Query query = getSession().createQuery( "SELECT DISTINCT(articles) " +
												"FROM Article articles " +
												"LEFT OUTER JOIN articles.categories categories " +
												"WHERE categories.sectionUniqueName=:sectionUniqueName " +
												"AND articles.articleState<>:articleState " +
												"AND articles.articleType=:articleType " +
												"ORDER BY articles.datePublished DESC");
		query.setParameter("sectionUniqueName", category.getSectionUniqueName());
		query.setParameter("articleState", ArticleState.ARCHIVED);
		query.setParameter("articleType", ArticleType.NEWSPAPER);
		query.setMaxResults(30); //At Maximum A Month Back
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (List<Article>) query.list();
	}
}
