package gr.media24.mSites.core.service;

import gr.media24.mSites.data.entities.Author;

import java.util.List;

import org.springframework.validation.Errors;

/**
 * Author's Service Interface
 * @author npapadopoulos
 */
public interface AuthorService {

	/**
	 * Save Author Object
	 * @param author Author Object
	 * @param errors BindingResult Errors Of User Form
	 */
	void saveAuthor(Author author, Errors errors);
	
	/**
	 * Get Author Object Given It's Id
	 * @param id Author's Id
	 * @return User Object
	 */
	Author getAuthor(Long id);
	
	/**
	 * Get All Author Objects
	 * @return List Of Authors
	 */
	List<Author> getAuthors();
	
	/**
	 * Get All Authors  [ Just Calling The 2 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param maxAuthors Maximum Number Of Returned Results
	 * @return List Of Authors
	 */
	List<Author> getAuthors(int maxAuthors);
	
	/**
	 * Get All Authors
	 * @param maxAuthors Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Authors
	 */
	List<Author> getAuthors(int maxAuthors, int offset);
	
	/**
	 * Delete Author Object Given It's Id
	 * @param id Author's Id
	 */
	void deleteAuthor(Long id);
	
	/**
	 * Count Authors 
	 * @return Number Of Authors
	 */
	Long countAuthors();
}