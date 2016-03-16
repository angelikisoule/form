package gr.media24.mSites.core.controllers.admin;

import javax.validation.Valid;

import gr.media24.mSites.core.service.CategoryService;
import gr.media24.mSites.core.service.FeedService;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.FeedStatus;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Feed's Controller
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/feed")
public class FeedController {

	@Autowired private FeedService feedService;
	@Autowired private CategoryService categoryService;
	
	@Value("${alert.feed.update.success}")
	private String UPDATE_SUCCESS;
	@Value("${alert.feed.delete.success}")
	private String DELETE_SUCCESS;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "id", "url", "params", "profile", "category", "feedFlag", "feedStatus" });
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		populateForm(model, new Feed());
		return "admin/feed/form";
	}	
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		populateForm(model, feedService.getFeed(id));
		return "admin/feed/form";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.POST)
	public String postForm(@ModelAttribute("feed") @Valid Feed feed, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		feedService.saveFeed(feed, result);
		if(result.hasErrors()) {
			populateForm(model, feed);
			return "admin/feed/form";
		}
		else {
			redirectAttributes.addFlashAttribute("successMessage", UPDATE_SUCCESS);
			return "redirect:/admin/feed";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("feeds", feedService.getFeeds());
		return "admin/feed/list";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		feedService.deleteFeed(id);
		redirectAttributes.addFlashAttribute("successMessage", DELETE_SUCCESS);
		return "redirect:/admin/feed";
	}
	
	/**
	 * Populate Feed Form
	 * @param model Model
	 * @param feed Feed Object
	 */
	void populateForm(Model model, Feed feed) {
		model.addAttribute("feed", feed);
		model.addAttribute("selectCategory", categoryService.getCategoriesByNullGroupName());
		model.addAttribute("selectFeedFlag", FeedFlag.getListOf());
		model.addAttribute("selectFeedStatus", FeedStatus.getListOf());
	}
}
