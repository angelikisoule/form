package gr.soule.form.core.utils;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Main Class For Contact Form. Contains The Basic Info Of The Email.
 * @author asoule
 */
public class UserMessage {

	@NotNull
	@Length(min = 3, max = 100)
	private String name;
	
	@NotNull
	@Length(min = 3, max = 100)
	private String email;
	
	@NotNull
	private Integer departmentsEmail;
	
	@NotNull
	@Length(min = 3)
	private String text;
	
	@NotNull
	@Length(min = 3, max = 100)
	private String subject;
	
	@NotNull
	@Length(min = 3, max = 100)
	private String telephone;
	
	@NotNull
	@Length(min = 3, max = 100)
	private String address;
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public Integer getDepartmentsEmail() {
		return departmentsEmail;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setDepartmentsEmail(Integer departmentsEmail) {
		this.departmentsEmail = departmentsEmail;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Match The Integer Read From The Email Form As The sendTo Email To The Real Email Address
	 * @param publicationName Publication's Name
	 * @return The Real Email Address The Email Will Be Send To
	 */
	public String getEmailTo(String publicationName) {
		String result = "info@24media.gr"; //Α Default One
		if(publicationName.equals("ladylike")) {
			switch(this.departmentsEmail) {
				case 1:
					result = "info@ladylike.gr";
					break;
				case 2:
					result = "advertise@andstart.gr";
					break;
				case 3:
					result = "webmaster@contra.gr";
					break;
				case 4:
					result = "you@contra.gr";
					break;
				case 5:
					result = "comm@contra.gr";
					break;
				default:
					break;
			}
		}
		else if(publicationName.equals("sport24")) {
			switch(this.departmentsEmail) {
				case 1:
					result = "info@sport24.gr";
					break;
				case 2:
					result = "support@sport24.gr";
					break;
				case 3:
					result = "advertise@andstart.gr";
					break;
				case 4:
					result = "info@sport24.gr";
					break;
				default:
					break;
			}
		}
		else if(publicationName.equals("news247")) {
			switch(this.departmentsEmail) {
				case 1:
					result = "info@news247.gr";
					break;
				default:
					break;
			}
		}
		else if(publicationName.equals("contra")) {
			switch(this.departmentsEmail) {
				case 1:
					result = "info@contra.gr";
					break;
				case 2:
					result = "advertise@andstart.gr";
					break;
				case 3:
					result = "webmaster@contra.gr";
					break;
				case 4:
					result = "comm@contra.gr";
					break;
				default:
					break;
			}
		}
		return result;
	}
}