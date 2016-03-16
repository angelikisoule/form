package gr.media24.mSites.core.service;

import java.util.List;

import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Tag;

import org.springframework.validation.Errors;

/**
 * Tag's Service Interface
 * @author npapadopoulos
 */
public interface TagService {

	/**
	 * Save Tag Object
	 * @param tag Tag Object
	 * @param errors BindingResult Errors Of Tag Form
	 */
	void saveTag(Tag tag, Errors errors);
	
	/**
	 * Get Tag Object Given It's Id
	 * @param id Tag's Id
	 * @return Tag Object
	 */
	Tag getTag(Long id);

	/**
	 * Get All Tag Objects
	 * @return List Of Tags
	 */
	List<Tag> getTags();
	
	/**
	 * Get All Tags [ Just Calling The 2 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param maxTags Maximum Number Of Returned Results
	 * @return List Of Tags
	 */
	List<Tag> getTags(int maxTags);
	
	/**
	 * Get All Tags
	 * @param maxTags Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Tags
	 */
	List<Tag> getTags(int maxTags, int offset);
	
	/**
	 * Delete A Tag Object Given It's Id
	 * @param id Tag's Id
	 */
	void deleteTag(Long id);
	
	/**
	 * Count Tags
	 * @return Number Of Tags
	 */
	Long countTags();
	
	/**
	 * Get Tag Article's Given It's Id
	 * @param id Tag's Id
	 * @return List Of Tag Articles
	 */
	List<Article> getTagArticles(Long id);
}