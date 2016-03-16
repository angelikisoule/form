package gr.media24.mSites.core.controllers.admin;

import javax.validation.Valid;

import gr.media24.mSites.core.service.RoleService;
import gr.media24.mSites.core.service.UserService;
import gr.media24.mSites.data.entities.User;
import gr.media24.mSites.data.entities.UserForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/user")
@PropertySource(value = "classpath:messages/alerts.properties")
public class UserController {

	@Autowired private UserService userService;
	@Autowired private RoleService roleService;
	
	@Value("${alert.user.update.success}")
	private String UPDATE_SUCCESS;
	@Value("${alert.user.delete.success}")
	private String DELETE_SUCCESS;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "username", "password", "confirmPassword", "email", "role" });
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		populateForm(model, new UserForm());
		return "admin/user/form";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, toUserForm(userService.getUser(id)));
		return "admin/user/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("user") @Valid UserForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		convertPasswordError(result);
		userService.saveUser(toUser(form), form.getPassword(), result);
		if(result.hasErrors()) {
			populateForm(model, form);
			return "admin/user/form";
		}
		else {
			redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
			return "redirect:/admin/user";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "admin/user/list";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		userService.deleteUser(id);
		redirectAttributes.addFlashAttribute("successMessage", DELETE_SUCCESS);
		return "redirect:/admin/user";
	}
	
	/**
	 * Converts The Global Error That @ScriptAssert Generates Into An Error On The Password Field
	 * @param result BindingResult Errors Of UserForm
	 */
	private static void convertPasswordError(BindingResult result) {
		for(ObjectError error : result.getGlobalErrors()) {
			String message = error.getDefaultMessage();
			if("user.password.mismatch.message".equals(message)) {
				if(!result.hasFieldErrors("password")) {
					result.rejectValue("password", "error.mismatch");
					result.rejectValue("confirmPassword", "error.mismatch");
				}
			}
		}
	}	
	
	/**
	 * Convert UserForm Given To A User Object
	 * @param form UserForm Object
	 * @return User Object
	 */
	private static User toUser(UserForm form) {
		User user = new User();
		user.setId(form.getId());
		user.setUsername(form.getUsername());
		user.setEmail(form.getEmail());
		user.setEnabled(true); //Enabled By Default
		user.setRole(form.getRole());
		return user;
	}
	
	/**
	 * Convert User To A UserForm Object. Don't Set password or confirmPassword Fields
	 * @param user User Object
	 * @return UserForm Object
	 */
	private static UserForm toUserForm(User user) {
		UserForm form = new UserForm();
		form.setId(user.getId());
		form.setUsername(user.getUsername());
		form.setEmail(user.getEmail());
		form.setRole(user.getRole());
		return form;
	}	
	
	/**
	 * Populate User Form
	 * @param model Model
	 * @param userForm UserForm Object
	 */
	void populateForm(Model model, UserForm userForm) {
		model.addAttribute("user", userForm);
		model.addAttribute("selectRole", roleService.getRoles());
	}
}
