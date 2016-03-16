package gr.media24.mSites.data.dao;

import java.util.List;

import gr.media24.mSites.data.entities.Publication;
import gr.media24.mSites.data.entities.Tag;

/**
 * Tag's DAO Interface
 * @author npapadopoulos
 */
public interface TagDao extends AbstractDao<Tag> {

	/**
	 * Get All Tags [ Just Calling The 2 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param maxTags Maximum Number Of Returned Results
	 * @return List Of Tags
	 */
	List<Tag> getAll(int maxTags);
	
	/**
	 * Get All Tags
	 * @param maxTags Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Tags
	 */
	List<Tag> getAll(int maxTags, int offset);

	/**
	 * Get Tags By The Given Name And Publication Name
	 * @param name Tag's Name
	 * @param publicationName Tag's Publication Name
	 * @return List Of Tag Objects With The Given Name / Publication Name
	 */
	List<Tag> getByNamePublicationName(String name, String publicationName); //Theoretically The Tag Name Should Be Unique For Every Publication, But It Isn't

	/**
	 * Get Tag By The Given Name, Display Name And Publication Name
	 * @param name Tag's Name
	 * @param displayName Tag's Display Name
	 * @param publicationName Tag's Publication Name
	 * @return Tag Object If Name / Display Name / Publication Name Exists, Otherwise null
	 */
	Tag getByNameDisplayNamePublicationName(String name, String displayName, String publicationName);
	
	/**
	 * Search For A Tag By name, displayName, publication and If It Exists Return It, Otherwise Create A New One
	 * @param name Tag's Name
	 * @param displayName Tag's Display Name
	 * @param publication Tag's Publication
	 * @return Existing Or New Tag Object
	 */
	Tag tagExists(String name, String displayName, Publication publication);
}
