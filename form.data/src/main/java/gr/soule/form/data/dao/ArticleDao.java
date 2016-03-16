package gr.media24.mSites.data.dao;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;

import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;

/**
 * Article's DAO Interface
 * @author npapadopoulos, asoule
 */
public interface ArticleDao extends AbstractDao<Article> {

	/**
	 * Get Article By The Given eceArticleId
	 * @param eceArticleId Article's eceArticleId
	 * @return Article Object If eceArticleId Exists, Otherwise null
	 */
	Article getByEceArticleId(String eceArticleId);
	
	/**
	 * Get All Articles  [ Just Calling The 2 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param maxArticles Maximum Number Of Returned Results
	 * @return List Of Articles
	 */
	List<Article> getAll(int maxArticles);
	
	/**
	 * Get All Articles
	 * @param maxArticles Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Articles
	 */
	List<Article> getAll(int maxArticles, int offset);
	
	/**
	 * Count All Articles
	 * @return Number Of Articles
	 */
	Long countAll();
	
	/**
	 * Get Articles By sectionUniqueName [ Just Calling The 4 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param maxArticles Maximum Number Of Returned Results
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueName(String sectionUniqueName, String publicationName, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param maxArticles Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueName(String sectionUniqueName, String publicationName, int maxArticles, int offset, String orderBy);
	
	/**
	 * Count Articles By sectionUniqueName
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @return Number Of Articles
	 */
	Long countBySectionUniqueName(String sectionUniqueName, String publicationName);
	
	/**
	 * Get Articles By articleType [ Just Calling The 3 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getByArticleType(ArticleType articleType, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By articleTypes [ Just Calling The 3 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param articleTypes List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getByArticleType(List<ArticleType> articleTypes, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By articleType
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getByArticleType(ArticleType articleType, int maxArticles, int offset, String orderBy);

	/**
	 * Get Articles By articleTypes
	 * @param articleTypes List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getByArticleType(List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy);
	
	/**
	 * Count Articles By articleType
	 * @param articleType Article's article Type
	 * @return Number Of Articles
	 */
	Long countByArticleType(ArticleType articleType);

	/**
	 * Count Articles By articleTypes
	 * @param articleTypes List Of Article's articleType
	 * @return Number Of Articles
	 */
	Long countByArticleType(List<ArticleType> articleTypes);
	
	/**
	 * Get Articles By articleState [ Just Calling The 3 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param articleState Article's articleState
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @return List Of Articles
	 */
	List<Article> getByArticleState(ArticleState articleState, int maxArticles);
	
	/**
	 * Get Articles By articleState
	 * @param articleState Article's articleState
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Articles
	 */
	List<Article> getByArticleState(ArticleState articleState, int maxArticles, int offset);
	
	/**
	 * Count Articles By articleState
	 * @param articleState Article's articleState
	 * @return Number Of Articles
	 */
	Long countByArticleState(ArticleState articleState);
	
