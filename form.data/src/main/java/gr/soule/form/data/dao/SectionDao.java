package gr.media24.mSites.data.dao;

import java.util.List;

import gr.media24.mSites.data.entities.Publication;
import gr.media24.mSites.data.entities.Section;

/**
 * Section's DAO Interface
 * @author npapadopoulos
 */
public interface SectionDao extends AbstractDao<Section> {
	
	/**
	 * Get All Sections [ Just Calling The 2 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param maxSections Maximum Number Of Returned Results
	 * @return List Of Sections
	 */
	List<Section> getAll(int maxSections);
	
	/**
	 * Get All Sections
	 * @param maxSections Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Sections
	 */
	List<Section> getAll(int maxSections, int offset);
	
	/**
	 * Get Section By The Given Name And Publication
	 * @param name Section's Name
	 * @param publicationName Section's Publication Name
	 * @return Section Object If Section Exists, Otherwise null
	 */
	Section getByNamePublicationName(String name, String publicationName);
    	
	/**
	 * Get Sections By Name Like A Given Term, And A Publication
	 * @param term Name Like Term
	 * @param publicationName Section's Publication Name
	 * @return List Of Section Objects
	 */
	List<Section> getByNameLikePublicationName(String term, String publicationName);
		
	/**
     * Search For A Section By name And Publication. If It Exists Return It, Otherwise Create A New One
     * @param name Section's Name
     * @param publication Section's Publication
     * @return Existing Or New Section Object
     */
	Section sectionExists(String name, Publication publication);
}
