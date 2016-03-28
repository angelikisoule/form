package gr.soule.form.core.utils;

/**
 * @author nk, tk
 */
public class URLDecorator {

    public static String urlStringConvert(String urlString, String title, int articleID) {
        String articleTitle = slugify(title);
        int articlePathEndIndex = urlString.lastIndexOf("/");
        return urlString.substring(0, articlePathEndIndex + 1) + articleTitle + "." + articleID + ".html";
    }

    public static String slugify(String input) {
        if(input == null || input.length() == 0) {
        	return "";
        }
        String toReturn = input.toLowerCase();
        toReturn = lettersOnly(toReturn);
        toReturn = toReturn.replaceAll("[-]+", "-");
        if(toReturn.startsWith("-")) {
            toReturn = toReturn.replaceFirst("-", "");
        }
        if(toReturn.endsWith("-")) {
            toReturn = toReturn.substring(0, toReturn.length() - 1);
        }
        return toReturn;
    }

    private static String lettersOnly(String s) {
        return GreeklishConverter.toGreeklish(s).replaceAll("[^a-z0-9_]", "-");
    }
}
