package gr.media24.mSites.data.enums;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * Article's Type Enumeration
 * @author nk, tk, npapadopoulos
 */
public enum ArticleType {
	
	VIDEO, PICTURE, STORY, PHOTOSTORY, NEWSPAPER, ADVERTORIAL;

	public static List<ArticleType> getListOf(String types) {
		String[] typesArray = types.split(",");
		List<String> typesList = Lists.newArrayList(typesArray);
		List<ArticleType> articleTypes = Lists.transform(typesList, new Function<String, ArticleType>() {
			public ArticleType apply(String typeString) {
				return ArticleType.valueOf(typeString.trim().toUpperCase());
			}
		});
		return articleTypes;
	}
}
