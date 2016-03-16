package gr.media24.mSites.core.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * To Upper Case Conversion Of An Input String 
 * @author npapadopoulos
 */
public class ToUpperCaseTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private String input;
	private String output;
	
	@Override
    public int doStartTag() throws JspException {
		pageContext.setAttribute(getOutput(), convertToUpperCase(input));
		return SKIP_BODY;
	}
	
	public static String convertToUpperCase(String input) {
		String result = input;
		result = result.replaceAll("έ", "ε");
		result = result.replaceAll("Έ", "ε");
		result = result.replaceAll("ή", "η");
		result = result.replaceAll("Ή", "η");
		result = result.replaceAll("ί", "ι");
		result = result.replaceAll("Ί", "ι");
		result = result.replaceAll("ϊ", "ι");
		result = result.replaceAll("ΐ", "ι");		
		result = result.replaceAll("ύ", "υ");
		result = result.replaceAll("Ύ", "υ");
		result = result.replaceAll("ϋ", "υ");
		result = result.replaceAll("ΰ", "υ");
		result = result.replaceAll("ώ", "ω");
		result = result.replaceAll("Ώ", "ω");
		result = result.replaceAll("ό", "ο");
		result = result.replaceAll("Ό", "ο");
		result = result.replaceAll("ά", "α");
		result = result.replaceAll("Ά", "α");
		result = result.toUpperCase();
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
