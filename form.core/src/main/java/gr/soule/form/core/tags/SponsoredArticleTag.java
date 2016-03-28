package gr.soule.form.core.tags;

import java.util.List;

import gr.media24.mSites.core.Settings;
import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Picture;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Given An Article's eceArticleId Check If The Article Is Sponsored And If It Is, Search For A Related Image With A Given Caption That Will Be Used As A Banner
 * @author npapadopoulos
 */
public class SponsoredArticleTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private ArticleService articleService;
	private Settings settings;
	private String sponsored;
	private String banner;
	private String eceArticleId;
	private String sectionUniqueName;
	private String publicationName;
	private String caption;
	
	@Override
    public int doStartTag() throws JspException {
		articleService = (ArticleService) pageContext.getAttribute("articleService", PageContext.REQUEST_SCOPE);
		settings = (Settings) pageContext.getAttribute("settings", PageContext.REQUEST_SCOPE);
        if(publicationName==null || publicationName.isEmpty()) publicationName = settings.getDefaultPublicationName(); //Use Default Publication If No Publication Is Defined
    	Boolean isSponsored = sponsoredArticle(eceArticleId, sectionUniqueName, publicationName);
		pageContext.setAttribute(getSponsored(), isSponsored);
		if(isSponsored) {
			pageContext.setAttribute(getBanner(), sponsoredBanner(eceArticleId, caption));
		}
		else {
			pageContext.setAttribute(getBanner(), null);
		}
		return SKIP_BODY;
    }
	
	/**
	 * An Article Is Considered Sponsored If It Belongs To A Given Category 
	 * @param eceArticleId Article's eceArticleId
	 * @param sectionUniqueName Category's Section Unique Name To Search For
	 * @param publicationName Category Publication's Name
	 * @return TRUE If The Article Is Sponsored, Otherwise FALSE
	 */
	private Boolean sponsoredArticle(String eceArticleId, String sectionUniqueName, String publicationName) {
		List<Category> categories = articleService.getArticleCategories(eceArticleId);
		for(Category category : categories) {
			if(category.getSectionUniqueName().equals(sectionUniqueName) && category.getPublication().getName().equals(publicationName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * The Sponsored Banner Is An Article's Photo Relation Having The Given Caption
	 * @param eceArticleId Article's eceArticleId
	 * @param searchCaption Photo Caption To Search For
	 * @return The Banner's URL Or null
	 */
	private String sponsoredBanner(String eceArticleId, String searchCaption) {
		Picture picture = articleService.getArticlePictureByCaption(eceArticleId, searchCaption);
		if(picture!=null) {
			return picture.getAlternate();
		}
		else {
			return null;
		}
	}
	
	@Override
	public void release() {
		super.release();
		this.articleService = null;
		this.settings = null;
		this.sponsored = null;
		this.banner = null;
		this.eceArticleId = null;
		this.sectionUniqueName = null;
		this.publicationName = null;
		this.caption = null;
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
	
	public String getSponsored() {
		return sponsored;
	}
	
	public void setSponsored(String sponsored) {
		this.sponsored = sponsored;
	}
	
	public String getBanner() {
		return banner;
	}
	
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	public String getEceArticleId() {
		return eceArticleId;
	}

	public void setEceArticleId(String eceArticleId) {
		this.eceArticleId = eceArticleId;
	}
	
	public String getSectionUniqueName() {
		return sectionUniqueName;
	}

	public void setSectionUniqueName(String sectionUniqueName) {
		this.sectionUniqueName = sectionUniqueName;
	}
	
	public String getPublicationName() {
		return publicationName;
	}
	
	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
}