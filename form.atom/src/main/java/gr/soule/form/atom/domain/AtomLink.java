package gr.media24.mSites.atom.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author npapadopoulos
 */
public class AtomLink {

	private String rel;
	private String type;
	private String href;
	private String title;
	private String comment;
	private String credits;
	private String caption;
	private String code;
	
	public String getRel() {
		return rel;
	}
	
	public void setRel(String rel) {
		this.rel = rel;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("rel", rel)
			.append("type", type)
			.append("href", href)
			.append("title", title)
			.append("comment", comment)
			.append("credits", credits)
			.append("caption", caption)
			.append("code", code)
			.toString();
	}
}