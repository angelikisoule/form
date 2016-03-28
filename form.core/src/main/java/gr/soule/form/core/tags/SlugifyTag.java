package gr.soule.form.core.tags;

import gr.media24.mSites.core.utils.URLDecorator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author nk, tk
 */
public class SlugifyTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private String input = "";
    private String var = "";

    @Override
    public int doStartTag() throws JspException {
        String output = URLDecorator.slugify(input);
        pageContext.setAttribute(var, output);
        return SKIP_BODY;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
