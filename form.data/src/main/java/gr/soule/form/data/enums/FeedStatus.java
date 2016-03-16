package gr.media24.mSites.data.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @author npapadopoulos
 */
public enum FeedStatus {
	
	DISABLED, ENABLED;
	
	public static List<FeedStatus> getListOf() {
		return  Arrays.asList(FeedStatus.values());
	}
}
