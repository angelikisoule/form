package gr.media24.mSites.data.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

/**
 * Tag Bean
 * @author npapadopoulos
 */
@NamedQueries({
	@NamedQuery(
			name = "getTagByNamePublicationName",
			query = "FROM Tag WHERE name=:name AND publication.name=:publicationName"),
	@NamedQuery(
			name = "getTagByNameDisplayNamePublicationName",
			query = "FROM Tag WHERE name=:name AND displayName=:displayName AND publication.name=:publicationName")
})
@Entity
@Table(name = "tag", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "displayName", "publication" })})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Tag extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Length(min = 2, max = 100)
	private String name;
	
	@NotNull
	@Length(min = 2, max = 100)
	private String displayName;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "publication", referencedColumnName = "id", nullable = false)
	private Publication publication;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}	
	
	public Publication getPublication() {
		return publication;
	}
	
	public void setPublication(Publication publication) {
		this.publication = publication;
	}
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Tag Object
	 */
    @Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("name", name)
			.append("displayName", displayName)
			.append("publication", publication)
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
		Tag rhs = (Tag) obj;
		return new EqualsBuilder()
			.append(name, rhs.name)
			.append(displayName, rhs.displayName)
			.append(publication, rhs.publication)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(647, 191) //Two Randomly Chosen Prime Numbers
            .append(name)
            .append(displayName)
            .append(publication)
            .toHashCode();
    }
}
