package gr.media24.mSites.core.controllers.admin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import gr.media24.mSites.core.service.AuthorService;
import gr.media24.mSites.core.utils.Pager;
import gr.media24.mSites.data.entities.Author;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Author's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/author")
@PropertySource(value = "classpath:messages/alerts.properties")
public class AuthorController {

	@Autowired private AuthorService authorService;
	
	@Value("${alert.author.update.success}")
	private String UPDATE_SUCCESS;
	@Value("${alert.author.delete.success}")
	private String DELETE_SUCCESS;
	@Value("${alert.author.delete.error}")
	private String DELETE_ERROR;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "name" });
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		populateForm(model, new Author());
		return "admin/author/form";
	}	
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, authorService.getAuthor(id));
		return "admin/author/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("author") @Valid Author author, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		authorService.saveAuthor(author, result);
		if(result.hasErrors()) {
			populateForm(model, author);
			return "admin/author/form";
		}
		else {
			redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
			return "redirect:/admin/author";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countAuthors = authorService.countAuthors();
		Pager pager = new Pager(request.getRequestURI(), countAuthors, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countAuthors", countAuthors);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("authors", authorService.getAuthors(pagerSize, pagerOffset));
		model.addAttribute("pager", pager);
		return "admin/author/list";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			authorService.deleteAuthor(id);
			redirectAttributes.addFlashAttribute("successMessage", DELETE_SUCCESS);
		}
		catch(Exception exception) { //Constraint Violation Exception Should Be Catched Outside The Transaction Boundary
			redirectAttributes.addFlashAttribute("errorMessage", DELETE_ERROR);
		}
		return "redirect:/admin/author";
	}
	
	/**
	 * Populate Author Form
	 * @param model Model
	 * @param author Author Object
	 */
	void populateForm(Model model, Author author) {
		model.addAttribute("author", author);
	}
}
