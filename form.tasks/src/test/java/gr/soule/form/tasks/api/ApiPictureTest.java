package gr.media24.mSites.tasks.api;

import gr.media24.mSites.atom.domain.AtomEntry;
import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Picture;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.tasks.TasksTest;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Tests For Api Pictures
 * @author npapadopoulos
 */
public class ApiPictureTest extends TasksTest {

	@Autowired private SessionFactory sessionFactory;
	@Autowired private ApiUpdater updater;
    @Autowired private ArticleDao articleDao;
    @Autowired private FeedDao feedDao;

    /**
     * getArticleType() Method
     * @throws XmlPullParserException
     * @throws IOException
     */
    @Test
    public void getArticleTypeTest() throws XmlPullParserException, IOException {
    	Random generator = new Random();
    	for(int i = 0; i < 5; i++) { //Test 5 Random Entries Of The 10 In Total
			int random = generator.nextInt(10);
			AtomEntry entry = pictures.get(random);
	        ArticleType result = updater.getArticleType(entry);
	        Assert.assertEquals(ArticleType.PICTURE, result);
		}
    }
    
    /**
     * Make Sure That You Can Persist apiPicture.xml And Especially Fields That You Have Only For PICTURE Articles
     * @throws XmlPullParserException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
	@Test
    public void persistAndFieldsTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.API_SECTIONS).get(0); //There's Only One
    	updater.entriesToArticles(pictures, feed, 0, false);
    	sessionFactory.getCurrentSession().flush();
    	List<Article> articles = articleDao.getByArticleType(ArticleType.PICTURE, 100, null);
    	Assert.assertTrue(articles.size() == 10);
    	/*
    	 * Make Sure That You Do Not Persist Related Articles
    	 */
    	for(Article article : articles) {
    		Assert.assertTrue(article.getRelatedArticles().size() == 0);
    	}
    	/*
    	 * Make Sure That You Do Persist caption And credits
    	 */
    	Picture picture = (Picture) articleDao.getByEceArticleId("3391371");
    	Assert.assertEquals("Ατρόμητος - Κέρκυρα", picture.getCaption());
    	Assert.assertEquals("Picture Credits", picture.getCredits());
    }
}
