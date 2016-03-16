package gr.media24.mSites.core.tags;

import gr.media24.mSites.core.Settings;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Get A Cropped Image Given It's URL And Desired Width, Height
 * @author tk
 */
public class GetRelatedImageCroppedTag extends SimpleTagSupport {

    private Settings settings;
    private String url;    
    private String width;
    private String height;
    private String var;

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();        
        settings = (Settings) request.getAttribute("settings");               
        String result = url.replace("http://www." + settings.getDefaultPublicationName() +".gr/", "/cropped/")
        		.replace(".jpg", "/jpg")
                .replace(".png", "/png")
                .replace(".gif", "/gif") + "?width=" + width + "&height=" + height;
        pageContext.setAttribute(var, result);
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
       
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}