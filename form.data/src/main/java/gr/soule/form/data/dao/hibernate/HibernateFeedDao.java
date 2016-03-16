package gr.media24.mSites.data.dao.hibernate;

import java.util.List;

import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.FeedStatus;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Feed's DAO Implementation
 * @author npapadopoulos
 */
@Repository
public class HibernateFeedDao extends HibernateAbstractDao<Feed> implements FeedDao {

	@Override
	public Feed getByUrlParams(String url, String params) {
		Query query = getSession().getNamedQuery("getFeedByUrlParams");
		query.setParameter("url", url);
		query.setParameter("params", params);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (Feed) query.uniqueResult();
	}
	
	@Override
	public Feed getRandomByPublicationNameAndFeedFlags(String publicationName, List<FeedFlag> feedFlags) {
		Query query = getSession().createQuery(	"SELECT feed " +
												"FROM Feed feed " +
												"LEFT OUTER JOIN feed.category category " +
												"LEFT OUTER JOIN category.publication publication " +
												"WHERE publication.name=:publicationName " +
												"AND feed.feedFlag IN (:feedFlags)");
		query.setParameter("publicationName", publicationName);
		query.setParameterList("feedFlags", feedFlags);
		query.setMaxResults(1);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (Feed) query.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Feed> getByFeedFlag(FeedFlag feedFlag) {
		Query query = getSession().getNamedQuery("getFeedByFeedFlag");
		query.setParameter("feedFlag", feedFlag);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (List<Feed>) query.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Feed> getByFeedFlagsAndFeedStatus(List<FeedFlag> feedFlags, FeedStatus feedStatus) {
		Query query = getSession().getNamedQuery("getFeedByFeedFlagsAndFeedStatus");
		query.setParameterList("feedFlags", feedFlags);
		query.setParameter("feedStatus", feedStatus);
		query.setCacheable(true);
		query.setCacheRegion("query.LONG_TERM");
		return (List<Feed>) query.list();
	}
}
