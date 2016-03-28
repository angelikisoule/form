package gr.soule.form.core.tags;

import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.entities.Newspaper;
import gr.media24.mSites.data.entities.Picture;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.EnclosureComment;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.tag.common.core.Util;

/**
 * Get The URL Of The First Article's Picture Relation (Optionally) Having An Enclosure Comment  
 * @author tk, npapadopoulos
 */
public class GetRelatedImageTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private Article article;
	private String version;
	private boolean cropped;
    private String comment;
    private int scope;
    private String var;
    
    public GetRelatedImageTag() {
        init();
    }

    private void init() {
    	this.article = null;
    	this.version = null;
    	this.comment = null;
        this.cropped = true;        
    	this.scope = 1;
        this.var = null;
    }

    @Override
    public int doEndTag() throws JspException {
        Object picture = null;
        String url = "";
        switch(article.getArticleType()) {
	        case VIDEO:
	        case STORY:
	        case PHOTOSTORY:
	        case ADVERTORIAL:
	        	picture = this.getPicture(article.getRelatedArticles(), comment);
	        	if(picture!=null) url = transformAlternate(((Picture) picture).getAlternate());
                break;
            case NEWSPAPER:
            	picture = article;
            	url = transformAlternate(((Newspaper) picture).getLink());
            	break;
            case PICTURE: /*Pictures Have No Relations*/
            default:
            	break;
        }
        
        if(this.var != null) {
            this.pageContext.setAttribute(this.var, url, this.scope);
        }
        else {
            try {
                this.pageContext.getOut().print(url);
            }
            catch(IOException exception) {
                throw new JspTagException(exception.toString(), exception);
            }
        }
        return EVAL_PAGE;
    }
    
    /**
     * Get First Related Picture Of An Article (Having An Optional EnclosureComment)  
     * @param relatedArticles Article's Related Articles
     * @param comment Optional EnclosureComment
     * @return Picture Object
     */
    private Picture getPicture(Set<ArticleRelatedArticle> relatedArticles, String comment) {
        /*
         * We May Have More Than One Pictures With The Same Enclosure Comment (i.e. 'main'). 
         * In These Cases We Need To Preserve The Order In Which The Pictures Read From The Feed
         */
    	SortedSet<ArticleRelatedArticle> sortedRelatedArticles = new TreeSet<ArticleRelatedArticle>(new Comparator<ArticleRelatedArticle>() {
    		public int compare(ArticleRelatedArticle related1, ArticleRelatedArticle related2) {
    			return Long.valueOf(related1.getId()).compareTo(related2.getId());
    		}
    	});
    	sortedRelatedArticles.addAll(relatedArticles); //Sort The relatedArticles Given
    	for(ArticleRelatedArticle related : sortedRelatedArticles) {
        	if(related.getRelated().getArticleType().equals(ArticleType.PICTURE)) {
        		EnclosureComment enclosureComment = null;
        		if(comment!=null) {
        			try {
            			enclosureComment = EnclosureComment.valueOf(comment.toUpperCase());
            		}
            		catch(IllegalArgumentException exception) { /*Do Nothing*/
            			
            		}
        		}
        		if(enclosureComment!=null && !related.getEnclosureComment().equals(enclosureComment)) continue; //Enclosure Comment Is Optional
            	return (Picture) related.getRelated();
            }
        }
        return null;
    }

    /**
     * Transform Picture's Alternate Depending On version And cropped Parameters Given
     * @param url Initial Picture's Alternate
     * @return Transformed Picture's Alternate
     */
    private String transformAlternate(String alternate) {
    	alternate = alternate.replaceAll("/w[0-9]{2,3}[a-zA-Z]*/", "/" + version + "/");
        if(cropped) {
        	alternate = alternate.replace("/BINARY/", "/ALTERNATES/");
        }
        else {
        	alternate = alternate.replace("/ALTERNATES/", "/BINARY/");
        }
        return alternate;
    }

    @Override
    public void release() {
        init();
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isCropped() {
        return cropped;
    }

    public void setCropped(boolean cropped) {
        this.cropped = cropped;
    }
    
    public String getComment() {
    	return comment;
    }
    
    public void setComment(String comment) {
    	this.comment = comment;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = Util.getScope(scope);
    }
    
    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
}