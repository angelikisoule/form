package gr.media24.mSites.data.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import gr.media24.mSites.data.entities.Story;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author npapadopoulos
 */
public class StoryDaoTest extends DaoTest {

	@Autowired private StoryDao storyDao;
	
    /**
     * Story's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Story> allStories = storyDao.getAll();
    	assertTrue(allStories.size() == 3);
    	
    	Story story = allStories.get(0);
    	Assert.assertTrue(story.getDatePublished().compareTo(DaoTest.testDate) == 0);
    	Assert.assertTrue(story.getAuthors().size() == 1);
    	Assert.assertTrue(story.getCategories().size() == 2);
		Assert.assertTrue(story.getRelatedArticles().size() == 2);
    }
}