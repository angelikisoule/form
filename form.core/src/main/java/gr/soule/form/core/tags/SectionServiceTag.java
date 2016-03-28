package gr.soule.form.core.tags;

import gr.media24.mSites.core.Settings;
import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.enums.ArticleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * Get Articles Of A Given Section Having The Specified Article Types
 * [ You Can Get Similar Results Using The 'group' Variants Of ArticleServiceTag ]
 * @author nk, tk, npapadopoulos
 */
public class SectionServiceTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(SectionServiceTag.class.getName());
	
	private ArticleService articleService;
	private Settings settings;
	private String name;
	private String publicationName;
	private String articleTypes;
	private Integer maxItems;
	private int offset = 0;
	private boolean lazy = false;

	@Override
	public int doStartTag() throws JspException {
		List<Article> articlesList = new ArrayList<Article>();
		articleService = (ArticleService) pageContext.getAttribute("articleService", PageContext.REQUEST_SCOPE);
		settings = (Settings) pageContext.getAttribute("settings", PageContext.REQUEST_SCOPE);
		try {
			if(publicationName==null || publicationName.isEmpty()) publicationName = settings.getDefaultPublicationName(); //Use Default Publication If No Publication Is Defined
			List<ArticleType> articleTypesList = articleTypes();
			LinkedHashSet<Article> sectionArticles = articleService.getArticlesBySection(name, publicationName, lazy);
			for(Article article : sectionArticles) {
				if(articleTypesList.contains(article.getArticleType())) {
					articlesList.add(article);
				}
			}
		}
		catch(NullPointerException exception) { //No Big Deal, There Are Sections That Do Not Always Have Articles
			logger.trace("Section " + name + " Requested For SectionServiceTag Is Empty Or It Does Not Exist");
		}
		int endIndex = findEndArticleIndex(offset, articlesList.size());
		if(offset < articlesList.size()) articlesList = articlesList.subList(offset, endIndex); else articlesList = new ArrayList<Article>();		
		pageContext.setAttribute(getId(), articlesList, PageContext.REQUEST_SCOPE);
		return SKIP_BODY;
	}

	private int findEndArticleIndex(int offset, int size) {
		if(maxItems != null && maxItems > 0 && maxItems + offset < size) {
			return maxItems + offset;
		}
		else {
			return size;
		}		
	}

	@Override
	public int doEndTag() throws JspException {
		this.release();
		return EVAL_PAGE;
	}

	public void release() {
		super.release();
		this.articleService = null;
		this.settings = null;
		this.name = null;
		this.publicationName = null;
		this.articleTypes = null;
		this.maxItems = null;
		this.offset = 0;
		this.lazy = false;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(Integer maxItems) {
		this.maxItems = maxItems;
	}
	
	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
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