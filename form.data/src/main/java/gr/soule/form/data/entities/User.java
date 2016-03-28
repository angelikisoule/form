package gr.soule.form.data.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * User Bean
 * @author npapadopoulos
 */
@NamedQueries({
	@NamedQuery(
			name = "getUserByUsername",
			query = "FROM User WHERE username=:username"),
	@NamedQuery(
			name = "getUserByEmail",
			query = "FROM User WHERE email=:email"),
	@NamedQuery(
			name = "getUsersByRoleName",
			query = "FROM User WHERE role.name=:roleName")
})
@Entity
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "LONG_TERM")
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Length(min = 3, max = 45)
	@Column(name = "username", unique = true)
	private String username;

	@Email
	@NotNull
	@Length(min = 3, max = 100)
	@Column(name = "email", unique = true)
	private String email;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
	private Role role;

	@NotNull
	@Column(name = "enabled")
	private boolean enabled = true;

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of User Object
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("username", username)
			.append("email", email)
			.append("role", role)
			.append("enabled", enabled)
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
		User rhs = (User) obj;
		return new EqualsBuilder()
			.append(username, rhs.username)
			.append(email, rhs.email)
			.append(role, rhs.role)
			.append(enabled, rhs.enabled)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(29, 31) //Two Randomly Chosen Prime Numbers
            .append(username)
            .append(email)
            .append(role)
            .append(enabled)
            .toHashCode();
    }	
}
