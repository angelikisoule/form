package gr.media24.mSites.data.dao.hibernate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import gr.media24.mSites.data.dao.ArticleRelatedArticleDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.enums.EnclosureComment;

/**
 * ArticleRelatedArticle's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateArticleRelatedArticleDao extends HibernateAbstractDao<ArticleRelatedArticle> implements ArticleRelatedArticleDao {

	@Override
	public ArticleRelatedArticle getByArticleAndRelatedAndEnclosureComment(Article article, Article related, EnclosureComment enclosureComment) {
		Query query = getSession().getNamedQuery("getArticleRelatedArticleByArticleAndRelatedAndEnclosureComment");
		query.setParameter("article", article);
		query.setParameter("related", related);
		query.setParameter("enclosureComment", enclosureComment);
		query.setCacheable(false);
		return (ArticleRelatedArticle) query.uniqueResult();
	}

	@Override
	public ArticleRelatedArticle articleRelatedArticleExists(Article article, Article related, EnclosureComment enclosureComment) {
		if(article.getId()==null) { //Article Is Not Persisted Yet
			return new ArticleRelatedArticle(article, related, enclosureComment);
		}
		else {
			ArticleRelatedArticle articleRelatedArticle = getByArticleAndRelatedAndEnclosureComment(article, related, enclosureComment);
			if(articleRelatedArticle==null) {
				return new ArticleRelatedArticle(article, related, enclosureComment);
			}
			else {
				return articleRelatedArticle;
			}
		}	
	}
}
