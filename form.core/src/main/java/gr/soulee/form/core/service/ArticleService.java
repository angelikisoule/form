package gr.media24.mSites.core.service;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Picture;
import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;

import org.springframework.web.servlet.ModelAndView;

/**
 * Articles's Service Interface
 * @author npapadopoulos, asoule
 */
public interface ArticleService {

	/**
	 * Get Article By The Given eceArticleId
	 * @param eceArticleId Article's eceArticleId
	 * @return Article Object If eceArticleId Exists, Otherwise null
	 */
	Article getArticleByEceArticleId(String eceArticleId);
	
	/**
	 * Get Article's ModelAndView Based On The Requested URI
	 * @param uri Requested URI
	 * @param request HttpServlet Request To Get User-Agent String et al.
	 * @return ModelAndView
	 */
	ModelAndView getArticleModelAndView(String uri, HttpServletRequest request);
	
	/**
	 * Get All Articles  [ Just Calling The 2 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param maxArticles Maximum Number Of Returned Results
	 * @return List Of Articles
	 */
	List<Article> getArticles(int maxArticles);
	
	/**
	 * Get All Articles
	 * @param maxArticles Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Articles
	 */
	List<Article> getArticles(int maxArticles, int offset);
	
	/**
	 * Count All Articles
	 * @return Number Of Articles
	 */
	Long countArticles();
	
	/**
	 * Get Articles By sectionUniqueName [ Just Calling The 4 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param maxArticles Maximum Number Of Returned Results
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesBySectionUniqueName(String sectionUniqueName, String publicationName, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param maxArticles Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesBySectionUniqueName(String sectionUniqueName, String publicationName, int maxArticles, int offset, String orderBy);
	
	/**
	 * Count Articles By sectionUniqueName
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @return Number Of Articles
	 */
	Long countArticlesBySectionUniqueName(String sectionUniqueName, String publicationName);
	
	/**
	 * Get Articles By articleType [ Just Calling The 3 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesByArticleType(ArticleType articleType, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By articleTypes [ Just Calling The 3 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param articleTypes List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesByArticleType(List<ArticleType> articleTypes, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By articleType
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesByArticleType(ArticleType articleType, int maxArticles, int offset, String orderBy);
	
	/**
	 * Get Articles By articleTypes
	 * @param articleTypes List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesByArticleType(List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy);
	
	/**
	 * Count Articles By articleType
	 * @param articleType Article's article Type
	 * @return Number Of Articles
	 */
	Long countArticlesByArticleType(ArticleType articleType);
	
	/**
	 * Count Articles By articleTypes
	 * @param articleTypes List Of Article's article Type
	 * @return Number Of Articles
	 */
	Long countArticlesByArticleType(List<ArticleType> articleTypes);
	
	/**
	 * Get Articles By articleState [ Just Calling The 3 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param articleState Article's articleState
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @return List Of Articles
	 */
	List<Article> getArticlesByArticleState(ArticleState articleState, int maxArticles);
	
	/**
	 * Get Articles By articleState
	 * @param articleState Article's articleState
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Articles
	 */
	List<Article> getArticlesByArticleState(ArticleState articleState, int maxArticles, int offset);
	
	/**
	 * Count Articles By articleState
	 * @param articleState Article's articleState
	 * @return Number Of Articles
	 */
	Long countArticlesByArticleState(ArticleState articleState);
	
	/**
	 * Get Articles By sectionUniqueName And groupName [ Just Calling The 5 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName, int maxArticles, String orderBy);
	
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
	List<Article> getArticlesBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName, int maxArticles, int offset, String orderBy);
	
	/**
	 * Count Articles By sectionUniqueName And groupName
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @return Number Of Articles
	 */
	Long countArticlesBySectionUniqueNameGroupName(String sectionUniqueName, String groupName, String publicationName);
	
