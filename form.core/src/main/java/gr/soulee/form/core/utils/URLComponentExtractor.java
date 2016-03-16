package gr.media24.mSites.core.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author nk
 */
public class URLComponentExtractor {

    /**
     * Given The Request URL This Method Should Return The Escenic Section Name
     * @param url Request URL
     * @return Section Name
     * @throws MalformedURLException 
     */
    public static String extractSectionName(String url) {
        String sectionName = "";
        if(url.matches(".*\\.(html|ece)(\\?.*|#.*)?")) {
        	url = url.replaceAll("\\.(html|ece).*", "");
        	sectionName = url.split("/")[url.split("/").length - 2];
        }
        else {
            url = url.replaceAll("(\\?.*|#.*)", "");
            sectionName = url.split("/")[url.split("/").length - 1];
        }
        return sectionName;
    }

    /**
     * Given The Request URL This Method Should Return The Escenic Root Section Name
     * @param url Request URL
     * @return Root Section Name
     * @throws MalformedURLException
     */
    public static String extractRootSectionName(String urlString) throws MalformedURLException {
        urlString = urlString.replaceAll("\\?.*", "").replaceAll("#.*", ""); //Clean Parameters
        URL url = new URL(urlString);
        String host = url.getHost();
        String[] tokens = urlString.split("/");
        
        int index = 0;
        for(String token : tokens) {
            index++;
            if(token.startsWith(host)) {
                break;
            }
        }

        if(index >= tokens.length) {
            return "";
        }
        else {
            return tokens[index];
        }
    }

    /**
     * @param url Request URL
     * @return TRUE If Section Is A Root Section, Otherwise FALSE
     * @throws MalformedURLException
     */
    public static boolean isHome(String url) throws MalformedURLException{
    	return extractRootSectionName(url).isEmpty();
    }
    
    /**
     * Given The Request URL This Method Should Return The Article Id
     * @param url Request URL
     * @return Article Id
     */
    public static String extractArticleID(String url) {
        if(url.matches(".*\\.html(\\?.*|#.*)?")) {
            return url.replaceAll("\\.html.*", "").replaceAll(".*\\.", "");
        }
        else if(url.matches(".*\\.ece(\\?.*|#.*)?")) {
            return url.replaceAll("\\.ece.*", "").replaceAll(".*/", "").replace("article", "");
        }
        else {
            return "";
        }
    }
    
    /**
     * @param url Request URL
     * @return TRUE If Request URL Is An Article's URL, Otherwise FALSE
     */
    public static boolean isArticle(String url){
    	return !extractArticleID(url).isEmpty();
    }

    /**
     * Given The Request URL This Method Should Return The Domain Name
     * @param url Request URL
     * @return Domain Name
     */
    public static String extractDomainName(String strURL) throws MalformedURLException {
        URL url = new URL(strURL);
        int port = url.getPort();
        String[] hostSubs = url.getHost().split("\\.");
        if(port == 80 || port == -1) {
            return hostSubs[hostSubs.length - 2] + "." + hostSubs[hostSubs.length - 1];
        }
        else {
            return hostSubs[hostSubs.length - 2] + "." + hostSubs[hostSubs.length - 1] + ":" + port;
        }
    }
}