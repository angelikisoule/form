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
public class AtomEntry {

	private String type;
	private String title;
	private String teaserTitle;
	private String supertitle;
	private String teaserSupertitle;
	private String alternate;
	private List<AtomLink> links = new ArrayList<AtomLink>();
	private List<AtomCategory> categories = new ArrayList<AtomCategory>();
	private List<AtomTag> tags = new ArrayList<AtomTag>();
	private List<String> authors = new ArrayList<String>();
	private String id;
	private Calendar updated;
	private Calendar published;
	private String summary;
	private String teaserSummary;
	private String content;
	private String credits;
	private String caption;
	private String price;
	private String link;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTeaserTitle() {
		return teaserTitle;
	}
	
	public void setTeaserTitle(String teaserTitle) {
		this.teaserTitle = teaserTitle;
	}
	
	public String getSupertitle() {
		return supertitle;
	}
	
	public void setSupertitle(String supertitle) {
		this.supertitle = supertitle;
	}
	
	public String getTeaserSupertitle() {
		return teaserSupertitle;
	}
	
	public void setTeaserSupertitle(String teaserSupertitle) {
		this.teaserSupertitle = teaserSupertitle;
	}
	
	public String getAlternate() {
		return alternate;
	}
	
	public void setAlternate(String alternate) {
		this.alternate = alternate;
	}
	
	public List<AtomLink> getLinks() {
		return links;
	}
	
	public void setLinks(List<AtomLink> links) {
		this.links = links;
	}
	
	public List<AtomCategory> getCategories() {
		return categories;
	}
	
	public void setCategories(List<AtomCategory> categories) {
		this.categories = categories;
	}
	
	public List<AtomTag> getTags() {
		return tags;
	}
	
	public void setTags(List<AtomTag> tags) {
		this.tags = tags;
	}
	
	public List<String> getAuthors() {
		return authors;
	}
	
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Calendar getUpdated() {
		return updated;
	}
	
	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}
	
	public Calendar getPublished() {
		return published;
	}
	
	public void setPublished(Calendar published) {
		this.published = published;
	}
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getTeaserSummary() {
		return teaserSummary;
	}
	
	public void setTeaserSummary(String teaserSummary) {
		this.teaserSummary = teaserSummary;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCredits() {
		return credits;
	}
	
	public void setCredits(String credits) {
		this.credits = credits;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	/**
	 * Add Category To Entry's Current Categories 
	 * @param category Category
	 */
    public void addCategory(AtomCategory category) {
        if(!categories.contains(category)) {
        	this.categories.add(category);
        }
    }

    /**
     * Add Tag To Entry's Current Tags
     * @param tag Tag
     */
    public void addTag(AtomTag tag) {
    	if(!tags.contains(tag)) {
    		this.tags.add(tag);
    	}
    }
    
	/**
	 * Add Link To Entry's Current Links 
	 * @param link Link
	 */
    public void addLink(AtomLink link) {
        if(!links.contains(link)) {
        	this.links.add(link);
        }
    }

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("type", type)
			.append("title", title)
			.append("teaserTitle", teaserTitle)
			.append("supertitle", supertitle)
			.append("teaserSupertitle", teaserSupertitle)
			.append("alternate", alternate)
			.append("id", id)
			.append("updated", DateFormatUtils.format(updated, "yyyy-MM-dd HH:mm:SS"))
			.append("published", DateFormatUtils.format(published, "yyyy-MM-dd HH:mm:SS"))
			.append("summary", summary)
			.append("teaserSummary", teaserSummary)
			.append("content", content)
			.append("credits", credits)
			.append("caption", caption)
			.append("price", price)
			.append("link", link)
			.toString();
	}
}