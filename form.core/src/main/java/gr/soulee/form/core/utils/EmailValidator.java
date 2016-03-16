package gr.media24.mSites.core.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Validates The Email. Returns True If The String's Pattern Is Correct Otherwise False.  
 * @author asoule
 */
public class EmailValidator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	/**
	 * Validate hex with regular expression
	 * @param hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public boolean validate(final String hex) {
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}
}
