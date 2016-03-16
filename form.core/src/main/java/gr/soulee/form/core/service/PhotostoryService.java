package gr.media24.mSites.core.service;

import gr.media24.mSites.data.entities.Photostory;

/**
 * Photostory's Service Interface
 * @author npapadopoulos
 */
public interface PhotostoryService {

	/**
	 * Merge Photostory Object Given Only Some Of It's Attributes From An Editing Form
	 * @param photostory Photostory Object
	 */
	void mergePhotostory(Photostory photostory);
	
	/**
	 * Get Photostory Object Given It's Id
	 * @param id Photostory's Id
	 * @return Photostory Object
	 */
	Photostory getPhotostory(Long id);
}
