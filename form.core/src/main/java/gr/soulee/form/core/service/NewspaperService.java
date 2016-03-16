package gr.media24.mSites.core.service;

import gr.media24.mSites.data.entities.Newspaper;

import org.springframework.web.servlet.ModelAndView;

/**
 * Newspaper's Service Interface
 * @author npapadopoulos
 */
public interface NewspaperService {

	/**
	 * Get Newspaper's ModelAndView Based On A Given URL
	 * @param uri Requested URI
	 * @param publicationName Default Publication's Name
	 * @param isNewspaperCategory true For Newspaper Category, false For Newspaper Article View
	 * @param datePublished Date Published Selected By The User In The Newspaper Category View 
	 * @return ModelAndView
	 */
	ModelAndView getNewspaperModelAndView(String uri, String publicationName, boolean isNewspaperCategory, String datePublished);
	
	/**
	 * Check If The Requested URL Is A Newspaper URL (Starting With /category/newspapers/)
	 * @param url Requested URL
	 * @return TRUE If The Requested URL Is Newspaper URL, Otherwise FALSE
	 */
	boolean isNewspaperUrl(String url);
	
	/**
	 * Merge Newspaper Object Given Only Some Of It's Attributes From An Editing Form
	 * @param newspaper Newspaper Object
	 */
	void mergeNewspaper(Newspaper newspaper);
	
	/**
	 * Get Newspaper Object Given It's Id
	 * @param id Newspaper's Id
	 * @return Newspaper Object
	 */
	Newspaper getNewspaper(Long id);
}
