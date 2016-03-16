package gr.media24.mSites.core.service;

import gr.media24.mSites.data.entities.Video;

/**
 * Video's Service Interface
 * @author npapadopoulos
 */
public interface VideoService {

	/**
	 * Merge Video Object Given Only Some Of It's Attributes From An Editing Form
	 * @param video Video Object
	 */
	void mergeVideo(Video video);
	
	/**
	 * Get Video Object Given It's Id
	 * @param id Video's Id
	 * @return Video Object
	 */
	Video getVideo(Long id);
}
