package gr.soule.form.core.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Special Characters Removal Of An Input String 
 * @author asoule
 */
public class RemoveQuotesTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private String input;
	private String output;
	
	@Override
    public int doStartTag() throws JspException {
		pageContext.setAttribute(getOutput(), removeQuotes(input));
		return SKIP_BODY;
	}
	
	public String removeQuotes(String input){
		String result = input;
		result = result.replaceAll("\"", "\'");
		result = result.replaceAll("&", "&amp;");
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		return result;
	}
	
	@Override
	public void release() {
		super.release();
		this.input = null;
		this.output = null;
	}
	
	public String getInput() {
		return input;
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public String getOutput() {
		return output;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}
}
