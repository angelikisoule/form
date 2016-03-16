package gr.media24.mSites.core.controllers.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import gr.media24.mSites.core.service.PublicationService;
import gr.media24.mSites.core.service.SectionService;
import gr.media24.mSites.core.tags.ArticleUrlTag;
import gr.media24.mSites.core.utils.Pager;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.Section;

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
 * Section's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/section")
@PropertySource(value = "classpath:messages/alerts.properties")
public class SectionController {

	@Autowired private SectionService sectionService;
	@Autowired private PublicationService publicationService;
	
	@Value("${alert.section.update.success}")
	private String UPDATE_SUCCESS;
	@Value("${alert.section.delete.success}")
	private String DELETE_SUCCESS;
	@Value("${alert.section.delete.error}")
	private String DELETE_ERROR;
	@Value("${alert.section.deleteArticle.success}")
	private String DELETE_ARTICLE_SUCCESS;
	@Value("${alert.section.deleteArticle.error}")
	private String DELETE_ARTICLE_ERROR;

	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "name", "publication" });
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		populateForm(model, new Section());
		return "admin/section/form";
	}	
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, sectionService.getSection(id));
		return "admin/section/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("section") @Valid Section section, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		sectionService.saveSection(section, result);
		if(result.hasErrors()) {
			populateForm(model, section);
			return "admin/section/form";
		}
		else {
			redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
			return "redirect:/admin/section";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countSections = sectionService.countSections();
		Pager pager = new Pager(request.getRequestURI(), countSections, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countSections", countSections);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("sections", sectionService.getSections(pagerSize, pagerOffset));
		model.addAttribute("pager", pager);
		return "admin/section/list";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		/*
		 * Since Section Is The Owner Side Of The Section/Article Relationship We Have To Manually Check For Existing Section 
		 * Articles Before Trying To Delete. Otherwise Hibernate Will Propagate The Deletion To The Join Table Records Too
		 */
		if(sectionService.countSectionArticles(id)==0) {
			sectionService.deleteSection(id);
			redirectAttributes.addFlashAttribute("successMessage", DELETE_SUCCESS);
		}
		else {
			redirectAttributes.addFlashAttribute("errorMessage", DELETE_ERROR);
		}
		return "redirect:/admin/section";
	}

	@RequestMapping(value = "deleteArticle", method = RequestMethod.GET)
	public String deleteArticle(@RequestParam Map<String,String> params, RedirectAttributes redirectAttributes) {
		try {
			sectionService.deleteSectionArticle(Long.parseLong(params.get("sectionId")), params.get("eceArticleId"));
			redirectAttributes.addFlashAttribute("successMessage", DELETE_ARTICLE_SUCCESS);
		}
		catch(Exception exception) {
			redirectAttributes.addFlashAttribute("errorMessage", DELETE_ARTICLE_ERROR);
		}
		return "redirect:/admin/section";
	}

	/**
	 * Get Section Articles Through An AJAX Call
	 * @param id Section's Id
	 * @return Map Of Article Titles And Article Mobile URLs
	 */
	@RequestMapping(value = "articles/{id}")
	@ResponseBody
	public Map<String, String> articles(@PathVariable("id") Long id) {
		Map<String, String> result = new HashMap<String, String>();
		Section section = sectionService.getSection(id);
		for(Article article : section.getArticles()) {
			result.put(article.getEceArticleId() + ": " + article.getTitle(), ArticleUrlTag.getArticleUrl(article));
		}
		return result;
	}
	
	/**
	 * Populate Section Form
	 * @param model Model
	 * @param section Section Object
	 */
	void populateForm(Model model, Section section) {
		model.addAttribute("section", section);
		model.addAttribute("selectPublication", publicationService.getPublications());
	}
}
