package gr.soule.form.core.controllers.admin;

import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.core.service.PictureService;
import gr.media24.mSites.core.utils.Pager;
import gr.media24.mSites.data.entities.Picture;
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
 * Picture's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/picture")
public class PictureController {

	@Autowired private ArticleService articleService;
	@Autowired private PictureService pictureService;
	
	@Value("${alert.picture.update.success}")
	private String UPDATE_SUCCESS;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "eceArticleId", "articleType", "articleState", "title", "credits", "caption", "alternate", "dateLastUpdated", "datePublished" });
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true)); //Convert Empty String Values To NULL
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, pictureService.getPicture(id));
		return "admin/picture/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("picture") @Valid Picture picture, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			populateForm(model, picture);
			return "admin/picture/form";
		}
		pictureService.mergePicture(picture);
		redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
		return "redirect:/admin/picture/update/" + picture.getId();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countArticles = articleService.countArticlesByArticleType(ArticleType.PICTURE);
		Pager pager = new Pager(request.getRequestURI(), countArticles, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countArticles", countArticles);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("articles", articleService.getArticlesByArticleType(ArticleType.PICTURE, pagerSize, pagerOffset, null));
		model.addAttribute("pager", pager);
		return "admin/article/list";
	}
	
	/**
	 * Populate Picture Form
	 * @param model Model
	 * @param picture Picture Object
	 */
	void populateForm(Model model, Picture picture) {
		model.addAttribute("picture", picture);
		Picture existing = pictureService.getPicture(picture.getId()); //Categories And Authors Are Not Given As Input In The Form
		model.addAttribute("categories", existing.getCategories());
		model.addAttribute("authors", existing.getAuthors());
	}
}