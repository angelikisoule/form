package gr.media24.mSites.core.tags;

import gr.media24.mSites.core.Settings;
import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.enums.ArticleType;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

/**
 * Get A List Of Articles Satisfying The Given Tag Parameters [ ArticleType, Category's sectionUniqueName, Category's groupName et al. ]
 * @author nk, npapadopoulos, asoule
 */
public class ArticleServiceTag extends SimpleTagSupport {

	private static final Logger logger = Logger.getLogger(ArticleServiceTag.class.getName());
	
	private JspWriter out;
    private ArticleService articleService;
    private Settings settings;
    private ServletRequest servletRequest;
    private PageContext pageContext;
    private String var = "";
    private String category = "";
    private String tag = "";
    private String groupName = "";
    private String publicationName = "";
    private String articleTypes = "";
    private String maxItems = "10";
    private int offset = 0;
    private String orderBy = null; //The Default Is Ordering By datePublished
    private boolean doCountOnly = false;
    private List<Article> articles;
    private boolean lazy = false;

    public ArticleServiceTag() {

    }

    /**
     * Constructor To Help Testing. Pass Any Mocks Required Here.
     * @param out JspWriter
     * @param articleService ArticleService Interface 
     * @param servletRequest ServletRequest
     * @param pageContext Page Context
     * @param articles List Of Articles
     */
    public ArticleServiceTag(JspWriter out, ArticleService articleService, ServletRequest servletRequest, PageContext pageContext, List<Article> articles) {
        this.out = out;
        this.articleService = articleService;
        this.servletRequest = servletRequest;
        this.pageContext = pageContext;
        this.articles = articles;
    }

    protected static enum QUERYTYPE {
        CATEGORY,
        TYPES,
        TAG,
        CATEGORY_AND_GROUP,
        CATEGORY_AND_TYPES,
        CATEGORY_AND_TAG,
        CATEGORY_AND_TYPES_AND_GROUP
    }

    @Override
    public void doTag() throws JspException {
        if(pageContext == null) pageContext = (PageContext) getJspContext();
        out = pageContext.getOut();
        servletRequest = pageContext.getRequest();
        articleService = (ArticleService) servletRequest.getAttribute("articleService");
        settings = (Settings) servletRequest.getAttribute("settings");
        if(publicationName==null || publicationName.isEmpty()) publicationName = settings.getDefaultPublicationName(); //Use Default Publication If No Publication Is Defined
        List<ArticleType> articleTypesList = new ArrayList<ArticleType>();
        switch(getQueryType()) {
    		case CATEGORY:
    			if(doCountOnly) {
    				servletRequest.setAttribute(var, articleService.countArticlesBySectionUniqueName(category, publicationName));
    			}
    			else {
    				servletRequest.setAttribute(var, articleService.getArticlesBySectionUniqueName(category, publicationName, Integer.parseInt(maxItems), offset, orderBy));
    			}
    			break;
    		case TAG:
    			if(doCountOnly) {
    				servletRequest.setAttribute(var, articleService.countArticlesByTagName(tag, publicationName));
    			}
    			else {
    				servletRequest.setAttribute(var, articleService.getArticlesByTagName(tag, publicationName, Integer.parseInt(maxItems), offset, orderBy));
    			}
    			break;
          	case TYPES:
          		articleTypesList = articleTypes();
        		if(doCountOnly) {
        			servletRequest.setAttribute(var, articleService.countArticlesByArticleType(articleTypesList));
        		}
        		else {
        			servletRequest.setAttribute(var, articleService.getArticlesByArticleType(articleTypesList, Integer.parseInt(maxItems), offset, orderBy));
        		}
        		break;
             case CATEGORY_AND_GROUP:
            	 if(doCountOnly) {
            		 servletRequest.setAttribute(var, articleService.countArticlesBySectionUniqueNameGroupName(category, groupName, publicationName));
            	 }
            	 else {
            		 servletRequest.setAttribute(var, articleService.getArticlesBySectionUniqueNameGroupName(category, groupName, publicationName, Integer.parseInt(maxItems), offset, orderBy));
            	 }
            	 break;
          	case CATEGORY_AND_TYPES:
          		articleTypesList = articleTypes();
          		if(doCountOnly) {
          			servletRequest.setAttribute(var, articleService.countArticlesBySectionUniqueNameArticleType(category, publicationName, articleTypesList));
          		}
          		else if(lazy) { //Only Basic Article Fields Are Needed So We Can Skip FETCH JOINs
        			servletRequest.setAttribute(var, articleService.getArticlesBySectionUniqueNameArticleType(category, publicationName, articleTypesList, Integer.parseInt(maxItems), lazy)); //This Is A LinkedHashSet
        		}
          		else {
          			servletRequest.setAttribute(var, articleService.getArticlesBySectionUniqueNameArticleType(category, publicationName, articleTypesList, Integer.parseInt(maxItems), offset, orderBy));
          		}
          		break;
          	case CATEGORY_AND_TAG:          		
          		if(doCountOnly) {
          			servletRequest.setAttribute(var, articleService.countArticlesBySectionUniqueNameTagName(category, tag, publicationName));
          		}
          		else {
          			servletRequest.setAttribute(var, articleService.getArticlesBySectionUniqueNameTagName(category, tag, publicationName, Integer.parseInt(maxItems), offset, orderBy));
          		}
          		break;
      		case CATEGORY_AND_TYPES_AND_GROUP:
      			articleTypesList = articleTypes();
      			if(doCountOnly) {
      				servletRequest.setAttribute(var, articleService.countArticlesBySectionUniqueNameGroupNameArticleType(category, groupName, publicationName, articleTypesList));
      			}
      			else {
      				servletRequest.setAttribute(var, articleService.getArticlesBySectionUniqueNameGroupNameArticleType(category, groupName, publicationName, articleTypesList, Integer.parseInt(maxItems), offset, orderBy));
      			}
      			break;
            default: /*Hopefully You Won't Get Here*/
            	logger.error("Wrong Parameters Given To ArticleServiceTag");
            	break;
        }
    }

