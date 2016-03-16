package gr.media24.mSites.core.tags;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gr.media24.mSites.core.Settings;
import gr.media24.mSites.core.service.ArticleService;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 * Format Article's Body Tag
 * @author npapadopoulos, tk
 */
public class ArticleBodyTag extends SimpleTagSupport {

    private static final Logger logger = Logger.getLogger(ArticleBodyTag.class.getName());
    private JspWriter out;
    private ArticleService articleService;
    private Settings settings;
    private ServletRequest servletRequest;
    private PageContext pageContext;
    private String body;
    private Integer rosA, rosB; //Inline Positioning Of Advertisements, If null They Will Not Be Inserted In Article's Body
    private String imageVersion;
    private String croppedWidth;
    private String croppedHeight;
    private String capitalize;

    @Override
    public void doTag() throws JspException {
        try {
            if (pageContext == null) pageContext = (PageContext) getJspContext();
            out = pageContext.getOut();
            servletRequest = pageContext.getRequest();
            articleService = (ArticleService) servletRequest.getAttribute("articleService");
            settings = (Settings) servletRequest.getAttribute("settings");

            Document document = Jsoup.parse(body);

            /*
             * Images
             */
            Elements images = document.select("img");
            for(Element image : images) {
                Attributes attributes = image.attributes();
                for(Attribute attribute : attributes) {
                    if(attribute.getKey().equals("width") || attribute.getKey().equals("height") || attribute.getKey().equals("id") || attribute.getKey().equals("src")) {
                        if(attribute.getKey().equals("src")) {
                            /*
                             * Check If There Is A width Attribute On ArticleBodyTag And Replace The Inline Image Version
                             */
                            String src = attribute.getValue();
                            if(imageVersion!=null && !src.endsWith(".gif")) { //You Have To Keep /BINARY/original/ Version For .gif Otherwise It Will Be Broken
                                Pattern pattern = Pattern.compile("/w[0-9]{2,3}[a-zA-Z]*/");
                                Matcher matcher = pattern.matcher(src);
                                if(matcher.find()) { //data-original For Lazy Loading
                                	image.attr("data-original", src.replaceAll("/w[0-9]{2,3}[a-zA-Z]*/", "/" + imageVersion + "/"));
                                }
                                else if (src.contains("/original/")) {
                                	image.attr("data-original", src.replace("/original/", "/" + imageVersion + "/"));
                                }
                                else {
                                	image.attr("data-original", src.replace("/BINARY/", "/BINARY/" + imageVersion + "/"));
                                }
                            }
                            else {
                                /*
                                 * Check If There Is A croppedWidth Attribute On ArticleBodyTag And Replace The Inline Image Version
                                 */
                                if(croppedWidth!=null && croppedHeight!=null && src.contains(settings.getDefaultPublicationName())) {
                                    image.attr("data-original", src.replace("http://www." + settings.getDefaultPublicationName() + ".gr/", "/cropped/")
                                            .replace(".jpg", "/jpg")
                                            .replace(".png", "/png")
                                            .replace(".gif", "/gif") 
                                            + "?width=" + croppedWidth 
                                            + "&height=" + croppedHeight);
                                }
                                else {
                                    image.attr("data-original", src);
                                }
                            }
                        }
                        image.removeAttr(attribute.getKey());
                    }
                }
                image.attr("class", "img-responsive lazyload");
                image.attr("alt", "Inline Image");
            }
            
            /*
             * Clear Empty Paragraphs
             */
            Elements paragraphs = document.select("p");
            for (Element paragraph : paragraphs) {
                String p = paragraph.html().trim();
                if(p.isEmpty() || p.equals("&nbsp;")) {
                    paragraph.remove();
                }
                paragraph.html(p.replaceAll("&nbsp;", ""));
            }

            /*
             * Advertisements
             */
            if(rosA!=null || rosB!=null) {
	            Map<String, String> advertisements = settings.getInlineAdvertisements();
	            String rosAContent = advertisements.get("ROS_A");
	            String rosBContent = advertisements.get("ROS_B");
	            paragraphs = document.select("p:not(table p, blockquote p)"); //Important To Exclude table, blockquote Paragraphs From Counting
	            int paragraphsTotal = paragraphs.size();
	            int paragraphCounter = 0;
	            for(Element paragraph : paragraphs) {
	            	paragraphCounter++;
	                if(rosA!=null && rosA>0) { //ROS_A Positioning
		                if(paragraphsTotal>rosA) {
		                    if(paragraphCounter==rosA) {
		                    	paragraph.append(rosAContent);
		                    }
		                }
		                else {
		                    if(paragraphCounter==0) {
		                    	paragraph.append(rosAContent);
		                    }
		                }
	                }
	                if(rosB!=null && rosB>0) { //ROS_B Positioning
		                if(paragraphsTotal>rosB) {
		                    if(paragraphCounter==rosB) {
		                    	paragraph.append(rosBContent);
		                    }
		                }
		                else {
		                    if(paragraphCounter==paragraphs.size()-1) {
		                    	paragraph.append(rosBContent);
		                    }
		                }
	                }
	            }
            }

            /*
             * iFrames
             */
            Elements iFrames = document.select("iframe");
            for(Element iFrame : iFrames) {
                String src = iFrame.attr("src");
                String extra = "embed-responsive-16by9"; //The Default Ratio
                if(src!=null && 
                	(src.indexOf("playbuzz.com")!=-1 || 
                	 src.indexOf("storify.com")!=-1 || 
                	 src.indexOf("ilgiornale.it")!=-1 ||
                	 src.indexOf("qmerce.com")!=-1
                	)) { //Do Not Wrap Them
                
                }
                else if(src.indexOf("sport24radio.gr")!=-1 ||
                		src.indexOf("static.24media.gr/temp/tmp/radio-test")!=-1) {
                	iFrame.remove(); //We Remove Them For Now Because They're Not Responsive
                }
                else { //Remove Width And Height And Wrap It In A Container
                    Attributes attributes = iFrame.attributes();
                    for(Attribute attribute : attributes) {
                        if(attribute.getKey().equals("width") || attribute.getKey().equals("height")) {
                        	iFrame.removeAttr(attribute.getKey());
                        }
                    }
                    if(src.indexOf("vine.co")!=-1) { /*Vine Embeds Need Some Extra Care*/
                    	extra = "embed-responsive-1by1";
                    }
                    iFrame.addClass("embed-responsive-item");
                    iFrame.wrap("<div class='embed-responsive " + extra + "'></div>"); //TODO Some Testing For 16:9 or 4:3 Would Be Nice
                }
            }

            /*
             * LiveBlog and Playwire Scripts
             */
            Elements scripts = document.select("script");
            for(int i = 0; i < scripts.size(); i++) {
                String src = scripts.get(i).attr("src");
                if(src!=null && src.indexOf("live.24media.gr")!=-1) {
                    try {
                        String scriptHtml = scripts.get(i + 1).html(); //The Script Containing The Liveblog's Info Is The Next One
                        scripts.get(i + 1).remove(); //Remove The Real Script
                        String liveblogId = null;
                        String publicationId = null;
                        /*
                         * Get The Liveblog Id
                         */
                        Pattern pattern = Pattern.compile("liveBlogID[ ]?[:][ ]?(\\d+)");
                        Matcher matcher = pattern.matcher(scriptHtml);
                        while(matcher.find()) { //There Can't Be More Than One
                        	liveblogId = matcher.group(1);
                        }
                        /*
                         * Get The Publication Id
                         */
                        pattern = Pattern.compile("publicationID[ ]?[:][ ]?(\\d+)");
                        matcher = pattern.matcher(scriptHtml);
                        while(matcher.find()) { //There Can't Be More Than One
                        	publicationId = matcher.group(1);
                        }
                        servletRequest.setAttribute("liveblogId", liveblogId);
                        servletRequest.setAttribute("publicationId", publicationId);
                    }
                    catch (IndexOutOfBoundsException exception) { //Stay Silent
                    
                    }
                    break; //No Need To Continue
                }
                if(src!=null && src.indexOf("playwire.com")!=-1) { //We Have To Replace data-width And data-height Attributes In Order To Have A Responsive Video
                	scripts.get(i).attr("data-width", "100%");
                	scripts.get(i).removeAttr("data-height");
                }
            }

            /*
             * Photostories
             */
            Elements photostories = document.select("a");
            for(Element photostory : photostories) {
                String href = photostory.attr("href");
                if(href != null && photostory.attr("href").contains("galleries/photostories")) {
                    photostory.attr("id", "photostory");
                    String eceArticleId = articleService.getEceArticleIdFromUri(href);
                    Map<String, String> pictures = articleService.getPhotostoryPictures(eceArticleId);
                    servletRequest.setAttribute("photostoryPictures", pictures);
                }
            }

            /*
             * Tables
             */
            Elements tables = document.select("table");
            for(Element table : tables) {
                table.addClass("table table-striped");
                table.wrap("<div class='table-responsive'></div>");
            }

            /*
             * Anchors, External Links target="_blank"
             */
            Elements anchors = document.select("a");
            for(Element anchor : anchors) {
                String href = anchor.attr("href");
                if(!href.startsWith("/") && !href.isEmpty() && !href.contains("javascript:void") && !href.contains(settings.getDefaultPublicationName() + ".gr")) {
                	anchor.attr("target", "_blank");
                }
            }

            /*
             * Capitalize Body Elements Given With The capitalize Tag Attribute
             */
            if(capitalize != null) {
                Elements capitals = document.select(capitalize); //Just One Tag Type For Now
                for(Element capital : capitals) {
                    String content = capital.html();
                    if(content != null && !content.isEmpty()) {
                    	capital.html(jSoupToUpperCase(content));
                    }
                }
            }

            out.print(document.body().html());
        }
        catch(Exception exception) {
            logger.error(exception);
        }
    }

