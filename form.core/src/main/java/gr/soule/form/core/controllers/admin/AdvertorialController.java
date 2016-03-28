package gr.soule.form.core.controllers.admin;

import gr.media24.mSites.core.service.AdvertorialService;
import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.core.utils.Pager;
import gr.media24.mSites.data.entities.Advertorial;
import gr.media24.mSites.data.enums.ArticleType;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
 * Advertorial's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/advertorial")
public class AdvertorialController {

	@Autowired private ArticleService articleService;
	@Autowired private AdvertorialService advertorialService;
	
	@Value("${alert.advertorial.update.success}")
	private String UPDATE_SUCCESS;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "eceArticleId", "articleType", "articleState", "alternate", "title", "supertitle", "leadText", "teaserTitle", "teaserSupertitle", "teaserLeadText", "dateLastUpdated", "datePublished" });
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true)); //Convert Empty String Values To NULL
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, advertorialService.getAdvertorial(id));
		return "admin/advertorial/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("advertorial") @Valid Advertorial advertorial, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			populateForm(model, advertorial);
			return "admin/advertorial/form";
		}
		advertorialService.mergeAdvertorial(advertorial);
		redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
		return "redirect:/admin/advertorial/update/" + advertorial.getId();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countArticles = articleService.countArticlesByArticleType(ArticleType.ADVERTORIAL);
		Pager pager = new Pager(request.getRequestURI(), countArticles, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countArticles", countArticles);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("articles", articleService.getArticlesByArticleType(ArticleType.ADVERTORIAL, pagerSize, pagerOffset, null));
		model.addAttribute("pager", pager);
		return "admin/article/list";
	}
	
	/**
	 * Populate Advertorial Form
	 * @param model Model
	 * @param advertorial Advertorial Object
	 */
	void populateForm(Model model, Advertorial advertorial) {
		model.addAttribute("advertorial", advertorial);
		Advertorial existing = advertorialService.getAdvertorial(advertorial.getId()); //Categories, Authors And RelatedArticles Are Not Given As Input In The Form
		model.addAttribute("categories", existing.getCategories());
		model.addAttribute("authors", existing.getAuthors());
		model.addAttribute("relatedArticles", existing.getRelatedArticles());
	}
}