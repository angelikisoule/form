package gr.media24.mSites.data.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

/**
 * Picture Bean
 * @author nk, npapadopoulos
 */
@Entity
@Table(name = "article")
@DiscriminatorValue(value = "PICTURE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Picture extends Article {

	private static final long serialVersionUID = 1L;

	@Length(max = 255)
	@Column(name = "credits")
	private String credits;
	
	@Length(max = 255)
	@Column(name = "caption")
	private String caption;
	
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
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Picture Object
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append(super.toString())
			.append("credits", credits)
			.append("caption", caption)
			.toString();
	}
	
	/**
	 * Override The Default equals() Method
	 * @return TRUE If It's Equal, Else FALSE
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null) { return false; }
		if(obj==this) { return true; }
		if(obj.getClass()!=getClass()) {
			return false;
		}
		Picture rhs = (Picture) obj;
		return new EqualsBuilder()
			.appendSuper(super.equals(obj))
			.append(credits, rhs.credits)
			.append(caption, rhs.caption)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(677, 683) //Two Randomly Chosen Prime Numbers
            .appendSuper(super.hashCode())
            .append(credits)
            .append(caption)
            .toHashCode();
    }
}
