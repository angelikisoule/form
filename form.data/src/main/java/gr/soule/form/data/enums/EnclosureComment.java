package gr.media24.mSites.data.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @author npapadopoulos
 */
public enum EnclosureComment {

	MAIN, TEASER, INLINE, EMBED;
	
	public static List<EnclosureComment> getListOf() {
		return Arrays.asList(EnclosureComment.values());
	}
}