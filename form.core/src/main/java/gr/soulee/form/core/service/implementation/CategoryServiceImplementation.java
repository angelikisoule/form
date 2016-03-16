package gr.media24.mSites.core.service.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import gr.media24.mSites.atom.domain.AtomEntry;
import gr.media24.mSites.core.Settings;
import gr.media24.mSites.core.service.CategoryService;
import gr.media24.mSites.core.utils.CustomException;
import gr.media24.mSites.core.utils.ResourceNotFoundException;
import gr.media24.mSites.data.dao.CategoryDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.dao.TagDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Tag;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.ReservedCategoryName;
import gr.media24.mSites.tasks.api.ApiUpdater;
import gr.media24.mSites.tasks.api.ApiUpdaterFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Category's Service Implementation
 * @author npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class CategoryServiceImplementation implements CategoryService {

	private static Logger logger = Logger.getLogger(CategoryServiceImplementation.class.getName());
	
	@Autowired private Settings settings;
	@Autowired private ApiUpdaterFactory updaterFactory; 
	@Autowired private ApiUpdater updater;
	@Autowired private CategoryDao categoryDao;
	@Autowired private TagDao tagDao;
	@Autowired private FeedDao feedDao;
	
	@Override
	@Transactional(readOnly = false)
	public ModelAndView getCategoryModelAndView(String uri, String publicationName, HttpServletRequest request) throws CustomException, ResourceNotFoundException {
		ModelAndView model = new ModelAndView();
		List<Article> dynamicallyRead = new ArrayList<Article>();
		String sectionUniqueName = getSectionUniqueNameFromUri(uri);
		Pattern pattern = Pattern.compile("([\\w-]+)"); //Section Can Only Contain Word Characters
	    Matcher matcher = pattern.matcher(sectionUniqueName);
	    if(!matcher.matches()) throw new ResourceNotFoundException(); //404 Page
		if(!ReservedCategoryName.isReserved(sectionUniqueName)) {
			Category category = categoryDao.getBySectionUniqueNamePublicationName(sectionUniqueName, publicationName);
			Tag tag = null;
			if(category==null) {
				String tagSection = getTagSectionUniqueNameFromUri(uri);
				List<Tag> tags = tagDao.getByNamePublicationName(sectionUniqueName, publicationName);
				tag = (tags.size()>0) ? tags.get(0) : null;
				if(tag==null) {
					String userAgent = request.getHeader("User-Agent");
					if(userAgent!=null && (userAgent.toLowerCase().contains("googlebot") || userAgent.toLowerCase().contains("bingbot"))) { //Yoshimi Battles The Pink Robots
						throw new ResourceNotFoundException(); //404 Page
					}
					try { //Try For A Section
						dynamicallyRead = readSectionFeed(sectionUniqueName);
						if(dynamicallyRead.size()==0) throw new Exception();
						category = categoryDao.getBySectionUniqueNamePublicationName(sectionUniqueName, publicationName); //Category Should Now Exist
					}
					catch(Exception e) { //You May Be Luckier If You Try For A Tag
						try {
							dynamicallyRead = readTagFeed(tagSection, sectionUniqueName);
							if(dynamicallyRead.size()==0) throw new Exception(); //Nothing More To Do, Throw A Custom Exception
							tag = tagDao.getByNamePublicationName(sectionUniqueName, publicationName).get(0); //Tag Should Now Exist
							category = categoryDao.getBySectionUniqueNamePublicationName(tagSection, publicationName); //Category Should Now Exist
						}
						catch(Exception exception) {
							logger.trace(exception.getMessage()); //Throw A Custom Exception After Logging The Real One
							throw new CustomException("getCategoryModelAndView", "Can Not Dynamically Parse Category | Tag With Unique Name '" + sectionUniqueName + "'");
						}
					}
				}
			}
			model.addObject("category", category);
			model.addObject("tag", tag);
			if(sectionUniqueName.toLowerCase().endsWith("_shopping")) {
				model.addObject("selectCategory", getSelectShoppingMap());
				model.setViewName("templates/shopping"); //TODO Maybe The template Entity Is Not A Bad Idea After All
			}
			else {
				model.setViewName("templates/category");
			}
		}
		else { //Reserved Category Names
			if(sectionUniqueName.toLowerCase().equals("shopping_list")) { //You Need To Set The selectCategory Map
				model.addObject("selectCategory", getSelectShoppingMap());
				model.setViewName("templates/shopping");
			}
			else {
				model.setViewName("templates/" + sectionUniqueName.toLowerCase());
			}
		}
		return model;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void saveCategory(Category category, Errors errors) {
		validateForm(category, errors);
		if(!errors.hasErrors()) categoryDao.persistOrMerge(category);
	}

	@Override
	public Category getCategory(Long id) {
		return categoryDao.get(id);
	}
	
	@Override
	public Category getCategoryBySectionUniqueNamePublicationName(String sectionUniqueName, String publicationName) {
		return categoryDao.getBySectionUniqueNamePublicationName(sectionUniqueName, publicationName);
	}
	
	@Override
	public List<Category> getCategories() {
		return (List<Category>) categoryDao.getAll();
	}

	@Override
	public List<Category> getCategories(int maxCategories) {
		return categoryDao.getAll(maxCategories);
	}

	@Override
	public List<Category> getCategories(int maxCategories, int offset) {
		return categoryDao.getAll(maxCategories, offset);
	}
	
	@Override
	public List<Category> getCategoriesByNullGroupName() {
		return (List<Category>) categoryDao.getByNullGroupName();
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteCategory(Long id) {
		categoryDao.deleteById(id);
	}	
	
	@Override
	public Long countCategories() {
		return categoryDao.count();
	}
	
	@Override
	public String getSectionUniqueNameFromUri(String uri) {
		String[] tokens = uri.split("/");
		if(tokens.length==0) {
			if(!uri.equals("/")) {
				logger.error("Category @RequestMapping Error, Requested URI : " + uri);
			}
			return "homepage";
		}
		return tokens[tokens.length-1];
	}

	@Override
	public String getTagSectionUniqueNameFromUri(String uri) {
		String tokens[] = uri.split("/");
		if(tokens.length<3) {
			return "ece_frontpage";
		}
		return tokens[tokens.length-2];
	}

	@Override
	public Map<String, String> getSelectShoppingMap() {
		Map<String, String> result = new LinkedHashMap<String, String>();
		List<Category> categories = categoryDao.getBySectionUniqueNameLikePublicationName("_shopping", settings.getDefaultPublicationName());
		for(Category category : categories) {
			result.put(category.getSectionUniqueName(), category.getName());
		}
		return result;
	}
	
	/**
	 * Custom Validations For Form Fields
	 * @param category Category Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateForm(Category category, Errors errors) {
		if(category.getPublication()!=null) { //Proceed To Further Validation
			if(category.getGroupName()!=null && !category.getGroupName().equals("")) {
				validateSectionUniqueNameGroupName(category, errors);
			}
			else {
				validateSectionUniqueName(category, errors);
			}
		}
	}

	/**
	 * Validate Uniqueness Of Category's Section Unique Name and Group Name
	 * @param category Category Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateSectionUniqueNameGroupName(Category category, Errors errors) {
		Category existing = categoryDao.getBySectionUniqueNameGroupNamePublicationName(category.getSectionUniqueName(), category.getGroupName(), category.getPublication().getName());
		if(existing!=null && !existing.getId().equals(category.getId())) {
			errors.rejectValue("sectionUniqueName", "error.duplicate");
			errors.rejectValue("groupName", "error.duplicate");
			errors.rejectValue("publication", "error.duplicate");
		}
	}

	/**
	 * Validate Uniqueness Of Category's Section Unique Name
	 * @param category Category Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateSectionUniqueName(Category category, Errors errors) {
		Category existing = categoryDao.getBySectionUniqueNamePublicationName(category.getSectionUniqueName(), category.getPublication().getName());
		if(existing!=null && !existing.getId().equals(category.getId())) {
			errors.rejectValue("sectionUniqueName", "error.duplicate");
			errors.rejectValue("groupName", "error.duplicate");
			errors.rejectValue("publication", "error.duplicate");
		}
	}

	/**
	 * If A Request For A Section That Does Not Exist Is Made, Try To Avoid The 404 Error By Dynamically Reading A Couple Of It's Articles From It's Feed 
	 * @param sectionUniqueName Section's sectionUniqueName
	 * @return List Of Articles Parsed
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private List<Article> readSectionFeed(String sectionUniqueName) throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
		logger.info("A Not Existing Section Requested, sectionUniqueName = '" + sectionUniqueName + "'");
		String defaultPublication = settings.getDefaultPublicationName();
		Feed randomFeed = feedDao.getRandomByPublicationNameAndFeedFlags(defaultPublication, Arrays.asList(FeedFlag.API_SECTIONS));
		List<AtomEntry> entries = updaterFactory.getAtomEntries(updater.getSectionFeed(sectionUniqueName, settings.getDefaultPublicationId(), settings.getArticlesToReadDynamically(), randomFeed));
		return updater.entriesToArticles(entries, randomFeed, 0, false);
	}

	/**
	 * If A Request For A Tag That Does Not Exist Is Made, Try To Avoid The 404 Error By Dynamically Reading A Couple Of It's Articles From It's Feed 
	 * @param name Tag's Name
	 * @return List Of Articles Parsed
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private List<Article> readTagFeed(String sectionUniqueName, String tagName) throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
		logger.info("A Not Existing Tag Requested, name = '" + tagName + "'");
		String defaultPublication = settings.getDefaultPublicationName();
		Feed randomFeed = feedDao.getRandomByPublicationNameAndFeedFlags(defaultPublication, Arrays.asList(FeedFlag.API_SECTIONS));
		List<AtomEntry> entries = updaterFactory.getAtomEntries(updater.getTagFeed(sectionUniqueName, tagName, settings.getDefaultPublicationId(), settings.getArticlesToReadDynamically(), randomFeed));
		return updater.entriesToArticles(entries, randomFeed, 0, false);
	}
}