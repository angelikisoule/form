package gr.soule.form.core.controllers.admin;

import gr.soule.form.core.service.ChainService;
import gr.soule.form.core.service.FormService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Main Controller For Admin. Make Sure That The Request Mapping Patterns Are More Specific Than Everything 
 * Else Defined In The WebController Class, Otherwise You Would Not Have An Option To Access The Admin Pages
 * @author npapadopoulos
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired private FormService formService;
	@Autowired private ChainService chainService;
	
	@Value("${alert.article.forced.success}")
	private String FORCED_SUCCESS;
	@Value("${alert.article.forced.error}")
	private String FORCED_ERROR;

	@RequestMapping(method = RequestMethod.GET)
	public String main(Model model) {
		model.addAttribute("countForms", formService.count());
		//model.addAttribute("countChains", chainService.count());
		return "admin/home";
	}

}