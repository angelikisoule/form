package gr.media24.mSites.core.controllers.admin;

import org.hibernate.Cache;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin/cache")
@PropertySource(value = "classpath:messages/alerts.properties")
public class CacheController {

	@Autowired SessionFactory sessionFactory;
	private Cache cache;
	
	@Value("${alert.cache.delete.success}")
	private String DELETE_SUCCESS;
	
    @RequestMapping(value = "clear", method = RequestMethod.GET)
    public String resetCache(Model model, RedirectAttributes redirectAttributes) {
    	getCache().evictAllRegions();    	
    	redirectAttributes.addFlashAttribute("successMessage", DELETE_SUCCESS);
		return "redirect:/admin/statistics/list";
    }
    
    /**
     * Get Hibernate's Cache
     * @return Hibernate Cache
     */
    private Cache getCache() {
    	this.cache = sessionFactory.getCache();
    	return cache;
    }
}
