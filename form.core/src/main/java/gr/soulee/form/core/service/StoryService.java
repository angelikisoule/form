package gr.media24.mSites.core.service;

import gr.media24.mSites.data.entities.Story;

/**
 * Story's Service Interface
 * @author npapadopoulos
 */
public interface StoryService {

	/**
	 * Merge Story Object Given Only Some Of It's Attributes From An Editing Form
	 * @param story Story Object
	 */
	void mergeStory(Story story);
	
	/**
	 * Get Story Object Given It's Id
	 * @param id Story's Id
	 * @return Story Object
	 */
	Story getStory(Long id);
}
