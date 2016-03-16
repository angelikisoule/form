package gr.media24.mSites.data.entities;

import java.io.Serializable;

import javax.persistence.Column;
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

@NamedQueries({
	@NamedQuery(
			name = "getByNullGroupName",
			query = "FROM Category WHERE groupName IS NULL ORDER BY name ASC"),
	@NamedQuery(
			name = "getCategoryBySectionUniqueNameGroupNamePublicationName",
			query = "FROM Category WHERE sectionUniqueName=:sectionUniqueName AND groupName=:groupName AND publication.name=:publicationName"),
	@NamedQuery(
			name = "getCategoryBySectionUniqueNamePublicationName",
			query = "FROM Category WHERE sectionUniqueName=:sectionUniqueName AND groupName IS NULL AND publication.name=:publicationName"),
	@NamedQuery(
			name = "getCategoryBySectionUniqueNameLikePublicationName",
			query = "FROM Category WHERE sectionUniqueName LIKE :sectionUniqueName AND groupName IS NULL AND publication.name=:publicationName ORDER BY name ASC")			
})
@Entity
@Table(name = "category", uniqueConstraints = { @UniqueConstraint(columnNames = {"sectionUniqueName", "groupName", "publication" })})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Category extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/*
	 * There Are Some Really Short Names Like 'A2'
	 */
	@NotNull
	@Length(min = 2, max = 45)
	@Column(name = "name")
	private String name;

	/*
	 * There Are Some Really Short Names Like 'A2'
	 */
	@NotNull
	@Length(min = 2, max = 45)
	@Column(name = "sectionUniqueName")
	private String sectionUniqueName;
	
	@Length(max = 45)
	@Column(name = "groupName")
	private String groupName;
	
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
	
	public String getSectionUniqueName() {
		return sectionUniqueName;
	}
	
	public void setSectionUniqueName(String sectionUniqueName) {
		this.sectionUniqueName = sectionUniqueName;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public Publication getPublication() {
		return publication;
	}
	
	public void setPublication(Publication publication) {
		this.publication = publication;
	}
	
	public String getDisplayName() {
		return getName() + "." + getSectionUniqueName() + "." + getGroupName();
	}
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Category Object
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("name", name)
			.append("sectionUniqueName", sectionUniqueName)
			.append("groupName", groupName)
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
		Category rhs = (Category) obj;
		return new EqualsBuilder()
			.append(name, rhs.name)
			.append(sectionUniqueName, rhs.sectionUniqueName)
			.append(groupName, rhs.groupName)
			.append(publication, rhs.publication)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(7, 11) //Two Randomly Chosen Prime Numbers
            .append(name)
            .append(sectionUniqueName)
            .append(groupName)
            .append(publication)
            .toHashCode();
    }
}
