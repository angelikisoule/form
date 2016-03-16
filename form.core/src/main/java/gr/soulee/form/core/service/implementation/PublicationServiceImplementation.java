package gr.media24.mSites.core.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import gr.media24.mSites.core.service.PublicationService;
import gr.media24.mSites.data.dao.PublicationDao;
import gr.media24.mSites.data.entities.Publication;

/**
 * Publication's Service Implementation
 * @author npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class PublicationServiceImplementation implements PublicationService {

	@Autowired private PublicationDao publicationDao;

	@Override
	@Transactional(readOnly = false)
	public void savePublication(Publication publication, Errors errors) {
		validateForm(publication, errors);
		if(!errors.hasErrors()) publicationDao.persistOrMerge(publication);
	}

	@Override
	public Publication getPublication(Long id) {
		return publicationDao.get(id);
	}
	
	@Override
	public Publication getPublicationByName(String name) {
		return publicationDao.getByName(name);
	}
	
	@Override
	public List<Publication> getPublications() {
		return (List<Publication>) publicationDao.getAll();
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deletePublication(Long id) {
		publicationDao.deleteById(id);
	}
	
	@Override
	public Long countPublications() {
		return publicationDao.count();
	}
	
	/**
	 * Custom Validations For Form Fields
	 * @param publication Publication Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateForm(Publication publication, Errors errors) {
		validateName(publication, errors);
	}
	
	/**
	 * Validate Uniqueness Of Publication's Name
	 * @param publication Publication Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateName(Publication publication, Errors errors) {
		Publication existing = publicationDao.getByName(publication.getName());
		if(existing!=null && !existing.getId().equals(publication.getId())) {
			errors.rejectValue("name", "error.duplicate");
		}
	}
}