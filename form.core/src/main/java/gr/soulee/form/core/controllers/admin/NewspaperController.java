package gr.media24.mSites.core.controllers.admin;

import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.core.service.NewspaperService;
import gr.media24.mSites.core.utils.Pager;
import gr.media24.mSites.data.entities.Newspaper;
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
 * Newspaper's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/newspaper")
public class NewspaperController {

	@Autowired private ArticleService articleService;
	@Autowired private NewspaperService newspaperService;
	
	@Value("${alert.newspaper.update.success}")
	private String UPDATE_SUCCESS;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "eceArticleId", "articleType", "articleState", "title", "alternate", "link", "dateLastUpdated", "datePublished" });
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true)); //Convert Empty String Values To NULL
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, newspaperService.getNewspaper(id));
		return "admin/newspaper/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("newspaper") @Valid Newspaper newspaper, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			populateForm(model, newspaper);
			return "admin/newspaper/form";
		}
		newspaperService.mergeNewspaper(newspaper);
		redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
		return "redirect:/admin/newspaper/update/" + newspaper.getId();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countArticles = articleService.countArticlesByArticleType(ArticleType.NEWSPAPER);
		Pager pager = new Pager(request.getRequestURI(), countArticles, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countArticles", countArticles);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("articles", articleService.getArticlesByArticleType(ArticleType.NEWSPAPER, pagerSize, pagerOffset, null));
		model.addAttribute("pager", pager);
		return "admin/article/list";
	}
	
	/**
	 * Populate Newspaper Form
	 * @param model Model
	 * @param newspaper Newspaper Object
	 */
	void populateForm(Model model, Newspaper newspaper) {
		model.addAttribute("newspaper", newspaper);
		Newspaper existing = newspaperService.getNewspaper(newspaper.getId()); //Categories, Authors And RelatedArticles Are Not Given As Input In The Form
		model.addAttribute("categories", existing.getCategories());
		model.addAttribute("authors", existing.getAuthors());
	}
}
