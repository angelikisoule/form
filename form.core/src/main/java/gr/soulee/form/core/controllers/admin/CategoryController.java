package gr.media24.mSites.core.controllers.admin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import gr.media24.mSites.core.service.CategoryService;
import gr.media24.mSites.core.service.PublicationService;
import gr.media24.mSites.core.utils.Pager;
import gr.media24.mSites.data.entities.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Category's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/category")
public class CategoryController {

	@Autowired private CategoryService categoryService;
	@Autowired private PublicationService publicationService;
	
	@Value("${alert.category.update.success}")
	private String UPDATE_SUCCESS;
	@Value("${alert.category.delete.success}")
	private String DELETE_SUCCESS;
	@Value("${alert.category.delete.error}")
	private String DELETE_ERROR;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "name", "sectionUniqueName", "groupName", "publication" });
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true)); //Convert Empty String Values To NULL
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		populateForm(model, new Category());
		return "admin/category/form";
	}	
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, categoryService.getCategory(id));
		return "admin/category/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("category") @Valid Category category, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		categoryService.saveCategory(category, result);
		if(result.hasErrors()) {
			populateForm(model, category);
			return "admin/category/form";
		}
		else {
			redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
			return "redirect:/admin/category";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countCategories = categoryService.countCategories();
		Pager pager = new Pager(request.getRequestURI(), countCategories, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countCategories", countCategories);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("categories", categoryService.getCategories(pagerSize, pagerOffset));
		model.addAttribute("pager", pager);
		return "admin/category/list";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			categoryService.deleteCategory(id);
			redirectAttributes.addFlashAttribute("successMessage", DELETE_SUCCESS);
		}
		catch(Exception exception) { //Constraint Violation Exception Should Be Catched Outside The Transaction Boundary
			redirectAttributes.addFlashAttribute("errorMessage", DELETE_ERROR);
		}
		return "redirect:/admin/category";
	}
	
	/**
	 * Populate Category Form
	 * @param model Model
	 * @param category Category Object
	 */
	void populateForm(Model model, Category category) {
		model.addAttribute("category", category);
		model.addAttribute("selectPublication", publicationService.getPublications());
	}
}
