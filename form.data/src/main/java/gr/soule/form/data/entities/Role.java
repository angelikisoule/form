package gr.media24.mSites.data.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
 * Role Bean
 * @author npapadopoulos
 */
@NamedQuery(
		name = "getRoleByName",
		query = "FROM Role WHERE name=:name")
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "LONG_TERM")
public class Role extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Length(min = 5, max = 15)
	@Column(name = "name", unique = true)
	private String name;
	
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "LONG_TERM")
	private List<User> users;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * Add User To Role's Users
	 * @param user User Object
	 */
	public void addUser(User user) {
		if(!users.contains(user)) {
			this.users.add(user);
		}
	}
	
	/**
	 * Add List Of Users To Role's Users
	 * @param users List Of Users
	 */
	public void addUser(List<User> users) {
		for(User user : users) addUser(user);
	}
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Role Object
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
		Role rhs = (Role) obj;
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
        return new HashCodeBuilder(13, 17) //Two Randomly Chosen Prime Numbers
            .append(name)
            .toHashCode();
    }
}
