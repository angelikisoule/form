package gr.media24.mSites.data.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

/**
 * Publication Bean
 * @author npapadopoulos
 */
@NamedQuery(
		name = "getPublicationByName",
		query = "FROM Publication WHERE name=:name")
@Entity
@Table(name = "publication")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "LONG_TERM")
public class Publication extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Length(min = 5, max = 15)
	@Column(name = "name", unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Publication Object
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("name", name)
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
		Publication rhs = (Publication) obj;
		return new EqualsBuilder()
			.append(name, rhs.name)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(457, 887) //Two Randomly Chosen Prime Numbers
            .append(name)
            .toHashCode();
    }
}
