package gr.media24.mSites.data.entities;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Adapter To Make User Act As A UserDetails
 * @author npapadopoulos
 */
@SuppressWarnings("deprecation")
public class UserDetailsAdapter implements UserDetails {

	private static final long serialVersionUID = 1L;

	private User user;
	private String password;

	public UserDetailsAdapter(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	public Long getId() {
		return user.getId();
	}
	
	public String getUsername() {
		return user.getUsername();
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public boolean isEnabled() {
		return user.isEnabled();
	}
	
	public Role getRole() {
		return user.getRole();
	}
	
	public Calendar getDateUpdated() {
		return user.getDateUpdated();
	}
	
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl(user.getRole().getName()));
		return authorities;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}
}