    /**
     * Return The Appropriate Query Type Based On The Parameters Passed To The Tag
     * @return QUERYTYPE
     */
    protected QUERYTYPE getQueryType() {   
         if(!category.isEmpty() && groupName.isEmpty() && articleTypes.isEmpty() && tag.isEmpty()) {
             return QUERYTYPE.CATEGORY;
         }
         else if(category.isEmpty() && groupName.isEmpty() && !articleTypes.isEmpty() && tag.isEmpty()) {
             return QUERYTYPE.TYPES;
         }
         else if(!category.isEmpty() && !groupName.isEmpty() && articleTypes.isEmpty() && tag.isEmpty()) {
             return QUERYTYPE.CATEGORY_AND_GROUP;
         }
         else if(!category.isEmpty() && groupName.isEmpty() && !articleTypes.isEmpty() && tag.isEmpty()) {
             return QUERYTYPE.CATEGORY_AND_TYPES;
         }
         else if(!category.isEmpty() && !groupName.isEmpty() && !articleTypes.isEmpty() && tag.isEmpty()) {
             return QUERYTYPE.CATEGORY_AND_TYPES_AND_GROUP;
         }
         else if(category.isEmpty() && groupName.isEmpty() && articleTypes.isEmpty() && !tag.isEmpty()) {
        	 return QUERYTYPE.TAG;
         }
         else if(!category.isEmpty() && groupName.isEmpty() && articleTypes.isEmpty() && !tag.isEmpty()) {
        	 return QUERYTYPE.CATEGORY_AND_TAG;
         }
         return null;
    }

    public JspWriter getOut() {
    	return out;
    }
    
    public void setOut(JspWriter out) {
        this.out = out;
    } 
    
    public ArticleService getArticleService() {
    	return articleService;
    }
    
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }
    
    public Settings getSettings() {
    	return settings;
    }
    
    public void setSettings(Settings settings) {
    	this.settings = settings;
    }
    
    public ServletRequest getServletRequest() {
    	return servletRequest;
    }
    
    public void setServletRequest(ServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
    
    public PageContext getPageContext() {
    	return pageContext;
    }
    
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }
    
    public String getVar() {
    	return var;
    }
    
    public void setVar(String var) {
        this.var = var;
    }

    public String getCategory() {
    	return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getTag() {
    	return tag;
    }
    
    public void setTag(String tag) {
    	this.tag = tag;
    }
    
    public String getGroupName() {
    	return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getPublicationName() {
    	return publicationName;
    }
    
    public void setPublicationName(String publicationName) {
    	this.publicationName = publicationName;
    }

    public String getArticleTypes() {
    	return articleTypes;
    }
    
    public void setArticleTypes(String articleTypes) {
        this.articleTypes = articleTypes;
    }
    
    public String getMaxItems() {
    	return maxItems;
    }
    
    public void setMaxItems(String maxItems) {
        this.maxItems = maxItems;
    }

    public int getOffset() {
    	return offset;
    }
 
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public String getOrderBy() {
    	return orderBy;
    }
    
    public void setOrderBy(String orderBy) {
    	this.orderBy = orderBy;
    }
    
    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public boolean isDoCountOnly() {
    	return doCountOnly;
    }
    
    public void setDoCountOnly(boolean doCountOnly) {
        this.doCountOnly = doCountOnly;
    }
    
    public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	/**
     * Create A List Of ArticleTypes From The Given Comma Separated articleTypes String
     * @return List Of Article Types
     */
    private List<ArticleType> articleTypes() {
        List<ArticleType> articleTypesList = new ArrayList<ArticleType>();
        for(String articleTypeString : articleTypes.split(",")) { //No Way To Get Here If articleTypes Is Empty
            articleTypeString = articleTypeString.trim().toUpperCase();
            if(articleTypeString.equals("")) continue;
            try {
            	articleTypesList.add(ArticleType.valueOf(articleTypeString));
            }
            catch(IllegalArgumentException exception) {
            	logger.error("The Specified enum Type Has No Constant With The Specified Name");
            }
        }
        return articleTypesList;
    }
}