package gr.media24.mSites.core.service.implementation;

import java.util.List;

import gr.media24.mSites.core.service.FeedService;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.entities.Feed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

/**
 * Feed's Service Implementation
 * @author npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class FeedServiceImplementation implements FeedService {

	@Autowired private FeedDao feedDao;
	
	@Override
	@Transactional(readOnly = false)
	public void saveFeed(Feed feed, Errors errors) {
		validateForm(feed, errors);
		if(!errors.hasErrors()) feedDao.persistOrMerge(feed);
	}

	@Override
	public Feed getFeed(Long id) {
		return feedDao.get(id);
	}

	@Override
	public List<Feed> getFeeds() {
		return (List<Feed>) feedDao.getAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteFeed(Long id) {
		feedDao.deleteById(id);
	}
	
	/**
	 * Custom Validations For Form Fields
	 * @param role Role Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateForm(Feed feed, Errors errors) {
		validateUrlParams(feed, errors);
	}

	/**
	 * Validate Uniqueness Of Feed's URL / Params Combination
	 * @param feed Feed Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateUrlParams(Feed feed, Errors errors) {
		Feed existing = feedDao.getByUrlParams(feed.getUrl(), feed.getParams());
		if(existing!=null && !existing.getId().equals(feed.getId())) {
			errors.rejectValue("url", "error.duplicate");
			errors.rejectValue("params", "error.duplicate");			
		}
	}
}