	/**
	 * Get Articles By sectionUniqueName And groupName [ Just Calling The 5 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName And groupName
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName, int maxArticles, int offset, String orderBy);
	
	/**
	 * Count Articles By sectionUniqueName And groupName
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @return Number Of Articles
	 */
	Long countBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName);
	
	/**
	 * Get Articles By sectionUniqueName And articleType [ Just Calling The 5 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName And articleType [ Just Calling The 5 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleTypes List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, int offset, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleTypes List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param lazy true If We Only Need Basic Article Fields (And None Of The Associations)
	 * @return LinkedHashSet Of Articles
	 */
	LinkedHashSet<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, boolean lazy);

	/**
	 * Get Articles By sectionUniqueName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleTypes List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param lazy true If We Only Need Basic Article Fields (And None Of The Associations)
	 * @return LinkedHashSet Of Articles
	 */
	LinkedHashSet<Article> getBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, boolean lazy);
	
	/**
	 * Count Articles By sectionUniqueName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @return Number Of Articles
	 */
	Long countBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType);
	
	/**
	 * Count Articles By sectionUniqueName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleTypes List Of Article's articleTypes
	 * @return Number Of Articles
	 */
	Long countBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes);
	
	/**
	 * Get Articles By sectionUniqueName, groupName And articleType [ Just Calling The 6 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType, int maxArticles, String orderBy);

	/**
	 * Get Articles By sectionUniqueName, groupName And articleType [ Just Calling The 6 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param articleTypes List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName, groupName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType, int maxArticles, int offset, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName, groupName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param articleTypes List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy);
		
	/**
	 * Count Articles By sectionUniqueName, groupName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @return Number Of Articles
	 */
	Long countBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType);
	
	/**
	 * Count Articles By sectionUniqueName, groupName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param articleTypes List Of Article's articleTypes
	 * @return Number Of Articles
	 */
	Long countBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes);

	/**
	 * Get Articles By tagName [ Just Calling The 4 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param tagName Article Tag's tagName
	 * @param publicationName Article Tag's publicationName
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getByTagName(String tagName, String publicationName, int maxArticles, String orderBy);	
	
	/**
	 * Get Articles By tagName
	 * @param tagName Article Tag's tagName
	 * @param publicationName Article Tag's publicationName
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getByTagName(String tagName, String publicationName, int maxArticles, int offset, String orderBy);
	
	/**
	 * Count Articles By tagName
	 * @param tagName Article Tag's Name
	 * @param publicationName Article Tag's publicationName
	 * @return Number Of Articles
	 */
	Long countByTagName(String tagName, String publicationName);	
	
	/**
	 * Get Articles By sectionUniqueName And tagName [ Just Calling The 5 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName
	 * @param tagName Article Tag's Name
	 * @param publicationName Article Tag's publicationName
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName, int maxArticles, String orderBy);	
	
	/**
	 * Get Articles By sectionUniqueName And tagName
	 * @param sectionUniqueName
	 * @param tagName Article Tag's Name
	 * @param publicationName Article Tag's publicationName
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName, int maxArticles, int offset, String orderBy);
	
	/**
	 * Count Articles By sectionUniqueName And tagName
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param tagName Tag's Name
	 * @param publicationName Tag's publicationName
	 * @return Number Of Articles
	 */
	Long countBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName);

	/**
	 * Given The Section's Name And Publication Name Get It's Articles
	 * @param name Section's Name
	 * @param publicationName Section's Publication Name
	 * @param lazy true If We Only Need Basic Article Fields (And None Of The Associations)
	 * @return LinkedHashSet Of Articles
	 */
	LinkedHashSet<Article> getBySection(String sectionName, String publicationName, boolean lazy);
	
	/**
	 * Count Section's Articles Given It's Name And Publication
	 * @param sectionName Section's Name
	 * @param publicationName Section's Publication Name
	 * @return Number Of Articles
	 */
	Long countBySection(String sectionName, String publicationName);

	/**
	 * Return A List Of Articles Characterized As 'daily' Depending On Current HOUR_OF_DAY ( Currently Only For NEWSPAPER Articles )
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @return List Of Articles
	 */
	List<Article> getDailyBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, Calendar fromDate, Calendar toDate, int maxArticles);

    /**
     * Search For An Article By eceArticleId and If It Exists Return It, Otherwise Return A New Instance
     * @param eceArticleId Article's eceArticleId
     * @param articleClass Article's Class
     * @return Existing Article Or A New Article Instance
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
	Article articleExists(String eceArticleId, Class<? extends Article> articleClass) throws InstantiationException, IllegalAccessException;
	
	/**
	 * Check If An Article Has Changed Since Last Updater's Scheduled-Task Execution
	 * @param eceArticleId Article's eceArticleId
	 * @param dateLastUpdated Article's dateLastUpdated According To The Related Atom Entry Tag
	 * @return TRUE If The Article Is Not Persisted Or If It Is But With A Different dateLastUpdated Than The Atom Entry's Date
	 */
	boolean newOrUpdated(String eceArticleId, Calendar dateLastUpdated);
	
	/**
	 * Search Articles With eceArticleId Or title Like A Given Term
	 * @param term Search Term
	 * @return List Of String Results For Autocomplete Form
	 */
	List<String> searchByAttributesLike(String term);
	
	/**
	 * Get Previous Or Next Article URL Given A 'current' Article (By datePublished)
	 * @param article Current Article Object
	 * @param next true To Get The Next Article, false To Get The Previous One
	 * @return Next | Previous Article URL
	 */
	String previousOrNextByDatePublished(Article article, boolean next);
	
	/**
	 * Get Previous Or Next Article URL Given A 'current' Article (By eceArticleId)
	 * @param article Current Article Object
	 * @param next true To Get The Next Article, false To Get The Previous One
	 * @return Next | Previous Article URL
	 */
	String previousOrNextByEceArticleId(Article article, boolean next);
}