	/**
	 * Get Articles By sectionUniqueName And articleType [ Just Calling The 5 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName And articleType [ Just Calling The 5 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleTypes List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, String orderBy);
	
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
	List<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, int offset, String orderBy);
	
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
	List<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy);
	
	/**
	 * Get Articles By sectionUniqueName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param lazy true If We Only Need Basic Article Fields (And None Of The Associations)
	 * @return LinkedHashSet Of Articles
	 */
	LinkedHashSet<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles, boolean lazy);
	
	/**
	 * Get Articles By sectionUniqueName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType List Of Article's articleTypes
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param lazy true If We Only Need Basic Article Fields (And None Of The Associations)
	 * @return LinkedHashSet Of Articles
	 */
	LinkedHashSet<Article> getArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes, int maxArticles, boolean lazy);
	
	/**
	 * Count Articles By sectionUniqueName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @return Number Of Articles
	 */
	Long countArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType);
	
	/**
	 * Count Articles By sectionUniqueName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleTypes List Of Article's articleTypes
	 * @return Number Of Articles
	 */
	Long countArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, List<ArticleType> articleTypes);
	
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
	List<Article> getArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType, int maxArticles, String orderBy);

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
	List<Article> getArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes, int maxArticles, String orderBy);
	
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
	List<Article> getArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType, int maxArticles, int offset, String orderBy);
	
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
	List<Article> getArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes, int maxArticles, int offset, String orderBy);	
	
	/**
	 * Count Articles By sectionUniqueName, groupName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @return Number Of Articles
	 */
	Long countArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, ArticleType articleType);
	
	/**
	 * Count Articles By sectionUniqueName, groupName And articleType
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param groupName Article's Category groupName
	 * @param publicationName Article's Category publicationName
	 * @param articleTypes List Of Article's articleTypes
	 * @return Number Of Articles
	 */
	Long countArticlesBySectionUniqueNameGroupNameArticleType(String sectionUniqueName, String groupName, String publicationName, List<ArticleType> articleTypes);
	
	/**
	 * Get Articles By tagName [ Just Calling The 4 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param tagName Article Tag's tagName
	 * @param publicationName Article Tag's publicationName
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesByTagName(String tagName, String publicationName, int maxArticles, String orderBy);
	
	/**
	 * Get Articles By tagName [ Just Calling The 4 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param tagName Article Tag's tagName
	 * @param publicationName Article Tag's publicationName
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param offset Beginning From The Specified Offset
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesByTagName(String tagName, String publicationName, int maxArticles, int offset, String orderBy);
	
	/**
	 * Count Articles By tagName
	 * @param tagName Article Tag's Name
	 * @param publicationName Article Tag's publicationName
	 * @return Number Of Articles
	 */
	Long countArticlesByTagName(String tagName, String publicationName);
	
	/**
	 * Get Articles By sectionUniqueName And tagName [ Just Calling The 5 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param sectionUniqueName
	 * @param tagName Article Tag's Name
	 * @param publicationName Article Tag's publicationName
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @param orderBy Order Results By The Field Name Given Or datePublished (The Default)
	 * @return List Of Articles
	 */
	List<Article> getArticlesBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName, int maxArticles, String orderBy);
	
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
	List<Article> getArticlesBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName, int maxArticles, int offset, String orderBy);
	
	/**
	 * Count Articles By sectionUniqueName And tagName
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param tagName Tag's Name
	 * @param publicationName Tag's publicationName
	 * @return Number Of Articles
	 */
	Long countArticlesBySectionUniqueNameTagName(String sectionUniqueName, String tagName, String publicationName);
	
	/**
	 * Given The Section's Name And Publication Name Get it's Articles
	 * @param name Section's Name
	 * @param publicationName Section's Publication Name
	 * @param lazy true If We Only Need Basic Article Fields (And None Of The Associations)
	 * @return LinkedHashSet Of Articles
	 */
	LinkedHashSet<Article> getArticlesBySection(String sectionName, String publicationName, boolean lazy);
	
	/**
	 * Return A List Of Articles Characterized As 'daily' Depending On Current HOUR_OF_DAY ( Used Mostly For NEWSPAPER Articles )
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @return List Of Articles
	 */
	List<Article> getDailyArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, int maxArticles);

	/**
	 * Return A List Of Articles In A Date Range (Based On The Published Date)
	 * @param sectionUniqueName Article's Category sectionUniqueName
	 * @param publicationName Article's Category publicationName
	 * @param articleType Article's articleType
	 * @param fromCalendar Article's Published Date Bigger Than fromCalendar
	 * @param toCalendar Article's Published Date Smaller Than toCalendar
	 * @param maxArticles Maximum Number Of Returned Articles
	 * @return List Of Articles
	 */
	List<Article> getDateArticlesBySectionUniqueNameArticleType(String sectionUniqueName, String publicationName, ArticleType articleType, Calendar fromCalendar, Calendar toCalendar, int maxArticles);
	
	/**
	 * Search Articles With eceArticleId Or title Like A Given Term
	 * @param term Search Term
	 * @return List Of String Results For Autocomplete Form
	 */
	List<String> searchArticlesByAttributesLike(String term);
	
	/**
	 * Archive Article Object Given It's Id
	 * @param id Article's Id
	 */
	void archiveArticle(Long id);
	
	/**
	 * Given An Article URI Return Article's eceArticleId
	 * @param uri Requested Article URI
	 * @return Article's eceArticleId
	 */
	String getEceArticleIdFromUri(String uri);
	
	/**
	 * Given An Article's Id And Author's Id Delete The Article / Author Relationship (If Author Is Not The Last One)
	 * @param articleId Article's Id
	 * @param authorId Author's Id
	 * @return true If The Relationship Deleted Successfully, Otherwise false
	 */
	boolean deleteArticleAuthor(Long articleId, Long authorId);
	
	/**
	 * Given An Article's Id And Category's Id Delete The Article / Category Relationship (If Category Is Not The Home Category)
	 * @param articleId Article's Id
	 * @param categoryId Category's Id
	 */
	void deleteArticleCategory(Long articleId, Long categoryId);

	/**
	 * Given An Article's Id And Related Article's Id Delete The Article / RelatedArticle Relationship
	 * @param articleId Article's Id
	 * @param relatedArticleId Related Article's Id
	 * @param enclosureComment ArticleRelatedArticle's Enclosure Comment
	 */
	void deleteArticleRelatedArticle(Long articleId, Long relatedArticleId, String enclosureComment);
	
	/**
	 * Get Article Categories Given It's eceArticleId
	 * @param eceArticleId Article's eceArticleId
	 * @return List Of Article's Categories
	 */
	List<Category> getArticleCategories(String eceArticleId);
	
	/**
	 * Get An Article's First Related Picture Having A Given Caption
	 * @param eceArticleId Article's eceArticleId
	 * @param caption Picture Caption To Search For
	 * @return The Picture Having The Given Caption
	 */
	Picture getArticlePictureByCaption(String eceArticleId, String caption);
	
	/**
	 * Get A Photostory's Picture Alternates And Captions Given It's eceArticleId. If The Photostory Does Not Exist Try To Parse It Dynamically
	 * @param eceArticleId Photostory's eceArticleId
	 * @return Map Of Picture Alternates And Captions
	 */
	Map<String,String> getPhotostoryPictures(String eceArticleId);
	
	/**
	 * Get Previous Or Next Article URL Given A 'current' Article (By datePublished)
	 * @param article Current Article Object
	 * @param next true To Get The Next Article, false To Get The Previous One
	 * @return Next | Previous Article URL
	 */
	String previousOrNextUrlByDatePublished(Article article, boolean next);
	
	/**
	 * Get Previous Or Next Article URL Given A 'current' Article (By eceArticleId)
	 * @param article Current Article Object
	 * @param next true To Get The Next Article, false To Get The Previous One
	 * @return Next | Previous Article URL
	 */
	String previousOrNextUrlByEceArticleId(Article article, boolean next);
	
	/**
	 * Forced Update Of An Article Without Taking Into Account It's dateLastUpdated Value
	 * @param eceArticleId Article's eceArticleId
	 * @return Article Object
	 */
	Article forcedUpdate(String eceArticleId);
}