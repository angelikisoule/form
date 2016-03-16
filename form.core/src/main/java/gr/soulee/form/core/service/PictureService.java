package gr.media24.mSites.core.service;

import gr.media24.mSites.data.entities.Picture;

/**
 * Picture's Service Interface
 * @author npapadopoulos
 */
public interface PictureService {

	/**
	 * Merge Picture Object Given Only Some Of It's Attributes From An Editing Form
	 * @param picture Picture Object
	 */
	void mergePicture(Picture picture);
	
	/**
	 * Get Picture Object Given It's Id
	 * @param id Picture's Id
	 * @return Picture Object
	 */
	Picture getPicture(Long id);
}
