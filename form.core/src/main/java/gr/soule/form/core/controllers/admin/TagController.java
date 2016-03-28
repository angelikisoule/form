package gr.soule.form.core.controllers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import gr.media24.mSites.core.service.PublicationService;
import gr.media24.mSites.core.service.TagService;
import gr.media24.mSites.core.tags.ArticleUrlTag;
import gr.media24.mSites.core.utils.Pager;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Tag;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Tag's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/tag")
@PropertySource(value = "classpath:messages/alerts.properties")
public class TagController {

	@Autowired private TagService tagService;
	@Autowired private PublicationService publicationService;
	
	@Value("${alert.tag.update.success}")
	private String UPDATE_SUCCESS;
	@Value("${alert.tag.delete.success}")
	private String DELETE_SUCCESS;
	@Value("${alert.tag.delete.error}")
	private String DELETE_ERROR;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "name", "displayName", "publication" });
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		populateForm(model, new Tag());
		return "admin/tag/form";
	}	
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, tagService.getTag(id));
		return "admin/tag/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("tag") @Valid Tag tag, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		tagService.saveTag(tag, result);
		if(result.hasErrors()) {
			populateForm(model, tag);
			return "admin/tag/form";
		}
		else {
			redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
			return "redirect:/admin/tag";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countTags = tagService.countTags();
		Pager pager = new Pager(request.getRequestURI(), countTags, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countTags", countTags);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("tags", tagService.getTags(pagerSize, pagerOffset));
		model.addAttribute("pager", pager);
		return "admin/tag/list";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			tagService.deleteTag(id);
			redirectAttributes.addFlashAttribute("successMessage", DELETE_SUCCESS);
		}
		catch(Exception exception) { //Constraint Violation Exception Should Be Catched Outside The Transaction Boundary
			redirectAttributes.addFlashAttribute("errorMessage", DELETE_ERROR);
		}
		return "redirect:/admin/tag";
	}
	
	/**
	 * Get Tag Articles Through An AJAX Call
	 * @param id Tag's Id
	 * @return Map Of Article Titles And Article Mobile URLs
	 */
	@RequestMapping(value = "articles/{id}")
	@ResponseBody
	public Map<String, String> articles(@PathVariable("id") Long id) {
		Map<String, String> result = new HashMap<String, String>();
		List<Article> articles = tagService.getTagArticles(id);
		if(articles!=null) { //The Service May Return null
			for(Article article : articles) {
				result.put(article.getEceArticleId() + ": " + article.getTitle(), ArticleUrlTag.getArticleUrl(article));
			}
		}
		return result;
	}
	
	/**
	 * Populate Tag Form
	 * @param model Model
	 * @param tag Tag Object
	 */
	void populateForm(Model model, Tag tag) {
		model.addAttribute("tag", tag);
		model.addAttribute("selectPublication", publicationService.getPublications());
	}
}