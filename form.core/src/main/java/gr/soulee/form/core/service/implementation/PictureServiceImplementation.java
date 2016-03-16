package gr.media24.mSites.core.service.implementation;

import gr.media24.mSites.core.service.PictureService;
import gr.media24.mSites.data.dao.PictureDao;
import gr.media24.mSites.data.entities.Picture;
import gr.media24.mSites.data.enums.ArticleState;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Picture's Service Implementation
 * @author npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class PictureServiceImplementation implements PictureService {

	@Autowired private PictureDao pictureDao;

	@Override
	@Transactional(readOnly = false)
	public void mergePicture(Picture picture) {
		/*
		 * Only Certain Fields Are Exposed To The Editing Form So Get A Fresh Copy, Update Only These Fields And Merge
		 */
		Picture existing = getPicture(picture.getId());
		existing.setArticleState(ArticleState.EDIT);
		existing.setTitle(picture.getTitle());
		existing.setCredits(picture.getCredits());
		existing.setCaption(picture.getCaption());
		pictureDao.merge(existing);
	}

	@Override
	public Picture getPicture(Long id) {
		Picture picture = pictureDao.get(id);
		Hibernate.initialize(picture.getCategories());
		Hibernate.initialize(picture.getAuthors());
		return picture;
	}
}
