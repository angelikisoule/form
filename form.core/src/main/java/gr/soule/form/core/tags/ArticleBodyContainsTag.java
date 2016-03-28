package gr.soule.form.core.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Check If Article's Body Contains A Photostory Or A YouTube / Dailymotion iFrame
 * @author npapadopoulos
 */
public class ArticleBodyContainsTag extends TagSupport  {

	private static final long serialVersionUID = 1L;

	private String body;
	
	@Override
    public int doStartTag() throws JspException {
		pageContext.setAttribute(getId(), getCharacterization(body));
        return SKIP_BODY;
	}
	
	/**
	 * Inline Photostories Are Anchors With href Containing "/galleries/photostories" And Videos Are iFrames
	 * With src Containing "youtube.com" | "dailymotion.com" (We Don't Care For AdvancedMediaCode Videos) 
	 * @param body Article's Body
	 * @return "photostory", "video" Or null
	 */
	private String getCharacterization(String body) {
		String result = null;
		Document document = Jsoup.parse(body);
		Elements anchors = document.select("a");
		for(Element anchor : anchors) {
			String href = anchor.attr("href")!=null ? anchor.attr("href").toLowerCase() : "";
			if(href.contains("/galleries/photostories/")) {
				result = "photostory";
				break; //No Need To Continue
			}
		}
		if(result!=null) { //A Photostory Wins Everything Else
			return result;
		}
		else {
			Elements iframes = document.select("iframe");
			for(Element iframe : iframes) {
				String src = iframe.attr("src")!=null ? iframe.attr("src").toLowerCase() : "";
				if(src.contains("youtube.com") || src.contains("dailymotion.com")) {
					result = "video";
					break; //No Need To Continue
				}
			}
		}
		return result;
	}
	
    @Override
    public void release() {
        this.body = null;
    }
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
}
