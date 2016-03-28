package gr.soule.form.data.enums;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * Article's Type Enumeration
 * @author nk, tk, npapadopoulos
 */
public enum FormType {
	
	VIDEO, PICTURE, STORY, PHOTOSTORY, NEWSPAPER, ADVERTORIAL;

	public static List<FormType> getListOf(String types) {
		String[] typesArray = types.split(",");
		List<String> typesList = Lists.newArrayList(typesArray);
		List<FormType> articleTypes = Lists.transform(typesList, new Function<String, FormType>() {
			public FormType apply(String typeString) {
				return FormType.valueOf(typeString.trim().toUpperCase());
			}
		});
		return articleTypes;
	}
}
