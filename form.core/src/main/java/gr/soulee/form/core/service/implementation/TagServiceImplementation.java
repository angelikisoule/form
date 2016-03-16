package gr.media24.mSites.core.service.implementation;

import java.util.List;

import gr.media24.mSites.core.service.TagService;
import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.dao.PublicationDao;
import gr.media24.mSites.data.dao.TagDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
@Transactional(readOnly = true)
public class TagServiceImplementation implements TagService {

	@Autowired private TagDao tagDao;
	@Autowired private PublicationDao publicationDao;
	@Autowired private ArticleDao articleDao;
	
	@Override
	@Transactional(readOnly = false)
	public void saveTag(Tag tag, Errors errors) {
		if(tag.getPublication()!=null) { //Proceed To Further Validation
			validateForm(tag, errors);
			if(!errors.hasErrors()) tagDao.persistOrMerge(tag);
		}
	}

	@Override
	public Tag getTag(Long id) {
		return tagDao.get(id);
	}
	
	@Override
	public List<Tag> getTags() {
		return (List<Tag>) tagDao.getAll();
	}
	
	@Override
	public List<Tag> getTags(int maxTags) {
		return tagDao.getAll(maxTags);
	}

	@Override
	public List<Tag> getTags(int maxTags, int offset) {
		return tagDao.getAll(maxTags, offset);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteTag(Long id) {
		tagDao.deleteById(id);
	}
	
	@Override
	public Long countTags() {
		return tagDao.count();
	}
	
	@Override
	public List<Article> getTagArticles(Long id) {
		Tag tag = getTag(id);
		return articleDao.getByTagName(tag.getName(), tag.getPublication().getName(), 50, null); //Hardcoded For Now
	}
	
	/**
	 * Custom Validations For Form Fields
	 * @param tag Tag Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateForm(Tag tag, Errors errors) {
		validateName(tag, errors);
	}
	
	/**
	 * Validate Uniqueness Of Tag's Name
	 * @param tag Tag Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateName(Tag tag, Errors errors) {
		Tag existing = tagDao.getByNameDisplayNamePublicationName(tag.getName(), tag.getDisplayName(), tag.getPublication().getName());
		if(existing!=null && !existing.getId().equals(tag.getId())) {
			errors.rejectValue("name", "error.duplicate");
			errors.rejectValue("displayName", "error.duplicate");
			errors.rejectValue("publication", "error.duplicate");
		}
	}
}