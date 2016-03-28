package gr.soule.form.core;

import java.util.List;
import java.util.Map;

/**
 * @author npapadopoulos
 */
public class Settings {

	private String defaultPublicationId;
	private String defaultPublicationName;
	private String articlesToReadDynamically;
	private Map<String, String> newspapersMap;
	private List<String> dailyNewspapers;
	private Map<String, String> reservedArticlesMap;
	private Map<String, String> inlineAdvertisements;
	
	public String getDefaultPublicationId() {
		return defaultPublicationId;
	}
	
	public void setDefaultPublicationId(String defaultPublicationId) {
		this.defaultPublicationId = defaultPublicationId;
	}
	
	public String getDefaultPublicationName() {
		return defaultPublicationName;
	}
	
	public void setDefaultPublicationName(String defaultPublicationName) {
		this.defaultPublicationName = defaultPublicationName;
	}
	
	public String getArticlesToReadDynamically() {
		return articlesToReadDynamically;
	}
	
	public void setArticlesToReadDynamically(String articlesToReadDynamically) {
		this.articlesToReadDynamically = articlesToReadDynamically;
	}
	
	public Map<String, String> getNewspapersMap() {
		return newspapersMap;
	}

	public void setNewspapersMap(Map<String, String> newspapersMap) {
		this.newspapersMap = newspapersMap;
	}
	
	public List<String> getDailyNewspapers() {
		return dailyNewspapers;
	}
	
	public void setDailyNewspapers(List<String> dailyNewspapers) {
		this.dailyNewspapers = dailyNewspapers;
	}
	
	public Map<String, String> getReservedArticlesMap() {
		return reservedArticlesMap;
	}

	public void setReservedArticlesMap(Map<String, String> reservedArticlesMap) {
		this.reservedArticlesMap = reservedArticlesMap;
	}
	
	public Map<String, String> getInlineAdvertisements() {
		return inlineAdvertisements;
	}
	
	public void setInlineAdvertisements(Map<String, String> inlineAdvertisements) {
		this.inlineAdvertisements = inlineAdvertisements;
	}
}