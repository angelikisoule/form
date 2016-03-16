package gr.media24.mSites.data.enums;

/**
 * Reserved Categories Names Are Related To Specific .jsp View Files
 * @author npapadopoulos
 */
public enum ReservedCategoryName {

	HOMEPAGE, SEARCH, LATEST, NEWSPAPERS, NEWSLETTER, MAILCHIMP, GALLERIES, SHOPPING_LIST, VIDEOS ,PRODNEWSLETTER;
	
	/**
	 * Check If Category Name Is Reserved
	 * @param name Category Name
	 * @return TRUE If It Is Reserved, or FALSE
	 */
	public static boolean isReserved(String name) {
		for(ReservedCategoryName r : ReservedCategoryName.values()) {
			if(r.toString().equals(name.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
}