package gr.media24.mSites.core.service.implementation;

import gr.media24.mSites.core.service.VideoService;
import gr.media24.mSites.data.dao.VideoDao;
import gr.media24.mSites.data.entities.Video;
import gr.media24.mSites.data.enums.ArticleState;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Video's Service Implementation
 * @author npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class VideoServiceImplementation implements VideoService {

	@Autowired private VideoDao videoDao;

	@Override
	@Transactional(readOnly = false)
	public void mergeVideo(Video video) {
		/*
		 * Only Certain Fields Are Exposed To The Editing Form So Get A Fresh Copy, Update Only These Fields And Merge
		 */
		Video existing = getVideo(video.getId());
		existing.setArticleState(ArticleState.EDIT);
		existing.setTitle(video.getTitle());
		existing.setSupertitle(video.getSupertitle());
		existing.setLeadText(video.getLeadText());
		videoDao.merge(existing);
	}

	@Override
	public Video getVideo(Long id) {
		Video video = videoDao.get(id);
		Hibernate.initialize(video.getCategories());
		Hibernate.initialize(video.getAuthors());
		Hibernate.initialize(video.getRelatedArticles());
		return video;
	}
}
