package gr.soule.form.core.controllers.admin;

import gr.media24.mSites.core.service.PublicationService;
import gr.media24.mSites.data.entities.Publication;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * Publications's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/publication")
public class PublicationController {

	@Autowired private PublicationService publicationService;
	
	@Value("${alert.publication.update.success}")
	private String UPDATE_SUCCESS;
	@Value("${alert.publication.delete.success}")
	private String DELETE_SUCCESS;
	@Value("${alert.publication.delete.error}")
	private String DELETE_ERROR;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "name" });
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		populateForm(model, new Publication());
		return "admin/publication/form";
	}	
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, publicationService.getPublication(id));
		return "admin/publication/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("publication") @Valid Publication publication, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		publicationService.savePublication(publication, result);
		if(result.hasErrors()) {
			populateForm(model, publication);
			return "admin/publication/form";
		}
		else {
			redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
			return "redirect:/admin/publication";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("publications", publicationService.getPublications());
		return "admin/publication/list";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			publicationService.deletePublication(id);
			redirectAttributes.addFlashAttribute("successMessage", DELETE_SUCCESS);
		}
		catch(Exception exception) { //Constraint Violation Exception Should Be Catched Outside The Transaction Boundary
			redirectAttributes.addFlashAttribute("errorMessage", DELETE_ERROR);
		}
		return "redirect:/admin/publication";
	}
	
	/**
	 * Populate Publication Form
	 * @param model Model
	 * @param publication Publication Object
	 */
	void populateForm(Model model, Publication publication) {
		model.addAttribute("publication", publication);
	}
}
