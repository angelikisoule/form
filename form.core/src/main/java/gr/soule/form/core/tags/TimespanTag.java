package gr.soule.form.core.tags;

import gr.media24.mSites.core.utils.JMVCDate;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

/**
 * Produce A Human Readable Time Span Description Of Given Date
 * @author nk
 */
public class TimespanTag extends SimpleTagSupport {

	private static final Logger logger = Logger.getLogger(TimespanTag.class.getName());
	
	private Calendar date;

	@Override
	public void doTag() throws JspException {
		try {
			JspWriter out = getJspContext().getOut();
			String tspan = JMVCDate.timespan(date.getTime(), new Date(), JMVCDate.TIMESPAN.MIN);
			out.print(tspan);
		}
		catch(IOException exception) {
			logger.error(exception);
		}
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}