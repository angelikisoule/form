package gr.media24.mSites.core.service;

import gr.media24.mSites.data.entities.Publication;
import gr.media24.mSites.data.entities.Section;

import java.util.List;

import org.springframework.validation.Errors;

/**
 * Section's Service Interface
 * @author npapadopoulos
 */
public interface SectionService {

	/**
	 * Save Section Object
	 * @param section Section Object
	 * @param errors BindingResult Errors Of Section Form
	 */
	void saveSection(Section section, Errors errors);
	
	/**
	 * Get Section Object Given It's Id
	 * @param id Section's Id
	 * @return Section Object
	 */
	Section getSection(Long id);
	
	/**
	 * Get Section Object Given It's Name And Publication It Belongs To
	 * @param name Section's Name
	 * @param publication Section's Publication
	 * @return Section Object
	 */
	Section getSectionByNamePublication(String name, Publication publication);
	
	/**
	 * Get Section Object Given It's Name And Name Of The Publication It Belongs To
	 * @param name Section's Name
	 * @param publicationName Section's Publication Name
	 * @return Section Object
	 */
	Section getSectionByNamePublicationName(String name, String publicationName);
	
	/**
	 * Get All Section Objects
	 * @return List Of Sections
	 */
	List<Section> getSections();
	
	/**
	 * Get All Sections [ Just Calling The 2 Parameters Method With The Same Name, Setting offset = 0 ]
	 * @param maxSections Maximum Number Of Returned Results
	 * @return List Of Sections
	 */
	List<Section> getSections(int maxSections);
	
	/**
	 * Get All Sections
	 * @param maxSections Maximum Number Of Returned Results
	 * @param offset Beginning From The Specified Offset
	 * @return List Of Sections
	 */
	List<Section> getSections(int maxSections, int offset);
	
	/**
	 * Delete A Section Object Given It's Id
	 * @param id Section's Id
	 */
	void deleteSection(Long id);
	
	/**
	 * Delete An Article From A Section
	 * @param sectionId Section's Id
	 * @param eceArticleId Article's eceArticleId
	 */
	void deleteSectionArticle(Long sectionId, String eceArticleId);

	/**
	 * Count Sections
	 * @return Number Of Sections
	 */
	Long countSections();
	
	/**
	 * Count Section's Articles
	 * @param id Section's Id
	 * @return Number Of Articles
	 */
	Long countSectionArticles(Long id);
}
