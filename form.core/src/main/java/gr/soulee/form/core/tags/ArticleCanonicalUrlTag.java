package gr.media24.mSites.core.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Canonical URLs Must Always Have The "http://www." Prefix
 * @author npapadopoulos
 */
public class ArticleCanonicalUrlTag  extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private String articleAlternate;

    @Override
    public int doStartTag() throws JspException {
    	String result = articleAlternate.replaceAll("http://", ""); //No Need For https://
    	if(!result.startsWith("www.")) result = "http://www." + result;
    	pageContext.setAttribute(getId(), result);
    	return SKIP_BODY;
    }

    @Override
    public void release() {
        this.articleAlternate = null;
    }

    public String getArticleAlternate() {
    	return articleAlternate;
    }

    public void setArticleAlternate(String articleAlternate) {
    	this.articleAlternate = articleAlternate;
    }
}