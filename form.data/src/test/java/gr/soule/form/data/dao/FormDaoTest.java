package gr.soule.form.data.dao;

import gr.soule.form.data.entities.Form;

import java.util.LinkedHashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author asoule
 */
public class FormDaoTest extends DaoTest {

	@Autowired private FormDao formDao;

    /**
     * Article's getByEceArticleId Method
     */
    @Test
    public void getIdTest() {
    	Form form = formDao.getById("1010101");
    	Assert.assertNull(form);
    	form = formDao.getById("3429600");
    	Assert.assertNotNull(form);
    	//Check Article's Related Authors, Categories And Articles
    	Assert.assertTrue(form.getAuthors().size() == 1);
    	Assert.assertTrue(form.getCategories().size() == 2);
    	Assert.assertTrue(form.getRelatedArticles().size() == 2);
    }
	
    /**
     * Article's getAll Method
     */
    @Test
    public void getAllTest() {
    	List<Form> allForms = formDao.getAll();
    	Assert.assertTrue(allForms.size() == forms.size());
    	allForms = formDao.getAll(1);
    	Assert.assertTrue(allForms.size() == 1);
    }
    
    /**
     * Article's countAll Method
     */
    @Test
    public void countAllTest() {
    	Long count = formDao.count();
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
    	
    	List<Article> result = formDao.getBySectionUniqueName(sectionUniqueName, publicationName, maxArticles, null);
        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.get(0) instanceof Story);
        Assert.assertTrue(result.get(1) instanceof Story);
        //If An offset = 1 Is Set There Is Only One Result
        result = formDao.getBySectionUniqueName(sectionUniqueName, publicationName, maxArticles, offset, null);
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
        Long count = formDao.countBySectionUniqueName(uniqueName, publicationName);
        Assert.assertEquals(expectedResult, count);
    }
    
    /**
     * Article's getByArticleType Method
     */
    @Test
    public void getByArticleTypeTest() {
        int maxArticles = 10;
        int offset = 2;
        List<Article> result = formDao.getByArticleType(FormType.STORY, maxArticles, null);
        //There Are 3 Results All Instances Of Story.class
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.get(0) instanceof Story);
        Assert.assertTrue(result.get(1) instanceof Story);
        Assert.assertTrue(result.get(2) instanceof Story);
        //If An offset = 2 Is Set There Is Only One Result
        result = formDao.getByArticleType(FormType.STORY, maxArticles, offset, null);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.get(0) instanceof Story);
    }

    /**
     * Article's countByArticleType Method
     */
    @Test
    public void countByArticleTypeTest() {
    	Long count = formDao.countByArticleType(FormType.STORY);
        Long expectedResult = 3L;
    	Assert.assertEquals(expectedResult, count);
    }
    
    @Test
    public void getByArticleStateTest() {
    	int maxArticles = 10;
    	int offset = 1;
    	//There Is A PICTURE In EDIT State
    	List<Article> result = formDao.getByArticleState(State.EDIT, maxArticles);
    	Assert.assertEquals(1, result.size());
    	Assert.assertTrue(result.get(0).getArticleType().equals(FormType.PICTURE));
    	//If An offset = 1 Is Set There Are No Results
    	result = formDao.getByArticleState(State.EDIT, maxArticles, offset);
    	Assert.assertNull(result);
    }
    
    @Test
    public void countByArticleStateTest() {
    	Long count = formDao.countByArticleState(State.EDIT);
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

        List<Article> result = formDao.getBySectionUniqueNameGroupName(sectionUniqueName, groupName, publicationName, maxArticles, null);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.get(0) instanceof Story);
        //If An offset = 1 Is Set There Are No Results
        result = formDao.getBySectionUniqueNameGroupName(sectionUniqueName, groupName, publicationName, maxArticles, offset, null);
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
        Long count = formDao.countBySectionUniqueNameGroupName(sectionUniqueName, groupName, publicationName);
        Assert.assertEquals(expectedResult, count);
    }

    /**
     * Article's getBySectionUniqueNameArticleType Method
     */
    @Test
    public void getBySectionUniqueNameArticleTypeTest() {
    	String sectionUniqueName = "shopping-list";
    	String publicationName = "ladylike";
        FormType articleType = FormType.STORY;
        int maxArticles = 10;
        int offset = 2;
        
        List<Article> result = formDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType, maxArticles, null);
        Assert.assertEquals(3, result.size());
        //If An offset = 2 Is Set There Is Only One Result
        result = formDao.getBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType, maxArticles, offset, null);
        Assert.assertEquals(1, result.size());
    }
    
    /**
     * Article's countBySectionUniqueNameArticleType Method
     */
    @Test
    public void countBySectionUniqueNameArticleTypeTest() {
    	String sectionUniqueName = "shopping-list";
    	String publicationName = "ladylike";
        FormType articleType = FormType.STORY;
        Long expectedResult = 3L;
        Long count = formDao.countBySectionUniqueNameArticleType(sectionUniqueName, publicationName, articleType);
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
        FormType articleType = FormType.STORY;
        int maxArticles = 10;
        int offset = 1;
        
        List<Article> result = formDao.getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleType, maxArticles, null);
        Assert.assertEquals(2, result.size());
        //If An offset = 1 Is Set There Is Only One Result
        result = formDao.getBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleType, maxArticles, offset, null);
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
    	FormType articleType = FormType.STORY;
        Long expectedResult = 1L;
        Long count = formDao.countBySectionUniqueNameGroupNameArticleType(sectionUniqueName, groupName, publicationName, articleType);
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
    	LinkedHashSet<Article> result = formDao.getBySection(sectionName, publicationName, false);
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
    	Long count = formDao.countBySection(sectionName, publicationName);
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