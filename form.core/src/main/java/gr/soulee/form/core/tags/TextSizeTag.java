package gr.media24.mSites.core.tags;

import javax.servlet.http.Cookie;

/**
 * @author nk, tk
 */
public class TextSizeTag {
	
	private enum TextSizeValue {
		normal, small, large;
	}

	private static String cookieName = "textsize";
	private static TextSizeValue defaultTextsize = TextSizeValue.normal;
	private TextSizeValue textsize;
	private Cookie cookie;

	public TextSizeTag(String textsize, Cookie[] cookies) {
		this.textsize = textsize == null ? null : TextSizeValue.valueOf(textsize);
		this.cookie = this.getTextSizeCookie(cookies);
	}

	private Cookie getTextSizeCookie(Cookie[] cookies) {
		if(cookies == null) return null;
		for(Cookie cookie : cookies) {
			if(!cookie.getName().equals(TextSizeTag.cookieName)) continue;
			return cookie;
		}
		return null;
	}

	private Cookie sendFontSizeCookie(TextSizeValue textsizeValue) {
		Cookie cookie = new Cookie(TextSizeTag.cookieName, textsizeValue.toString());
		cookie.setPath("/");
		return cookie;
	}

	public boolean hasModifiedCookie() {
		return this.getModifiedCookie() != null;
	}

	public Cookie getModifiedCookie() {
		if(cookie == null) {
			return sendFontSizeCookie(TextSizeTag.defaultTextsize);
		}
		else if(textsize != null && cookie.getValue() != textsize.toString()) {
			return sendFontSizeCookie(textsize);
		}
		else {
			return null;
		}
	}

	public String getTextsize() {
		if(cookie == null) {
			return TextSizeTag.defaultTextsize.toString();
		}
		else if (textsize != null && cookie.getValue() != textsize.toString()) {
			return textsize.toString();
		}
		else {
			return cookie.getValue();
		}
	}
}