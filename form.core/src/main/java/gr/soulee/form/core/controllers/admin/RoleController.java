package gr.media24.mSites.core.controllers.admin;

import javax.validation.Valid;

import gr.media24.mSites.core.service.RoleService;
import gr.media24.mSites.data.entities.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Role's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/role")
@PropertySource(value = "classpath:messages/alerts.properties")
public class RoleController {

	@Autowired private RoleService roleService;
	
	@Value("${alert.role.update.success}")
	private String UPDATE_SUCCESS;
	@Value("${alert.role.delete.success}")
	private String DELETE_SUCCESS;
	@Value("${alert.role.delete.error}")
	private String DELETE_ERROR;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "name" });
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		populateForm(model, new Role());
		return "admin/role/form";
	}	
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, roleService.getRole(id));
		return "admin/role/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("role") @Valid Role role, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		roleService.saveRole(role, result);
		if(result.hasErrors()) {
			populateForm(model, role);
			return "admin/role/form";
		}
		else {
			redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
			return "redirect:/admin/role";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("roles", roleService.getRoles());
		return "admin/role/list";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			roleService.deleteRole(id);
			redirectAttributes.addFlashAttribute("successMessage", DELETE_SUCCESS);
		}
		catch(Exception exception) { //Constraint Violation Exception Should Be Catched Outside The Transaction Boundary
			redirectAttributes.addFlashAttribute("errorMessage", DELETE_ERROR);
		}
		return "redirect:/admin/role";
	}
	
	/**
	 * Populate Role Form
	 * @param model Model
	 * @param role Role Object
	 */
	void populateForm(Model model, Role role) {
		model.addAttribute("role", role);
	}
}
