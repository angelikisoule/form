package gr.media24.mSites.data.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @author npapadopoulos
 */
public enum VideoType {

	ADVANCEDCODE, DAILYMOTION, YOUTUBE;
	
	public static List<VideoType> getListOf() {
		return Arrays.asList(VideoType.values());
	}
}
