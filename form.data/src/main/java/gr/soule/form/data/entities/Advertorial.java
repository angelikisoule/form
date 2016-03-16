package gr.media24.mSites.data.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Advertorial Bean
 * @author npapadopoulos
 */
@Entity
@Table(name = "article")
@DiscriminatorValue(value = "ADVERTORIAL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Advertorial extends Article implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Advertorial Object
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append(super.toString())
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
		return new EqualsBuilder()
			.appendSuper(super.equals(obj))
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(691, 701) //Two Randomly Chosen Prime Numbers
            .appendSuper(super.hashCode())
            .toHashCode();
    }
}
