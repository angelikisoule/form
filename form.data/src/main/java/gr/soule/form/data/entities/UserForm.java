package gr.media24.mSites.data.entities;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.ScriptAssert;

/**
 * UserForm Bean
 * @author npapadopoulos
 */
@ScriptAssert(
		lang = "javascript",
		script = "_this.confirmPassword.equals(_this.password)",
		message = "user.password.mismatch.message"
		)
public class UserForm {

	/*
	 * No Need For Validation. It Will Be Used Only As A Hidden Form Field
	 */
	private Long id;
	
	@NotNull
	@Length(min = 3, max = 45)
	private String username;

	/*
	 * Passwords Are BCrypt Encoded (60 Characters Long By Default)
	 * But You Can Limit The Actual Input's Length To Whatever You Like
	 */
	@NotNull
	@Length(min = 3, max = 45)
	private String password;
	
	@NotNull
	@Length(min = 3, max = 45)
	private String confirmPassword;
	
	@Email
	@NotNull
	@Length(min = 3, max = 100)
	private String email;
	
	@NotNull
	private Role role;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of UserForm Object
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("username", username)
				.append("email", email)
				.append("role", role)
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
		UserForm rhs = (UserForm) obj;
		return new EqualsBuilder()
			.append(username, rhs.username)
			.append(email, rhs.email)
			.append(role, rhs.role)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(37, 41) //Two Randomly Chosen Prime Numbers
            .append(username)
            .append(email)
            .append(role)
            .toHashCode();
    }
}
