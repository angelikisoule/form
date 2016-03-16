package gr.media24.mSites.data.dao;

import java.util.List;

import gr.media24.mSites.data.entities.Author;

/**
 * Author's DAO Interface
 * @author npapadopoulos
 */
public interface AuthorDao extends AbstractDao<Author> {
	
    /**
     * Search For An Author By name and If It Exists Return It, Otherwise Create A New One
     * @param name Author's Name
     * @return Existing Or New Author Object
     */
	Author authorExists(String name);
	
	/**
	 * Get Author By The Given Name
	 * @param name Author's Name
	 * @return Author Object If Author Exists, Otherwise null
	 */
	Author getByName(String name);
	
	/**
	 * Get All Authors  [ Just Calling The 2 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param maxAuthors Maximum Number Of Returned Results
	 * @return List Of Authors
	 */
	List<Author> getAll(int maxAuthors);
	
	/**
	 * Get All Authors
	 * @param maxAuthors Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Authors
	 */
	List<Author> getAll(int maxAuthors, int offset);
}