package gr.media24.mSites.core.service.implementation;

import gr.media24.mSites.core.service.PhotostoryService;
import gr.media24.mSites.data.dao.PhotostoryDao;
import gr.media24.mSites.data.entities.Photostory;
import gr.media24.mSites.data.enums.ArticleState;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Photostory's Service Implementation
 * @author npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class PhotostoryServiceImplementation implements PhotostoryService {

	@Autowired private PhotostoryDao photostoryDao;

	@Override
	@Transactional(readOnly = false)
	public void mergePhotostory(Photostory photostory) {
		/*
		 * Only Certain Fields Are Exposed To The Editing Form So Get A Fresh Copy, Update Only These Fields And Merge
		 */
		Photostory existing = getPhotostory(photostory.getId());
		existing.setArticleState(ArticleState.EDIT);
		existing.setTitle(photostory.getTitle());
		existing.setSupertitle(photostory.getSupertitle());
		existing.setLeadText(photostory.getLeadText());
		photostoryDao.merge(existing);
	}

	@Override
	public Photostory getPhotostory(Long id) {
		Photostory photostory = photostoryDao.get(id);
		Hibernate.initialize(photostory.getCategories());
		Hibernate.initialize(photostory.getAuthors());
		Hibernate.initialize(photostory.getRelatedArticles());
		return photostory;
	}
}
