package gr.media24.mSites.data.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Story Bean
 * @author nk, npapadopoulos
 */
@Entity
@Table(name = "article")
@DiscriminatorValue(value = "STORY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Story extends Article implements Serializable {

	private static final long serialVersionUID = 1L;

	@Lob
	@Column(name = "body")
	private String body;
	
	/*
	 * Not Persisted With The Updater But It Might Be Useful If 
	 * We Want To Add Embed Codes Just For The mSites Articles
	 */
	@Lob
	@Column(name = "embeddedCode")
	private String embeddedCode;
	
    public String getBody() {
    	return body;
    }

    public void setBody(String body) {
    	this.body = body;
    }	
	
    public String getEmbeddedCode() {
    	return embeddedCode;
    }
    
    public void setEmbeddedCode(String embeddedCode) {
    	this.embeddedCode = embeddedCode;
    }

	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Story Object
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append(super.toString())
			.append("body", body)
			.append("embeddedCode", embeddedCode)
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
		Story rhs = (Story) obj;
		return new EqualsBuilder()
			.appendSuper(super.equals(obj))
			.append(body, rhs.body)
			.append(embeddedCode, rhs.embeddedCode)
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
            .append(body)
            .append(embeddedCode)
            .toHashCode();
    }
}
