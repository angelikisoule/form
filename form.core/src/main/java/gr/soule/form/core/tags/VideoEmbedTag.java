package gr.soule.form.core.tags;

import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Video;
import gr.media24.mSites.data.enums.ArticleType;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

/**
 * Given An Article With ArticleType = VIDEO, Build A Proper Embedded Code
 * @author npapadopoulos
 */
public class VideoEmbedTag extends SimpleTagSupport {

	private static final Logger logger = Logger.getLogger(VideoEmbedTag.class.getName());
	
    private Article article;
    private Boolean autoplay = false;

    public void doTag() throws JspException, IOException {
        if(article.getArticleType().equals(ArticleType.VIDEO)) {
            Video video = (Video) article;
            String embeddedCode = "";
            switch(video.getVideoType()) {
				case ADVANCEDCODE:
					embeddedCode = video.getEmbeddedCode(); 
					break;
				case DAILYMOTION:
					embeddedCode = "<iframe frameborder=\"0\" src=\"http://www.dailymotion.com/embed/video/" + video.getVideoId() + (this.getAutoplay() ? "?autoPlay=1" : "") + "\"></iframe>";
					break;
				case YOUTUBE:
					embeddedCode = "<iframe frameborder=\"0\" src=\"http://www.youtube.com/embed/" + video.getVideoId() + (this.getAutoplay() ? "?autoplay=1" : "") + "\" allowfullscreen></iframe>";
					break;
				default: /*You Won't Get Here*/
					break;
            }
            PageContext context = (PageContext) getJspContext();
            context.getOut().println(embeddedCode);
        }
        else {
        	logger.error("Article Given Is Not A VIDEO");
        }
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Boolean getAutoplay() {
        return autoplay;
    }

    public void setAutoplay(Boolean autoplay) {
        this.autoplay = autoplay;
    }
}