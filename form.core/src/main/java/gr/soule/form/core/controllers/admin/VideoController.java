package gr.soule.form.core.controllers.admin;

import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.core.service.VideoService;
import gr.media24.mSites.core.utils.Pager;
import gr.media24.mSites.data.entities.Video;
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
 * Video's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/video")
public class VideoController {

	@Autowired private ArticleService articleService;
	@Autowired private VideoService videoService;
	
	@Value("${alert.video.update.success}")
	private String UPDATE_SUCCESS;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "eceArticleId", "articleType", "articleState", "title", "alternate", "supertitle", "leadText", "videoType", "videoId", "dateLastUpdated", "datePublished" });
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true)); //Convert Empty String Values To NULL
	}
	
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("video", videoService.getVideo(id));
		return "admin/video/view";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, videoService.getVideo(id));
		return "admin/video/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("video") @Valid Video video, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			populateForm(model, video);
			return "admin/video/form";
		}
		videoService.mergeVideo(video);
		redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
		return "redirect:/admin/video/update/" + video.getId();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model, HttpServletRequest request) {
		int pagerSize = (size==null) ? 20 : size.intValue();
        int pagerPage = (page==null) ? 0 : page.intValue();
        int pagerOffset = pagerSize * pagerPage;
		Long countArticles = articleService.countArticlesByArticleType(ArticleType.VIDEO);
		Pager pager = new Pager(request.getRequestURI(), countArticles, Integer.valueOf(pagerPage).longValue(), pagerSize);		
		model.addAttribute("countArticles", countArticles);
        model.addAttribute("pagerSize", pagerSize);
        model.addAttribute("pagerPage", pagerPage);
		model.addAttribute("articles", articleService.getArticlesByArticleType(ArticleType.VIDEO, pagerSize, pagerOffset, null));
		model.addAttribute("pager", pager);
		return "admin/article/list";
	}
	
	/**
	 * Populate Video Form
	 * @param model Model
	 * @param video Video Object
	 */
	void populateForm(Model model, Video video) {
		model.addAttribute("video", video);
		Video existing = videoService.getVideo(video.getId()); //Categories, Authors And RelatedArticles Are Not Given As Input In The Form
		model.addAttribute("categories", existing.getCategories());
		model.addAttribute("authors", existing.getAuthors());
		model.addAttribute("relatedArticles", existing.getRelatedArticles());
	}
}