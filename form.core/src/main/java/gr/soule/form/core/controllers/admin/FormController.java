package gr.soule.form.core.controllers.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import gr.soule.form.core.service.FormService;
import gr.soule.form.core.utils.Pager;
import gr.soule.form.data.entities.Form;
import gr.soule.form.data.entities.User;
import gr.soule.form.data.entities.UserForm;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Form's Controller
 * @author asoule
 */
@Controller
@RequestMapping("/admin/form")
public class FormController {

	@Autowired private FormService formService;

	@Value("${alert.article.archive.success}")
	private String ARCHIVE_SUCCESS;
	@Value("${alert.user.update.success}")
	private String UPDATE_SUCCESS;
	@Value("${alert.user.delete.success}")
	private String DELETE_SUCCESS;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "text" });
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countForms = formService.count();
		Pager pager = new Pager(request.getRequestURI(), countForms, Integer.valueOf(pagerPage).longValue(), pagerSize);
		model.addAttribute("countForms", countForms);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("articles", formService.get(pagerSize, pagerOffset));
		model.addAttribute("pager", pager);
		return "admin/form/list";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("form", new Form());
		return "admin/form/form";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("form", formService.getById(id));
		return "admin/user/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("form") @Valid Form form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		formService.saveForm(form, result);
		if(result.hasErrors()) {
			model.addAttribute("form", form);
			return "admin/form/form";
		}
		else {
			redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
			return "redirect:/admin/form";
		}
	}
	
	@RequestMapping(value = "archive/{id}")
	public String archive(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		formService.archiveForm(id);
		redirectAttributes.addFlashAttribute("successMessage", ARCHIVE_SUCCESS);
		return "redirect:/admin/article";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		formService.deleteForm(id);
		redirectAttributes.addFlashAttribute("successMessage", DELETE_SUCCESS);
		return "redirect:/admin/form";
	}
	
}
