package gr.media24.mSites.data.dao;

import java.util.List;

import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.FeedStatus;

/**
 * Feed's DAO Interface
 * @author npapadopoulos
 */
public interface FeedDao extends AbstractDao<Feed> {

	/**
	 * Get Feed By The Given URL and Params
	 * @param url Feed's URL
	 * @param params Feed's Params
	 * @return Feed Object
	 */
	Feed getByUrlParams(String url, String params);
	
	/**
	 * Get A Feed By The Given Feed Flags Related With A Category That Belongs To The Given Publication
	 * @param publicationName Feed's Category Publication Name
	 * @param feedFlags Feed's Flags
	 * @return (Random) Feed Object
	 */
	Feed getRandomByPublicationNameAndFeedFlags(String publicationName, List<FeedFlag> feedFlags);
	
	/**
	 * Get Feeds By The Given Feed Flag
	 * @param feedFlag Feed's Flag
	 * @return List Of Feed Objects
	 */
	List<Feed> getByFeedFlag(FeedFlag feedFlag);
	
	/**
	 * Get Feeds By The Given Feed Flag And Status 
	 * @param feedFlags List Of Feed Flags
	 * @param feedStatus Feed's Status
	 * @return List Of Feed Objects
	 */
	List<Feed> getByFeedFlagsAndFeedStatus(List<FeedFlag> feedFlags, FeedStatus feedStatus);
}
