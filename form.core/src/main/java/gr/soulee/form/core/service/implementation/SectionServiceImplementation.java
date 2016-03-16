package gr.media24.mSites.core.service.implementation;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import gr.media24.mSites.core.service.SectionService;
import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.dao.PublicationDao;
import gr.media24.mSites.data.dao.SectionDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Publication;
import gr.media24.mSites.data.entities.Section;

@Service
@Transactional(readOnly = true)
public class SectionServiceImplementation implements SectionService {

	@Autowired private SectionDao sectionDao;
	@Autowired private PublicationDao publicationDao;
	@Autowired private ArticleDao articleDao;
	
	@Override
	@Transactional(readOnly = false)
	public void saveSection(Section section, Errors errors) {
		if(section.getPublication()!=null) { //Proceed To Further Validation
			validateForm(section, errors);
			if(!errors.hasErrors()) sectionDao.persistOrMerge(section);
		}
	}

	@Override
	public Section getSection(Long id) {
		Section section = sectionDao.get(id);
		Hibernate.initialize(section.getArticles());
		return section;
	}

	@Override
	public Section getSectionByNamePublication(String name, Publication publication) {
		return getSectionByNamePublicationName(name, publication.getName());
	}

	@Override
	public Section getSectionByNamePublicationName(String name, String publicationName) {
		return sectionDao.getByNamePublicationName(name, publicationName);
	}
	
	@Override
	public List<Section> getSections() {
		return (List<Section>) sectionDao.getAll();
	}
	
	@Override
	public List<Section> getSections(int maxSections) {
		return sectionDao.getAll(maxSections);
	}

	@Override
	public List<Section> getSections(int maxSections, int offset) {
		return sectionDao.getAll(maxSections, offset);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteSection(Long id) {
		sectionDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteSectionArticle(Long sectionId, String eceArticleId) {
		Section section = sectionDao.get(sectionId);
		List<Article> sectionArticles = section.getArticles();
		Iterator<Article> iterator = sectionArticles.iterator();
		while(iterator.hasNext()) {
			if(iterator.next().getEceArticleId().equals(eceArticleId)) {
				iterator.remove();
			}
		}
		section.setArticles(sectionArticles);
		sectionDao.merge(section);
	}

	@Override
	public Long countSections() {
		return sectionDao.count();
	}
	
	@Override
	public Long countSectionArticles(Long id) {
		Section section = sectionDao.get(id);
		return articleDao.countBySection(section.getName(), section.getPublication().getName());
	}
	
	/**
	 * Custom Validations For Form Fields
	 * @param section Section Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateForm(Section section, Errors errors) {
		validateName(section, errors);
	}
	
	/**
	 * Validate Uniqueness Of Section's Name
	 * @param section Section Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateName(Section section, Errors errors) {
		Section existing = sectionDao.getByNamePublicationName(section.getName(), section.getPublication().getName());
		if(existing!=null && !existing.getId().equals(section.getId())) {
			errors.rejectValue("name", "error.duplicate");
			errors.rejectValue("publication", "error.duplicate");
		}
	}
}
