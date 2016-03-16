package gr.media24.mSites.core.service;

import gr.media24.mSites.data.entities.Feed;

import java.util.List;

import org.springframework.validation.Errors;

/**
 * Feed's Service Interface
 * @author npapadopoulos
 */
public interface FeedService {

	/**
	 * Save Feed Object
	 * @param feed Feed Object
	 * @param errors BindingResult Errors Of Feed Form
	 */
	void saveFeed(Feed feed, Errors errors);
	
	/**
	 * Get Feed Object Given It's Id
	 * @param id Feed's Id
	 * @return Feed Object
	 */
	Feed getFeed(Long id);
	
	/**
	 * Get All Feed Objects
	 * @return List Of Feeds
	 */
	List<Feed> getFeeds();
	
	/**
	 * Delete A Feed Object Given It's Id
	 * @param id Feed's Id
	 */
	void deleteFeed(Long id);
}
