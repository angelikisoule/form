package gr.media24.mSites.data.dao;

import java.util.List;

import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Publication;

/**
 * Category's DAO Interface
 * @author npapadopoulos
 */
public interface CategoryDao extends AbstractDao<Category> {
	
	/**
	 * Get All Categories  [ Just Calling The 2 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param maxCategories Maximum Number Of Returned Results
	 * @return List Of Categories
	 */
	List<Category> getAll(int maxCategories);
	
	/**
	 * Get All Categories
	 * @param maxCategories Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Categories
	 */
	List<Category> getAll(int maxCategories, int offset);
	
	/**
	 * Get A List Of Categories With groupName = NULL
	 * @return List Of Categories
	 */
	List<Category> getByNullGroupName();
	
	/**
	 * Get A List Of Categories With Section Unique Name Like The Given Term
	 * @param term Category's Section Unique Name Like Term
	 * @param publicationName Category's Publication Name
	 * @return List Of Categories
	 */
	List<Category> getBySectionUniqueNameLikePublicationName(String term, String publicationName);
	
	/**
	 * Get Category By The Given Section Unique Name / Group Name
	 * @param sectionUniqueName Section's Unique Name
	 * @param groupName Group Name
	 * @param publicationName Category's Publication Name
	 * @return Category Object If sectionUniqueName / Group Name Exists, Otherwise null
	 */
	Category getBySectionUniqueNameGroupNamePublicationName(String sectionUniqueName, String groupName, String publicationName);
	
	/**
	 * Get Category By The Given sectionUniqueName (And NULL groupName)
	 * @param sectionUniqueName Section's Unique Name
	 * @param publicationName Category's Publication Name
	 * @return Category Object If sectionUniqueName Exists, Otherwise null
	 */
	Category getBySectionUniqueNamePublicationName(String sectionUniqueName, String publicationName);
	
    /**
     * Search For A Category By name, sectionUniqueName, groupName, publicationName And If It Exists Return It, Otherwise Create A New One
     * @param name Category's Name
     * @param sectionUniqueName Category's Section Unique Name
     * @param groupName Category's Group Name
     * @param publication Category's Publication
     * @return Existing Or New Category Object
     */
	Category categoryExists(String name, String sectionUniqueName, String groupName, Publication publication);
}