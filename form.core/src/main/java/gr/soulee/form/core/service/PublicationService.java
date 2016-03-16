package gr.media24.mSites.core.service;

import java.util.List;

import org.springframework.validation.Errors;

import gr.media24.mSites.data.entities.Publication;

/**
 * Publication's Service Interface
 * @author npapadopoulos
 */
public interface PublicationService {

	/**
	 * Save Publication Object
	 * @param publication Publication Object
	 * @param errors BindingResult Errors Of Publication Form
	 */
	void savePublication(Publication publication, Errors errors);
	
	/**
	 * Get Publication Object Given It's Id
	 * @param id Publication's Id
	 * @return Publication Object
	 */
	Publication getPublication(Long id);
	
	/**
	 * Get Publication Object Given It's Name
	 * @param name Publication's Name
	 * @return Publication Object
	 */
	Publication getPublicationByName(String name);
	
	/**
	 * Get All Publication Objects
	 * @return List Of Publications
	 */
	List<Publication> getPublications();
	
	/**
	 * Delete A Publication Object Given It's Id
	 * @param id Publication's Id
	 */
	void deletePublication(Long id);
	
	/**
	 * Count Publications
	 * @return Number Of Publications
	 */
	Long countPublications();
}
