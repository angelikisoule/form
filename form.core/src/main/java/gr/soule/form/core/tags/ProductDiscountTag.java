package gr.soule.form.core.tags;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Given A Product Title Get The Title Substring Representing The Product's Discount
 * @author npapadopoulos
 */
public class ProductDiscountTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String input;
	
    @Override
    public int doStartTag() throws JspException {
    	pageContext.setAttribute(getId(), getDiscount(input));
        return SKIP_BODY;
    }
    
    /**
     * Get Product's Discount Given The Product's Title
     * @param input Product's Title
     * @return Product's Discount (If Any)
     */
    private String getDiscount(String input) {
    	String result = null;
        Pattern pattern = Pattern.compile("[-]?[0-9]*\\.?,?[0-9]+[ ]?[%]");
        Matcher matcher = pattern.matcher(input);
        while(matcher.find()) {
            result = matcher.group();
        }
        return result;
    }
	
    @Override
    public void release() {
        this.input = null;
    }
	
	public String getInput() {
		return input;
	}
	
	public void setInput(String input) {
		this.input = input;
	}
}