    /*
     * Recursively Parse A jSoup Element And Capitalize It's Content
     */
    private String jSoupToUpperCase(String content) {
        Document element = Jsoup.parse(content);
        jSoupToUpperCase(element.body());
        return element.html();
    }

    private void jSoupToUpperCase(Node node) {
        if(node instanceof TextNode) {
            TextNode textnode = (TextNode) node;
            textnode.text(ToUpperCaseTag.convertToUpperCase(textnode.text()));
        }
        for(Node child : node.childNodes()) {
            jSoupToUpperCase(child);
        }
    }

    public JspWriter getOut() {
        return out;
    }

    public void setOut(JspWriter out) {
        this.out = out;
    }

    public ArticleService getArticleService() {
        return articleService;
    }

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public ServletRequest getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(ServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public PageContext getPageContext() {
        return pageContext;
    }

    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getRosA() {
    	return rosA;
    }

    public void setRosA(Integer rosA) {
    	this.rosA = rosA;
    }

    public Integer getRosB() {
    	return rosB;
    }

    public void setRosB(Integer rosB) {
    	this.rosB = rosB;
    }

    public String getImageVersion() {
        return imageVersion;
    }

    public void setImageVersion(String imageVersion) {
        this.imageVersion = imageVersion;
    }

    public String getCroppedWidth() {
        return croppedWidth;
    }

    public void setCroppedWidth(String croppedWidth) {
        this.croppedWidth = croppedWidth;
    }

    public String getCroppedHeight() {
        return croppedHeight;
    }

    public void setCroppedHeight(String croppedHeight) {
        this.croppedHeight = croppedHeight;
    }

    public String getCapitalize() {
        return capitalize;
    }

    public void setCapitalize(String capitalize) {
        this.capitalize = capitalize;
    }
}