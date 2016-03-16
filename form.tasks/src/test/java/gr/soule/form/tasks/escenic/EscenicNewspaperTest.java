package gr.media24.mSites.tasks.escenic;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.xmlpull.v1.XmlPullParserException;

import gr.dsigned.atom.domain.AtomEntry;
import gr.media24.mSites.data.dao.ArticleDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Newspaper;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.tasks.TasksTest;

/**
 * Tests For Escenic Newspapers
 * @author npapadopoulos
 */
public class EscenicNewspaperTest extends TasksTest {

	@Autowired private SessionFactory sessionFactory;
    @Autowired private EscenicUpdater updater;
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
        for(int i = 0; i < 5; i++) { //Test 5 Random Entries Of The 12 In Total
			int random = generator.nextInt(12);
			AtomEntry entry = newspapers.get(random);
	        ArticleType result = updater.getArticleType(entry);
	        assertEquals(ArticleType.NEWSPAPER, result);
		}
    }
    
    /**
     * Make Sure That You Don't Read Relations For NEWSPAPER Articles. NEWSPAPER's Alternate And Related Enclosures Have
     * The Same eceArticleId So If You'll Try To Persist The Enclosure As A PICTURE You'll Get A Duplicate Entry Exception
     */
    @Test
    public void persistAndFieldsTest() throws XmlPullParserException, IOException, InstantiationException, IllegalAccessException {
    	Feed feed = feedDao.getByFeedFlag(FeedFlag.ESCENIC).get(0); //There's Only One
    	updater.entriesToArticles(newspapers, feed, 0);
        sessionFactory.getCurrentSession().flush();
        /*
    	 * Make Sure That You Do Not Persist Related Articles
    	 */
        List<Article> articles = articleDao.getByArticleType(ArticleType.NEWSPAPER, 15, null);
    	Assert.assertTrue(articles.size() == 10); //Intentionally 2 Articles Have Dates Not In Range
    	for(Article article : articles) {
    		Assert.assertTrue(article.getRelatedArticles().size() == 0);
    	}
    	/*
    	 * Make Sure That You Persist link Fields
    	 */
    	Newspaper newspaper = (Newspaper) articleDao.getByEceArticleId("3396512");
    	Assert.assertNotNull(newspaper.getLink());
    }
}