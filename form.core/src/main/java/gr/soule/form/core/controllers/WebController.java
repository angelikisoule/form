package gr.soule.form.core.controllers;


import gr.soule.form.core.Settings;
import gr.soule.form.core.utils.CustomException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author npapadopoulos, tk
 */
@Controller
public class WebController {

    @Autowired private Settings settings;

    private static Logger logger = Logger.getLogger(WebController.class);

    /*
     * When a URL matches multiple patterns, a sort is used to find the most specific match. A pattern with a lower count 
     * of URI variables and wild cards is considered more specific. For example /hotels/{hotel}/* has 1 URI variable and 
     * 1 wild card and is considered more specific than /hotels/{hotel}/** which as 1 URI variable and 2 wild cards. There
     * are also some additional special rules, i.e. the default mapping pattern /** is less specific than any other pattern.
     * [ http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping-pattern-comparison ]
     */
    @RequestMapping(value = {"/**"}, method = {RequestMethod.GET})
    public ModelAndView main(@RequestParam(value = "datePublished", required = false) String datePublished, HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String uri = request.getRequestURI();
        if(uri.equals("/login.html") || uri.equals("/denied.html")) { //Make Sure That You Have The Way To Access The Admin's Login Form
        	ModelAndView model = new ModelAndView();
        	String viewName = uri.equals("/login.html") ? "login" : "denied";
            model.setViewName(viewName);
            return model;
        }
        else {
        	return null;
        }
    }


    /**
     * Catch Custom Exceptions Thrown By The Service Layer And Depending On ErrorCode | ErrorMessage Redirect To A Proper View
     * @param exception Custom Exception Object
     * @return ModelAndView Object
     */
    @ExceptionHandler(CustomException.class)
    public ModelAndView handleCustomException(CustomException exception) {
        if(exception.getErrorCode().equals("deprecated") || exception.getErrorCode().equals("archived")) {
            logger.trace(exception.getErrorMessage()); //No Big Deal
        }
        else {
            logger.error(exception.getErrorMessage());
        }
        ModelAndView model = new ModelAndView();
        model.addObject("errorCode", exception.getErrorCode());
        model.addObject("errorMessage", exception.getErrorMessage());
        model.setViewName("exceptions/custom");
        return model;
    }
}