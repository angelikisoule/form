package gr.soule.form.core.tags;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Video;
import gr.media24.mSites.data.enums.ArticleType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * Build YouTube | DailyMotion Video URL From Video's Id Or Embedded Code In Case Of ADVANCEDCODE Video That Contains "www.youtube.com" Or "www.dailymotion.com" 
 * @author npapadopoulos
 */
public class VideoUrlTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(VideoUrlTag.class.getName());
	
	private Article article;
	private Boolean autoplay = false;
	
    @Override
    public int doStartTag() throws JspException {
    	pageContext.setAttribute(getId(), getVideoUrl(article, autoplay));
        return SKIP_BODY;
    }
    
    /**
     * Given An Article Object (Hopefully Video) Get The Video's URL If It Is A YouTube | DailyMotion Video
     * @param article Article Object
     * @param autoplay Autoplay Video (Not Supported In Mobile Devices)
     * @return YouTube | DailyMotion Video URL Or null If Video Is Not One Of These Kinds
     */
    public static String getVideoUrl(Article article, Boolean autoplay) {
    	String result = null;
    	if(article.getArticleType().equals(ArticleType.VIDEO)) {
	    	Video video = (Video) article;
	    	switch(video.getVideoType()) {
				case YOUTUBE:
					result = "http://www.youtube.com/embed/" + video.getVideoId() + (autoplay ? "?autoplay=1" : "");
					break;
				case DAILYMOTION:
					result = "http://www.dailymotion.com/embed/video/" + video.getVideoId() + (autoplay ? "?autoPlay=1" : "");
					break;
				case ADVANCEDCODE: //This Is The Tricky One
					String code = video.getEmbeddedCode();
					if(code!=null && code.toLowerCase().contains("www.youtube.com")) {
						Pattern pattern = Pattern.compile("(?<=videos\\/|embed\\/|v=)([\\w-]+)");
					    Matcher matcher = pattern.matcher(code);
					    if(matcher.find()) {
					    	result = "http://www.youtube.com/embed/" + matcher.group(1) + (autoplay ? "?autoplay=1" : "");
					    }
					}
					else if(code!=null && code.toLowerCase().contains("www.dailymotion.com")) {
						Pattern pattern = Pattern.compile("(?<=video\\/)([\\w-]+)");
					    Matcher matcher = pattern.matcher(code);
					    if(matcher.find()) {
					    	result = "http://www.dailymotion.com/embed/video/" + matcher.group(1) + (autoplay ? "?autoPlay=1" : "");
					    }
					}
					break;
	    	}
    	}
    	else {
        	logger.error("Article Given Is Not A VIDEO");
        }
    	return result;
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
    
    public Boolean getAutoplay() {
    	return autoplay;
    }
    
    public void setAutoplay(Boolean autoplay) {
    	this.autoplay = autoplay;
    }
}