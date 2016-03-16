package gr.media24.mSites.core.controllers; 

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import gr.media24.mSites.core.Settings;
import gr.media24.mSites.core.utils.EmailValidator;
import gr.media24.mSites.core.utils.UserMessage;

import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Controller For Contact Form. Make Sure That The Request Mapping Patterns Are More Specific Than Everything Else
 * Defined In The WebController Class. This Controller Also Removes Blank Characters From Email Input And Sends User's Email.
 * @author asoule
 */
@Controller
@RequestMapping("/contact")
public class ContactController {
	
	private static final Logger logger = Logger.getLogger(ContactController.class);
		
	@Autowired private JavaMailSender mailSender;
	@Autowired private Settings settings;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "name", "email", "departmentsEmail", "text", "subject", "telephone", "address" });
	}

	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String emailForm(Model model){
		model.addAttribute("userMessage",new UserMessage());
		return "/contact/contactForm";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String emailSubmit(Model model, @Valid @ModelAttribute("userMessage") UserMessage userMessage, BindingResult result, HttpServletRequest request){
		EmailValidator emailValidator = new EmailValidator();
		/* Remove blank spaces from email string */
		userMessage.setEmail(userMessage.getEmail().trim());
		boolean x = emailValidator.validate(userMessage.getEmail());
		boolean y = result.hasErrors();
		if(y) {
			if(!x) {
				model.addAttribute("emailValidMessage","Λάθος email. ");
			}
			return "/contact/contactForm";
		}
		if(!x) {
			model.addAttribute("emailValidMessage","Λάθος email. ");
			return "/contact/contactForm";
		}
		model.addAttribute("userMessage",userMessage);
		model.addAttribute("emailTo",userMessage.getEmailTo(settings.getDefaultPublicationName()));
		SimpleMailMessage email= new SimpleMailMessage();
		email.setFrom(userMessage.getEmail());
		email.setTo(userMessage.getEmailTo(settings.getDefaultPublicationName()));
		email.setSubject(userMessage.getSubject());
		email.setText(userMessage.getText()+"\n\n" +
				"'Ονομα "+userMessage.getName()+"\n" +
				"Διεύθυνση "+userMessage.getAddress()+"\n" +
				"Τηλ "+userMessage.getTelephone()+"\n\n\n" +
				""+request.getLocalAddr()+"\n" +
				""+request.getRequestURL()+"\n" +
				""+request.getHeader("X-Forwarded-For")+"\n" +
				""+request.getRemoteAddr()+"\n" +
				""+request.getHeader("User-Agent"));
		mailSender.send(email);
		return "/contact/result";
	}

	@RequestMapping(value="/sendEmail")
	   public String sendEmail( @RequestParam(value = "articleTitle", required = true) String articleTitle,
	           					@RequestParam(value = "articleUrl", required = true) String articleUrl,
	           					@RequestParam(value = "emailTo", required = true) String emailTo,
	           					@RequestParam(value = "emailFrom", required = true) String emailFrom,
	           					HttpServletRequest request, HttpServletResponse response) throws IOException {
	       request.setCharacterEncoding("UTF-8");
	       response.setCharacterEncoding("UTF-8");
	       StringBuilder builder = new StringBuilder();
	       String serverName = request.getServerName();
	       builder.append("Ο ").append(emailFrom).append(" σου πρότεινε το παρακάτω link ");
	       builder.append(articleUrl);
	       String subject = serverName + " [" + articleTitle + "]";
	       String result = this.sendEmail(emailFrom, emailTo, "cp+mcontra@24media.gr", subject, builder.toString());
	       Gson gson = new GsonBuilder().create();
	       response.getWriter().print(gson.toJson(result));
	       return null;
	   }
	   
	   private String sendEmail(String emailFrom, String emailTo, String bcc, String subject, String message) {
		   EmailValidator emailValidator = new EmailValidator();
		   if(!emailValidator.validate(emailFrom) || !emailValidator.validate(emailTo)) { //With Frontend Email Validation In Place You Won't Get Here
			   return "Τα emails που δόθηκαν δεν έγιναν αποδεκτά";
		   }
		   else {
			   SimpleEmail email = new SimpleEmail();
		       try {
		    	   email.setSocketConnectionTimeout(3000);
		           email.setSocketTimeout(3000);
		           email.setCharset("UTF-8");
		           email.setHostName("localhost");
		           email.addTo(emailTo);
		           email.setFrom(emailFrom);
		           email.addBcc(bcc);
		           email.setSubject(subject);
		           email.setMsg(message);
		           email.send();
		           return "To email εστάλη";
		       }
		       catch(Exception exception) {
		           logger.error("email error", exception);
		           return "Τεχνικό πρόβλημα";
		       }
		   }
	   }
}