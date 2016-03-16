package gr.media24.mSites.core.service.implementation;

import gr.media24.mSites.core.service.StoryService;
import gr.media24.mSites.data.dao.StoryDao;
import gr.media24.mSites.data.entities.Story;
import gr.media24.mSites.data.enums.ArticleState;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Story's Service Implementation
 * @author npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class StoryServiceImplementation implements StoryService {

	@Autowired private StoryDao storyDao;

	@Override
	@Transactional(readOnly = false)
	public void mergeStory(Story story) {
		/*
		 * Only Certain Fields Are Exposed To The Editing Form So Get A Fresh Copy, Update Only These Fields And Merge
		 */
		Story existing = getStory(story.getId());
		existing.setArticleState(ArticleState.EDIT);
		existing.setTitle(story.getTitle());
		existing.setSupertitle(story.getSupertitle());
		existing.setLeadText(story.getLeadText());
		existing.setTeaserTitle(story.getTeaserTitle());
		existing.setTeaserSupertitle(story.getTeaserSupertitle());
		existing.setTeaserLeadText(story.getTeaserLeadText());
		existing.setBody(story.getBody());
		storyDao.merge(existing);
	}

	@Override
	public Story getStory(Long id) {
		Story story = storyDao.get(id);
		Hibernate.initialize(story.getCategories());
		Hibernate.initialize(story.getAuthors());
		Hibernate.initialize(story.getRelatedArticles());
		return story;
	}
}
