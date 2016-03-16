package gr.media24.mSites.core.service;

import gr.media24.mSites.core.utils.CustomException;
import gr.media24.mSites.data.entities.Category;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

/**
 * Category's Service Interface
 * @author npapadopoulos
 */
public interface CategoryService {
	
	/**
	 * Get Category's ModelAndView Based On The Requested URI
	 * @param uri Requested URI
	 * @param publication Default Publication
	 * @param request HttpServlet Request To Get User-Agent String et al.
	 * @return ModelAndView
	 */
	ModelAndView getCategoryModelAndView(String uri, String publicationName, HttpServletRequest request) throws CustomException;
	
	/**
	 * Save Category Object
	 * @param category Category Object
	 * @param errors BindingResult Errors Of Category Form
	 */
	void saveCategory(Category category, Errors errors);
	
	/**
	 * Get Category Object Given It's Id
	 * @param id Category's Id
	 * @return Category Object
	 */
	Category getCategory(Long id);

	/**
	 * Get Category By The Given sectionUniqueName (And NULL groupName)
	 * @param sectionUniqueName Section's Unique Name
	 * @param publicationName Category's Publication Name
	 * @return Category Object If sectionUniqueName Exists, Otherwise null
	 */
	Category getCategoryBySectionUniqueNamePublicationName(String sectionUniqueName, String publicationName);
	
	/**
	 * Get All Category Objects
	 * @return List Of Categories
	 */
	List<Category> getCategories();
	
	/**
	 * Get All Categories  [ Just Calling The 2 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param maxCategories Maximum Number Of Returned Results
	 * @return List Of Categories
	 */
	List<Category> getCategories(int maxCategories);
	
	/**
	 * Get All Categories
	 * @param maxCategories Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Categories
	 */
	List<Category> getCategories(int maxCategories, int offset);
	
	/**
	 * Get All Categories With NULL Group Name
	 * @return List Of Categories
	 */
	List<Category> getCategoriesByNullGroupName();
	
	/**
	 * Delete A Category Object Given It's Id
	 * @param id Category's Id
	 */
	void deleteCategory(Long id);
	
	/**
	 * Count Categories
	 * @return Number Of Categories
	 */
	Long countCategories();
	
	/**
	 * Get Category's sectionUniqueName From Requested URI
	 * @param uri Requested URI
	 * @return Category's sectionUniqueName
	 */
	String getSectionUniqueNameFromUri(String uri);
	
	/**
	 * In Case Of A Tag Requested Get The sectionUniqueName Of The Related With The Tag Section
	 * @param uri Requested URI
	 * @return Tag Section's sectionUniqueName
	 */
	String getTagSectionUniqueNameFromUri(String uri);
	
	/**
	 * Get A Map Of All Shopping Categories
	 * @return Map Of Shopping Categories' Section Unique Name And Name
	 */
	Map<String, String> getSelectShoppingMap();
}