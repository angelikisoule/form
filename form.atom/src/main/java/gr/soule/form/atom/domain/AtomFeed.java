package gr.media24.mSites.atom.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author npapadopoulos
 */
public class AtomFeed {

	private String title;
	private List<AtomLink> links = new ArrayList<AtomLink>();
	private String subtitle;
	private String id;
	private String icon;
	private Calendar updated;
	private List<AtomEntry> entries = new ArrayList<AtomEntry>();
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<AtomLink> getLinks() {
		return links;
	}
	
	public void setLinks(List<AtomLink> links) {
		this.links = links;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Calendar getUpdated() {
		return updated;
	}
	
	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}
	
	public List<AtomEntry> getEntries() {
		return entries;
	}
	
	public void setEntries(List<AtomEntry> entries) {
		this.entries = entries;
	}
	
	/**
	 * Add Entry To Feed's Current Entries
	 * @param entry Entry
	 */
	public void addEntry(AtomEntry entry) {
        this.entries.add(entry);
    }
	
	/**
	 * Add Link To Feed's Current Links
	 * @param link Link
	 */
	public void addLink(AtomLink link) {
		this.links.add(link);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("title", title)
			.append("link", links)
			.append("subtitle", subtitle)
			.append("id", id)
			.append("icon", icon)
			.append("updated", DateFormatUtils.format(updated, "yyyy-MM-dd HH:mm:SS"))
			.toString();
	}
}