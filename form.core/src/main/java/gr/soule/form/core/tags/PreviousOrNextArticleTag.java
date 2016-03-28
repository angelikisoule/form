package gr.soule.form.core.tags;

import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.data.entities.Article;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author npapadopoulos
 */
public class PreviousOrNextArticleTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
    private ArticleService articleService;
	private Article article;
	private boolean next = false;
	private String criterion;
	
	@Override
	public int doStartTag() throws JspException {
		String alternate = null;
		String result = null;
		/*
		 * Spring exposes the current HttpServletRequest object through a wrapper object of type ServletRequestAttributes. This wrapper object is bound to ThreadLocal and is obtained by calling the static method RequestContextHolder.currentRequestAttributes()
		 */
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		articleService = (ArticleService) pageContext.getAttribute("articleService", PageContext.REQUEST_SCOPE);
		if(criterion!=null && criterion.equals("byEceArticleId")) {
			alternate = articleService.previousOrNextUrlByEceArticleId(article, next);
		}
		else {
			alternate = articleService.previousOrNextUrlByDatePublished(article, next);
		}
		if(alternate!=null) {
			/*
			 * Example: http://rihanna:8080/naked?lastname=whoknows&age=whocares
			 * request.getScheme() 		:	"http"
			 * request.getServerName()	:	"rihanna"
			 * request.getServerPort()	:	"8080"
			 * request.getRequestURI()	:	"/naked"
			 * request.getQueryString()	:	"lastname=whoknows&age=whocares"
			 */
			StringBuilder url = new StringBuilder();
			String[] tokens = alternate.replaceAll("http://", "").split("/");
			String noServerNameAndPort = alternate.replaceAll("http://", "").replaceAll(tokens[0], "");
			url.append("http://");
			url.append(request.getServerName());
			if(request.getServerPort()!=80) { //Default http Port
				url.append(":" + request.getServerPort());
			}
			url.append(noServerNameAndPort);
			result = url.toString();
		}
		pageContext.setAttribute(getId(), result, PageContext.REQUEST_SCOPE);
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		this.release();
		return EVAL_PAGE;
	}

	public void release() {
		super.release();
		this.articleService = null;
		this.article = null;
		this.next = false;
		this.criterion = null;
	}
	
    public ArticleService getArticleService() {
        return articleService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }
	
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    
    public boolean isNext() {
    	return next;
    }
    
    public void setNext(boolean next) {
    	this.next = next;
    }
    
    public String getCriterion() {
    	return criterion;
    }
    
    public void setCriterion(String criterion) {
    	this.criterion = criterion;
    }
}