package gr.media24.mSites.data.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @author npapadopoulos
 */
public enum FeedFlag {

	ESCENIC, API_SECTIONS, API_POOL;
	
	public static List<FeedFlag> getListOf() {
		return Arrays.asList(FeedFlag.values());
	}
}
