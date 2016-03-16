package gr.media24.mSites.data.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Article's State Enumeration
 * @author npapadopoulos
 */
public enum ArticleState {
	
	NEW, ARCHIVED, EDIT;
	
	public static List<ArticleState> getListOf() {
		return Arrays.asList(ArticleState.values());
	}
}
