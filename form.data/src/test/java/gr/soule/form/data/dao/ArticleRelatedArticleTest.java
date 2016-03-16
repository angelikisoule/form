package gr.media24.mSites.data.dao;

import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.entities.Picture;
import gr.media24.mSites.data.entities.Video;
import gr.media24.mSites.data.enums.EnclosureComment;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author npapadopoulos
 */
public class ArticleRelatedArticleTest extends DaoTest {

	@Autowired private ArticleDao articleDao;
	@Autowired private ArticleRelatedArticleDao articleRelatedArticleDao;
	
	@Test
	public void getByArticleAndRelatedAndEnclosureCommentTest() {
		Video video = (Video) articleDao.getByEceArticleId("3430051");
		Picture picture = (Picture) articleDao.getByEceArticleId("3429417");
		ArticleRelatedArticle related = articleRelatedArticleDao.getByArticleAndRelatedAndEnclosureComment(video, picture, EnclosureComment.MAIN);
		Assert.assertNotNull(related);
		related = articleRelatedArticleDao.getByArticleAndRelatedAndEnclosureComment(video, picture, EnclosureComment.INLINE);
		Assert.assertNull(related);
	}
}
