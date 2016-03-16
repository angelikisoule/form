package gr.media24.mSites.core.controllers.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.core.utils.Pager;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.enums.ArticleState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Article's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleController {

	@Autowired private ArticleService articleService;

	@Value("${alert.article.archive.success}")
	private String ARCHIVE_SUCCESS;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countArticles = articleService.countArticles();
		Pager pager = new Pager(request.getRequestURI(), countArticles, Integer.valueOf(pagerPage).longValue(), pagerSize);
		model.addAttribute("countArticles", countArticles);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("articles", articleService.getArticles(pagerSize, pagerOffset));
		model.addAttribute("pager", pager);
		return "admin/article/list";
	}
	
	@RequestMapping(value="archived")
	public String archived(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countArticles = articleService.countArticlesByArticleState(ArticleState.ARCHIVED);
		Pager pager = new Pager(request.getRequestURI(), countArticles, Integer.valueOf(pagerPage).longValue(), pagerSize);
		model.addAttribute("countArticles", countArticles);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("articles", articleService.getArticlesByArticleState(ArticleState.ARCHIVED, pagerSize, pagerOffset));
		model.addAttribute("pager", pager);
		return "admin/article/list";
	}

	@RequestMapping(value = "archive/{id}")
	public String archive(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		articleService.archiveArticle(id);
		redirectAttributes.addFlashAttribute("successMessage", ARCHIVE_SUCCESS);
		return "redirect:/admin/article";
	}
	
	/**
	 * Delete An Article / Author Relationship Through An AJAX Call
	 * @param articleId Article's Id
	 * @param authorId Author's Id
	 * @return A String Indicating If The Deletion Was Successful Or Not
	 */
	@RequestMapping(value = "deleteAuthor", method = RequestMethod.GET)
	public @ResponseBody String deleteAuthor(@RequestParam(value = "articleId", required = true) Long articleId, @RequestParam(value = "authorId", required = true) Long authorId) {
		if(articleService.deleteArticleAuthor(articleId, authorId)) {
			return "success";
		}
		return "error";
	}
	
	/**
	 * Delete An Article / Category Relationship Through An AJAX Call
	 * @param articleId Article's Id
	 * @param categoryId Category's Id
	 */
	@RequestMapping(value = "deleteCategory", method = RequestMethod.GET)
	public @ResponseBody String deleteCategory(@RequestParam(value = "articleId", required = true) Long articleId, @RequestParam(value = "categoryId", required = true) Long categoryId) {
		articleService.deleteArticleCategory(articleId, categoryId);
		return null;
	}
	
	/**
	 * Delete An Article / RelatedArticle Relationship Through An AJAX Call
	 * @param articleId Article's Id
	 * @param relatedArticleId Related Article's Id
	 */
	@RequestMapping(value = "deleteRelatedArticle", method = RequestMethod.GET)
	public @ResponseBody String deleteRelatedArticle(@RequestParam(value = "articleId", required = true) Long articleId, @RequestParam(value = "relatedArticleId", required = true) Long relatedArticleId, @RequestParam(value = "enclosureComment", required = false) String enclosureComment) {
		articleService.deleteArticleRelatedArticle(articleId, relatedArticleId, enclosureComment);
		return null;
	}
	
	/**
	 * Search Articles With eceArticleId Or title Like A Given Term
	 * @param term Search Term
	 * @return List Of String Results For The Autocomplete Form
	 */
	@RequestMapping(value = "/searchArticle", method = RequestMethod.GET)
	@ResponseBody
	public List<String> searchArticle(@RequestParam("term") String term) {
		return articleService.searchArticlesByAttributesLike(term);
	}
	
	/**
	 * Return The View For Editing An Article Given A eceArticleId Selected From The Autocomplete Form 
	 * @param eceArticleId Article's eceArticleId
	 * @return Article's Editing View
	 */
	@RequestMapping(value = "/searchResult/{eceArticleId}", method = RequestMethod.GET)
	public String searchResult(@PathVariable("eceArticleId") String eceArticleId) {
		Article article = articleService.getArticleByEceArticleId(eceArticleId);
		return "redirect:/admin/" + article.getArticleType().toString().toLowerCase() + "/update/" + article.getId();
	}
}
