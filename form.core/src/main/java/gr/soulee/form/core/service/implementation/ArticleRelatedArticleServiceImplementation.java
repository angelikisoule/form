package gr.media24.mSites.core.service.implementation;

import gr.media24.mSites.core.service.ArticleRelatedArticleService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * RelatedArticle's Service Implementation
 * @author npapadopoulos
 */
@Service
@Transactional(readOnly = true)
public class ArticleRelatedArticleServiceImplementation implements ArticleRelatedArticleService {

}
