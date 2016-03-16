package gr.media24.mSites.data.dao;

import java.util.LinkedHashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Story;
import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;

/**
 * @author npapadopoulos
 */
public class ArticleDaoTest extends DaoTest {

	@Autowired private ArticleDao articleDao;

    /**
     * Article's getByEceArticleId Method
     */
    @Test
    public void getByEceArticleIdTest() {
    	Article article = articleDao.getByEceArticleId("1010101");
    	Assert.assertNull(article);
    	article = articleDao.getByEceArticleId("3429600");
    	Assert.assertNotNull(article);
    	//Check Article's Related Authors, Categories And Articles
    	Assert.assertTrue(article.getAuthors().size() == 1);
    	Assert.assertTrue(article.getCategories().size() == 2);
    	Assert.assertTrue(article.getRelatedArticles().size() == 2);
    }
	
    /**
     * Article's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Article> allArticles = articleDao.getAll();
    	Assert.assertTrue(allArticles.size() == articles.size());
    	allArticles = articleDao.getAll(1);
    	Assert.assertTrue(allArticles.size() == 1);
    }
    
    /**
     * Article's countAll Method
     */
    @Test
    public void countAllTest() {
    	Long count = articleDao.countAll();
    	Assert.assertTrue(count > 0);
    }
    
