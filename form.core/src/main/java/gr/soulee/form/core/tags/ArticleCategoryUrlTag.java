package gr.media24.mSites.core.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Get The URL Of An Article's Home Category Given The Article's URL
 * @author npapadopoulos
 */
public class ArticleCategoryUrlTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String articleUrl;

    @Override
    public int doStartTag() throws JspException {
    	pageContext.setAttribute(getId(), articleUrl.substring(0, articleUrl.lastIndexOf("/")+1));
        return SKIP_BODY;
    }

    @Override
    public void release() {
        this.articleUrl = null;
    }

    public String getArticleUrl() {
    	return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
    	this.articleUrl = articleUrl;
    }
}