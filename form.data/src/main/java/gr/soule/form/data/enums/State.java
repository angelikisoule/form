package gr.soule.form.data.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Entities' State Enumeration
 * @author asoule
 */
public enum State {
	
	NEW, ARCHIVED, EDIT;
	
	public static List<State> getListOf() {
		return Arrays.asList(State.values());
	}
}
