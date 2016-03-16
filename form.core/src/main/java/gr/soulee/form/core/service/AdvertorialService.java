package gr.media24.mSites.core.service;

import gr.media24.mSites.data.entities.Advertorial;

/**
 * Advertorial's Service Interface
 * @author npapadopoulos
 */
public interface AdvertorialService {

	/**
	 * Merge Advertorial Object Given Only Some Of It's Attributes From An Editing Form
	 * @param advertorial Advertorial Object
	 */
	void mergeAdvertorial(Advertorial advertorial);
	
	/**
	 * Get Advertorial Object Given It's Id
	 * @param id Advertorial's Id
	 * @return Advertorial Object
	 */
	Advertorial getAdvertorial(Long id);
}
