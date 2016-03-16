package gr.media24.mSites.core.controllers;

import gr.media24.mSites.core.Settings;
import gr.media24.mSites.core.service.ArticleService;
import gr.media24.mSites.core.service.CategoryService;
import gr.media24.mSites.core.service.NewspaperService;
import gr.media24.mSites.core.service.PublicationService;
import gr.media24.mSites.core.utils.CustomException;
import gr.media24.mSites.core.utils.GreeklishConverter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import javax.imageio.ImageIO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @Autowired private PublicationService publicationService;
    @Autowired private CategoryService categoryService;
    @Autowired private ArticleService articleService;
    @Autowired private NewspaperService newspaperService;

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
        else if(uri.startsWith("/newspapers")) {
        	boolean isNewspaperCategory = uri.endsWith(".html") ? false : true;
        	return newspaperService.getNewspaperModelAndView(uri, settings.getDefaultPublicationName(), isNewspaperCategory, datePublished);
        }
        else if(uri.endsWith(".html") || uri.endsWith(".ece")) { //.ece For Some Reserved Articles
        	return articleService.getArticleModelAndView(uri, request);
        }
        else {
        	String publicationName = settings.getDefaultPublicationName();
        	if(publicationName.equals("news247") && uri.toLowerCase().contains("ekloges2015/liveblog")) { //TODO Only As A Temporary Solution, We Need To Get Back To The templates Logic!
        		return new ModelAndView("ekloges/ekloges");
        	}
        	else if(publicationName.equals("sport24") && uri.toLowerCase().contains("sport24radio/player")) {
        		return new ModelAndView("templates/radio");
        	}
        	else {
        		return categoryService.getCategoryModelAndView(uri, publicationName, request);
        	}
        }
    }

    @RequestMapping(value = {"/img"}, method = {RequestMethod.GET})
    public ResponseEntity<byte[]> img(@RequestParam(value = "url", required = true) String url, @RequestParam(value = "width", required = true) String width,@RequestParam(value = "height", required = true) String height, HttpServletRequest request, HttpServletResponse response) throws CustomException, MalformedURLException, IOException {       
        String imageName=url.split("/")[url.split("/").length-1];
        String clearedImageName = imageName.replace("-", "").replace("+", "").replace(" ","");
        clearedImageName = GreeklishConverter.toGreeklish(clearedImageName);
        URL urlToThumb = new URL(url.replace(imageName, clearedImageName));
        Collection<URL> urls = new ArrayList<URL>();
        urls.add(urlToThumb);
        BufferedImage bImage = null;                
        bImage = Thumbnails.fromURLs(urls).size(Integer.parseInt(width), Integer.parseInt(height)).crop(Positions.CENTER).asBufferedImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", baos);
        baos.flush();
        byte[] imageInByteArray = baos.toByteArray();
        baos.close();
        HttpHeaders headers = new HttpHeaders();
        if(url.contains("jpg")) headers.setContentType(MediaType.IMAGE_JPEG);
        if(url.contains("png")) headers.setContentType(MediaType.IMAGE_PNG);
        if(url.contains("gif")) headers.setContentType(MediaType.IMAGE_GIF);
        headers.setContentLength(imageInByteArray.length);
        return new ResponseEntity<byte[]>(imageInByteArray, headers, HttpStatus.OK);
    }

    @RequestMapping(value = {"/cropped/**"}, method = {RequestMethod.GET})
    public String imgRedirect(@RequestParam(value = "width", required = true) String width,@RequestParam(value = "height", required = true) String height ,HttpServletRequest request, HttpServletResponse response) throws CustomException {
        String result = "http://"+settings.getDefaultPublicationName()+".gr/"
                    	+ request.getRequestURI().replace("/cropped/", "")
                    	.replace("/img/", "")
                    	.replace("/png", ".png")
                    	.replace("/jpg", ".jpg")
                    	.replace("/gif", ".gif");
        return "forward:/img/?url=" + result;
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