    /**
     * Article's getBySectionUniqueName Method
     */
    @Test
    public void getBySectionUniqueNameTest() {
    	String sectionUniqueName = "celebrities";
    	String publicationName = "ladylike";
    	int maxArticles = 10;
    	int offset = 1;
    	
    	List<Article> result = articleDao.getBySectionUniqueName(sectionUniqueName, publicationName, maxArticles, null);
        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.get(0) instanceof Story);
        Assert.assertTrue(result.get(1) instanceof Story);
        //If An offset = 1 Is Set There Is Only One Result
        result = articleDao.getBySectionUniqueName(sectionUniqueName, publicationName, maxArticles, offset, null);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.get(0) instanceof Story);
    }

    /**
     * Article's countBySectionUniqueName Method
     */
    @Test
    public void countBySectionUniqueNameTest() {
    	String uniqueName = "celebrities";
    	String publicationName = "ladylike";
        Long expectedResult = 2L;
        Long count = articleDao.countBySectionUniqueName(uniqueName, publicationName);
        Assert.assertEquals(expectedResult, count);
    }
    
    /**
     * Article's getByArticleType Method
     */
    @Test
    public void getByArticleTypeTest() {
        int maxArticles = 10;
        int offset = 2;
        List<Article> result = articleDao.getByArticleType(ArticleType.STORY, maxArticles, null);
        //There Are 3 Results All Instances Of Story.class
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.get(0) instanceof Story);
        Assert.assertTrue(result.get(1) instanceof Story);
        Assert.assertTrue(result.get(2) instanceof Story);
        //If An offset = 2 Is Set There Is Only One Result
        result = articleDao.getByArticleType(ArticleType.STORY, maxArticles, offset, null);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.get(0) instanceof Story);
    }

    /**
     * Article's countByArticleType Method
     */
    @Test
    public void countByArticleTypeTest() {
    	Long count = articleDao.countByArticleType(ArticleType.STORY);
        Long expectedResult = 3L;
    	Assert.assertEquals(expectedResult, count);
    }
    
    @Test
    public void getByArticleStateTest() {
    	int maxArticles = 10;
    	int offset = 1;
    	//There Is A PICTURE In EDIT State
    	List<Article> result = articleDao.getByArticleState(ArticleState.EDIT, maxArticles);
    	Assert.assertEquals(1, result.size());
    	Assert.assertTrue(result.get(0).getArticleType().equals(ArticleType.PICTURE));
    	//If An offset = 1 Is Set There Are No Results
    	result = articleDao.getByArticleState(ArticleState.EDIT, maxArticles, offset);
    	Assert.assertNull(result);
    }
    
    @Test
    public void countByArticleStateTest() {
    	Long count = articleDao.countByArticleState(ArticleState.EDIT);
    	Long expectedResult = 1L;
    	Assert.assertEquals(expectedResult, count);
    }

    /**
     * Article's getBySectionUniqueNameGroupName Test
     */
    @Test
    public void getBySectionUniqueNameGroupNameTest() {
    	String sectionUniqueName = "Women";
        String groupName = "@main2";
        String publicationName = "ladylike";
        int maxArticles = 10;
        int offset = 1;

        List<Article> result = articleDao.getBySectionUniqueNameGroupName(sectionUniqueName, groupName, publicationName, maxArticles, null);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.get(0) instanceof Story);
        //If An offset = 1 Is Set There Are No Results
        result = articleDao.getBySectionUniqueNameGroupName(sectionUniqueName, groupName, publicationName, maxArticles, offset, null);
        Assert.assertNull(result);
    }

    /**
     * Article's countBySectionUniqueNameGroupName Method
     */
    @Test
    public void countBySectionUniqueNameGroupNameTest() {
    	String sectionUniqueName = "celebrities";
    	String groupName = "@main1";
        String publicationName = "ladylike";
    	Long expectedResult = 2L;
        Long count = articleDao.countBySectionUniqueNameGroupName(sectionUniqueName, groupName, publicationName);
        Assert.assertEquals(expectedResult, count);
    }

    /**
     * Article's getBySectionUniqueNameArticleType Method
     */
    @Test
    public void getBySectionUniqueNameArticleTypeTest() {
    	String sectionUniqueName = "shopping-list";
    	String publicationName = "ladylike";
        ArticleType articleType = ArticleType.STORY;
        int maxArticles = 10;
        int offset = 2;
        
        List<Article> result = articleDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType, maxArticles, null);
        Assert.assertEquals(3, result.size());
        //If An offset = 2 Is Set There Is Only One Result
        result = articleDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType, maxArticles, offset, null);
        Assert.assertEquals(1, result.size());
    }
    
    /**
     * Article's countBySectionUniqueNameArticleType Method
     */
    @Test
    public void countBySectionUniqueNameArticleTypeTest() {
    	String sectionUniqueName = "shopping-list";
    	String publicationName = "ladylike";
        ArticleType articleType = ArticleType.STORY;
        Long expectedResult = 3L;
        Long count = articleDao.countBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType);
        Assert.assertEquals(expectedResult, count);
    }

    /**
     * Article's getBySectionUniqueNameGroupNameArticleType Method
     */
    @Test
    public void getBySectionUniqueNameGroupNameArticleTypeTest() {
    	String sectionUniqueName = "celebrities";
    	String groupName = "@main1";
    	String publicationName = "ladylike";
        ArticleType articleType = ArticleType.STORY;
        int maxArticles = 10;
        int offset = 1;
        
        List<Article> result = articleDao.getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleType, maxArticles, null);
        Assert.assertEquals(2, result.size());
        //If An offset = 1 Is Set There Is Only One Result
        result = articleDao.getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleType, maxArticles, offset, null);
        Assert.assertEquals(1, result.size());
    }
    
    /**
     * Article's countBySectionUniqueNameGroupNameArticleType Method
     */
    @Test
    public void countBySectionUniqueNameGroupNameArticleTypeTest() {
    	String sectionUniqueName = "Women";
    	String groupName = "@main2";
        String publicationName = "ladylike";
    	ArticleType articleType = ArticleType.STORY;
        Long expectedResult = 1L;
        Long count = articleDao.countBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleType);
        Assert.assertEquals(expectedResult, count);
    }
    
    /**
     * Article's getByTagName Method
     */
    @Ignore
    public void getByTagNameTest() {
    	//TODO Test: getByTagName()
    }
    
    /**
     * Article's countByTagName Method
     */
    @Ignore
    public void countByTagNameTest() {
    	//TODO Test: countByTagName()
    }
    
    /**
     * Article's getBySectionUniqueNameTagName Method
     */
    @Ignore
    public void getBySectionUniqueNameTagNameTest() {
    	//TODO Test: getBySectionUniqueNameTagName()
    }
    
    /**
     * Article's countBySectionUniqueNameTagName Method
     */
    @Ignore
    public void countBySectionUniqueNameTagName() {
    	//TODO Test: countBySectionUniqueNameTagName()
    }
    
    /**
     * Article's getBySection Method
     */
    @Test
    public void getBySectionTest() {
    	String sectionName = "newsletter@topStories1";
    	String publicationName = "ladylike";
    	int expectedResult = 3;
    	LinkedHashSet<Article> result = articleDao.getBySection(sectionName, publicationName, false);
        Assert.assertEquals(expectedResult, result.size());
    	for(Article article : result) {
    		Assert.assertTrue(article instanceof Story);
    	}
    }
    
    /**
     * Article's countBySection Method
     */
    @Test
    public void countBySectionTest() {
    	String sectionName = "newsletter@topStories1";
    	String publicationName = "ladylike";
    	Long expectedResult = 3L;
    	Long count = articleDao.countBySection(sectionName, publicationName);
    	Assert.assertEquals(expectedResult, count);
    }
    
    /**
     * Article's getDailyBySectionUniqueNameArticleType Method
     */
    @Ignore
    public void getDailyBySectionUniqueNameArticleTypeTest() {
    	//TODO Test: getDailyBySectionUniqueNameArticleTypeTest
    }
    
    /**
     * Article's searchByAttributesLike Method
     */
    @Ignore
    public void searchByAttributesLikeTest() {
    	//TODO Test: searchByAttributesLikeTest
    }
}