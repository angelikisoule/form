package gr.media24.mSites.core.tags;

import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.enums.ArticleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * Get A List Of An Article's Related Articles Having The Specified Article Type's
 * @author nk, tk, npapadopoulos
 */
public class GetRelatedArticlesTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(GetRelatedArticlesTag.class.getName());
	
	private String var;
	private Article article;
    private String articleTypes;

    @Override
    public void release() {
        article = null;
        articleTypes = null;
    }

    @Override
    public int doStartTag() throws JspException {
        List<ArticleType> articleTypesList = articleTypes();
        List<Article> result = new ArrayList<Article>();
        /*
         * relatedArticles Are Loading Lazily, Make Sure That Your Input Article Is Properly Initialized 
         */
        Set<ArticleRelatedArticle> relatedArticles = article.getRelatedArticles();
        for(ArticleRelatedArticle related : relatedArticles) {
        	if(articleTypesList.contains(related.getRelated().getArticleType())) {
        		result.add(related.getRelated());
            }
        }
        
        pageContext.setAttribute(var, result);
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        this.release();
        return EVAL_PAGE;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }    
    
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getArticleTypes() {
        return articleTypes;
    }

    public void setArticleTypes(String articleTypes) {
        this.articleTypes = articleTypes;
    }

    /**
     * Create A List Of ArticleTypes From The Given Comma Separated articleTypes String
     * @return List Of Article Types
     */
    private List<ArticleType> articleTypes() {
        List<ArticleType> articleTypesList = new ArrayList<ArticleType>();
        if(articleTypes==null || articleTypes.isEmpty()) { //Return All ArticleTypes
        	articleTypesList = Arrays.asList(ArticleType.values());
        }
        else {
            for(String articleTypeString : articleTypes.split(",")) {
                articleTypeString = articleTypeString.trim().toUpperCase();
                if(articleTypeString.equals("")) continue;
                try {
                	articleTypesList.add(ArticleType.valueOf(articleTypeString));
                }
                catch(IllegalArgumentException exception) {
                	logger.error("The Specified enum Type Has No Constant With The Specified Name");
                }
            }
        }
        return articleTypesList;
    }
}