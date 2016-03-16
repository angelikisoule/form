package gr.media24.mSites.data.dao;

import java.util.List;

import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Newspaper;

/**
 * Newspaper's DAO Interface
 * @author npapadopoulos
 */
public interface NewspaperDao extends AbstractDao<Newspaper> {

	/**
	 * Get A List Of A Newspaper's Most Recent Article Objects 
	 * [Used For Navigation In Single Newspaper View]
	 * @param category Newspaper's Category
	 * @return List Of Newspaper's Most Recent Articles
	 */
	List<Article> recentNewspapers(Category category);
}