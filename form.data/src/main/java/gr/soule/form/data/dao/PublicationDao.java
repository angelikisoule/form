package gr.media24.mSites.data.dao;

import gr.media24.mSites.data.entities.Publication;

/**
 * Publication's DAO Interface
 * @author npapadopoulos
 */
public interface PublicationDao extends AbstractDao<Publication> {

	/**
	 * Get Publication By The Given Name
	 * @param name Publication's Name
	 * @return Publication Object
	 */
	Publication getByName(String name);
}
