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
 * Get YouTube | DailyMotion Video Thumbnail 
 * @author npapadopoulos
 */
public class VideoThumbnailTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(VideoUrlTag.class.getName());
	
	private Article article;
	
    @Override
    public int doStartTag() throws JspException {
    	pageContext.setAttribute(getId(), getVideoThumbnail(article));
    	return SKIP_BODY;
    }
    
    /**
     * Given An Article Object (Hopefully Video) Get The Video's Thumbnail If It Is A YouTube | DailyMotion Video
     * @param article Article Object
     * @return YouTube | DailyMotion Video Thumbnail Or null If Video Is Not One Of These Kinds
     */
    public static String getVideoThumbnail(Article article) {
    	String result = null;
    	if(article.getArticleType().equals(ArticleType.VIDEO)) {
	    	Video video = (Video) article;
	    	switch(video.getVideoType()) {
				case YOUTUBE:
					result = "http://img.youtube.com/vi/" + video.getVideoId() + "/hqdefault.jpg";
					break;
				case DAILYMOTION:
					result = "http://www.dailymotion.com/thumbnail/video/" + video.getVideoId();
					break;
				case ADVANCEDCODE: //This Is The Tricky One
					String code = video.getEmbeddedCode();
					if(code!=null && code.toLowerCase().contains("www.youtube.com")) {
						Pattern pattern = Pattern.compile("(?<=videos\\/|embed\\/|v=)([\\w-]+)");
					    Matcher matcher = pattern.matcher(code);
					    if(matcher.find()) {
					    	result = "http://img.youtube.com/vi/" + matcher.group(1) + "/hqdefault.jpg";
					    }
					}
					else if(code!=null && code.toLowerCase().contains("www.dailymotion.com")) {
						Pattern pattern = Pattern.compile("(?<=video\\/)([\\w-]+)");
					    Matcher matcher = pattern.matcher(code);
					    if(matcher.find()) {
					    	result = "http://www.dailymotion.com/thumbnail/video/" + matcher.group(1);
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
}