package gr.media24.mSites.data.dao;

import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.enums.EnclosureComment;

/**
 * ArticleRelatedArticle's DAO Interface
 * @author npapadopoulos
 */
public interface ArticleRelatedArticleDao extends AbstractDao<ArticleRelatedArticle> {

	/**
	 * Get ArticleRelatedArticle Given The Article, The Related Article And The Relationships Enclosure Comment
	 * @param article Article Object
	 * @param related Related Article Object
	 * @param enclosureComment Relationship's Enclosure Comment
	 * @return ArticleRelatedArticle Object
	 */
	ArticleRelatedArticle getByArticleAndRelatedAndEnclosureComment(Article article, Article related, EnclosureComment enclosureComment);
	
	/**
	 * Check If An ArticleRelatedArticle Exists And If Not Create It By The Given Article, Related Article And Enclosure Comment
	 * @param article Article Object
	 * @param related Related Article Object
	 * @param enclosureComment Relationship's Enclosure Comment
	 * @return The Existing ArticleRelatedArticle Or A New One Created By The Given Parameters
	 */
	ArticleRelatedArticle articleRelatedArticleExists(Article article, Article related, EnclosureComment enclosureComment);
}
