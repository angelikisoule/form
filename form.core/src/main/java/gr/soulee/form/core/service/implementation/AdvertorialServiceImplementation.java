package gr.media24.mSites.core.service.implementation;

import gr.media24.mSites.core.service.AdvertorialService;
import gr.media24.mSites.data.dao.AdvertorialDao;
import gr.media24.mSites.data.entities.Advertorial;
import gr.media24.mSites.data.enums.ArticleState;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Advertorial's Service Implementation
 * @author npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class AdvertorialServiceImplementation implements AdvertorialService {

	@Autowired private AdvertorialDao advertorialDao;

	@Override
	@Transactional(readOnly = false)
	public void mergeAdvertorial(Advertorial advertorial) {
		/*
		 * Only Certain Fields Are Exposed To The Editing Form So Get A Fresh Copy, Update Only These Fields And Merge
		 */
		Advertorial existing = getAdvertorial(advertorial.getId());
		existing.setArticleState(ArticleState.EDIT);
		existing.setTitle(advertorial.getTitle());
		existing.setSupertitle(advertorial.getSupertitle());
		existing.setLeadText(advertorial.getLeadText());
		existing.setTeaserTitle(advertorial.getTeaserTitle());
		existing.setTeaserSupertitle(advertorial.getTeaserSupertitle());
		existing.setTeaserLeadText(advertorial.getTeaserLeadText());
		advertorialDao.merge(existing);
	}

	@Override
	public Advertorial getAdvertorial(Long id) {
		Advertorial advertorial = advertorialDao.get(id);
		Hibernate.initialize(advertorial.getCategories());
		Hibernate.initialize(advertorial.getAuthors());
		Hibernate.initialize(advertorial.getRelatedArticles());
		return advertorial;
	}
}
