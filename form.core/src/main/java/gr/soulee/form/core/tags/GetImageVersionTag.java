package gr.media24.mSites.core.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Given An Image URL Get A Different Image Version Depending On The Requested width And cropped Attributes 
 * @author npapadopoulos
 */
public class GetImageVersionTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String input;
	private String width;
	private boolean cropped = false;

    @Override
    public int doStartTag() throws JspException {
    	pageContext.setAttribute(getId(), getImageVersion(input, width, cropped));
        return SKIP_BODY;
    }

    private String getImageVersion(String input, String width, boolean cropped) {
    	String result = input.replaceAll("/w[0-9]{2,3}/", "/" + width + "/");
    	if(cropped) {
    		result = result.replace("/BINARY/", "/ALTERNATES/");
        }
        else {
        	result = result.replace("/ALTERNATES/", "/BINARY/");
        }
        return result;
    }

    @Override
    public void release() {
    	this.input = null;
        this.width = null;
        this.cropped = false;
    }

    public String getInput() {
    	return input;
    }

    public void setInput(String input) {
    	this.input = input;
    }

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isCropped() {
		return cropped;
	}

	public void setCropped(boolean cropped) {
		this.cropped = cropped;
	}
}