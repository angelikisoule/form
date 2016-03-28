package gr.soule.form.core.controllers.admin;

import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.core.service.AuthorService;
import gr.media24.mSites.core.service.CategoryService;
import gr.media24.mSites.core.service.PublicationService;
import gr.media24.mSites.core.service.SectionService;
import gr.media24.mSites.core.service.TagService;
import gr.media24.mSites.data.entities.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Main Controller For Admin. Make Sure That The Request Mapping Patterns Are More Specific Than Everything 
 * Else Defined In The WebController Class, Otherwise You Would Not Have An Option To Access The Admin Pages
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired private ArticleService articleService;
	@Autowired private AuthorService authorService;
	@Autowired private PublicationService publicationService;
	@Autowired private CategoryService categoryService;
	@Autowired private SectionService sectionService;
	@Autowired private TagService tagService;
	
	@Value("${alert.article.forced.success}")
	private String FORCED_SUCCESS;
	@Value("${alert.article.forced.error}")
	private String FORCED_ERROR;

	@RequestMapping(method = RequestMethod.GET)
	public String main(Model model) {
		model.addAttribute("countArticles", articleService.countArticles());
		model.addAttribute("countAuthors", authorService.countAuthors());
		model.addAttribute("countPublications", publicationService.countPublications());
		model.addAttribute("countCategories", categoryService.countCategories());
		model.addAttribute("countSections", sectionService.countSections());
		model.addAttribute("countTags", tagService.countTags());
		return "admin/home";
	}

	/**
	 * Forced Update Of An Item Without Taking Into Account It's dateLastUpdated Value
	 * @param eceArticleId Item's eceArticleId
	 * @return Item's Editing View
	 */
	@RequestMapping(value = "forced/{eceArticleId}", method = RequestMethod.GET)
	public String forced(@PathVariable("eceArticleId") String eceArticleId, RedirectAttributes redirectAttributes) {
		Article article = articleService.forcedUpdate(eceArticleId);
		if(article!=null) { //Article Updated
			redirectAttributes.addFlashAttribute("successMessage", FORCED_SUCCESS);
			return "redirect:/admin/" + article.getArticleType().toString().toLowerCase() + "/update/" + article.getId();			
		}
		else { //An Exception Occurred
			redirectAttributes.addFlashAttribute("errorMessage", FORCED_ERROR);
			return "redirect:/admin/article";
		}
	}
}