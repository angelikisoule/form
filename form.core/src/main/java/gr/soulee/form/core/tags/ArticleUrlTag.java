package gr.media24.mSites.core.tags;

import gr.media24.mSites.data.entities.Advertorial;
import gr.media24.mSites.data.entities.Article;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Get The Article's URL From It's Alternate
 * @author npapadopoulos
 */
public class ArticleUrlTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private Article article;
	
    @Override
    public int doStartTag() throws JspException {
    	pageContext.setAttribute(getId(), getArticleUrl(article));
        return SKIP_BODY;
    }
        
    /**
     * The Mobile's Article URL Must Be Identical With The Alternate Read From The Main Publications Prefixed With 'm.' 
     * Article URL Should Not Depend On Server's Name Or Port Number [ Both m.ladylike.gr and localhost:8080 Should Be Acceptable ]
     * @param article Article Object
     * @return Article's URL
     */
    public static String getArticleUrl(Article article) {
    	/*
    	 * Spring exposes the current HttpServletRequest object through a wrapper object of type ServletRequestAttributes. This wrapper 
    	 * object is bound to ThreadLocal and is obtained by calling the static method RequestContextHolder.currentRequestAttributes()
    	 */
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	
    	StringBuilder url = new StringBuilder();
    	if(article instanceof Advertorial) {
    		return article.getAlternate(); //Article URL Don't Change
    	}
    	else {
    		/*
    		 * Example: http://rihanna:8080/naked?lastname=whoknows&age=whocares
    		 * request.getScheme() 		:	"http"
    		 * request.getServerName()	:	"rihanna"
    		 * request.getServerPort()	:	"8080"
    		 * request.getRequestURI()	:	"/naked"
    		 * request.getQueryString()	:	"lastname=whoknows&age=whocares"
    		 */
    		String[] tokens = article.getAlternate().replaceAll("http://", "").split("/");
    		String noServerNameAndPort = article.getAlternate().replaceAll("http://", "").replaceAll(tokens[0], "");
    		url.append("http://");
    		url.append(request.getServerName());
    		if(request.getServerPort()!=80) { //Default http Port
    			url.append(":" + request.getServerPort());
    		}
    		url.append(noServerNameAndPort);
    	}
    	return url.toString();
    }
    
    @Override
    public void release() {
        this.article = null;
    }
    
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}