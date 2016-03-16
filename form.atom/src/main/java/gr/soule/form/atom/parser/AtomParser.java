package gr.media24.mSites.atom.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import gr.media24.mSites.atom.domain.AtomCategory;
import gr.media24.mSites.atom.domain.AtomEntry;
import gr.media24.mSites.atom.domain.AtomFeed;
import gr.media24.mSites.atom.domain.AtomLink;
import gr.media24.mSites.atom.domain.AtomTag;
import gr.media24.mSites.atom.utils.DateUtils;

/**
 * @author npapadopoulos
 */
public class AtomParser {

	private static final Logger logger = Logger.getLogger(AtomParser.class.getName());
	
	private static final ExecutorService executor = Executors.newFixedThreadPool(3);
	
	private AtomFeed feed;
	private final HashMap<String, String> attributesMap = new HashMap<String, String>();
	
	private static final String FEED_TAG = "feed";
    private static final String TYPE_TAG = "type";
	private static final String TITLE_TAG = "title";
    private static final String LINK_TAG = "link";
    private static final String SUBTITLE_TAG = "subtitle";
    private static final String ID_TAG = "id";
    private static final String ICON_TAG = "icon";
    private static final String UPDATED_TAG = "updated";
    private static final String PUBLISHED_TAG = "published";
    private static final String ENTRY_TAG = "entry";
    private static final String CATEGORY_TAG = "category";
    private static final String AUTHOR_TAG = "author";
    private static final String NAME_TAG = "name";
    private static final String SUMMARY_TAG = "summary";
    private static final String CONTENT_TAG = "content";
    private static final String CONTENT_ENCODED_TAG = "content:encoded";
    private static final String ENCODED_TAG = "encoded";
    private static final String CREDITS_TAG = "credits";
    private static final String CAPTION_TAG = "caption";
    private static final String PRODUCT_TAG = "product";
	
	public AtomFeed parse(final InputStream input, long timeOut) {
		Callable<AtomFeed> task = new Callable<AtomFeed>() {
			public AtomFeed call() throws XmlPullParserException, IOException {
				AtomParser parser = new AtomParser();
				return parser.parse(input);
			}
		};
		AtomFeed result = null;
		Future<AtomFeed> future = executor.submit(task);
		try {
            result = future.get(timeOut, TimeUnit.MILLISECONDS);
        }
		catch(Exception exception) {
            throw new RuntimeException(exception);
        }
		finally {
            future.cancel(true);
        }
        return result;
	}
	
	public AtomFeed parse(InputStream input) throws XmlPullParserException, IOException {
		feed = new AtomFeed();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(input, null);
        int eventType = parser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                String startTag = parser.getName();
                if(FEED_TAG.equals(startTag)) {
                    processFeed(parser);
                }
            }
            eventType = parser.next();
        }
        return feed;
	}
	
	/**
	 * Process Atom Feed's Tags
	 * @param parser XmlPullParser
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private void processFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                String startTag = parser.getName();
                if(TITLE_TAG.equals(startTag)) {
                    feed.setTitle(parser.nextText());
                }
                else if(LINK_TAG.equals(startTag)) {
                	AtomLink link = new AtomLink();
                	link.setRel(attributesMap.get("rel"));
                	link.setType(attributesMap.get("type"));
                	link.setHref(attributesMap.get("href"));
                	link.setTitle(attributesMap.get("title"));
                	feed.addLink(link);
                }
                else if(SUBTITLE_TAG.equals(startTag)) {
                    feed.setSubtitle(parser.nextText());
                }
                else if(ID_TAG.equals(startTag)) {
                	feed.setId(parser.nextText());
                }
                else if(ICON_TAG.equals(startTag)) {
                    feed.setIcon(parser.nextText());
                }
                else if(UPDATED_TAG.equals(startTag)) {
                	String next = parser.nextText();
                	Calendar updated = DateUtils.stringToCalendar(next);
                	feed.setUpdated(updated);
                }
                else if(ENTRY_TAG.equals(startTag)) {
                    processEntry(parser);
                }
            }
            else if(eventType == XmlPullParser.END_TAG) {
                String endTag = parser.getName();
                if(FEED_TAG.equals(endTag)) {
                	break;
                }
            }
            eventType = parser.next();
        }
	}
	
	/**
	 * Process Atom Entry's Tags
	 * @param parser XmlPullParser
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private void processEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        AtomEntry entry = new AtomEntry();
        while(eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
            	String startTag = parser.getName();
            	if(TYPE_TAG.equals(startTag)) {
                	entry.setType(parser.nextText());
                }
                else if(TITLE_TAG.equals(startTag)) {
                	getAttributeMap(parser);
                	if(attributesMap.containsKey("comment") && attributesMap.get("comment").equals("teaser")) {
                		entry.setTeaserTitle(parser.nextText());
                	}
                	else {
                		entry.setTitle(parser.nextText());
                	}
                }
                else if(LINK_TAG.equals(startTag)) {
                	getAttributeMap(parser);
                	if(attributesMap.containsKey("rel") && attributesMap.get("rel").equals("alternate")) { //Article's Alternate URL
                        entry.setAlternate(attributesMap.get("href"));
                    }
                	if(attributesMap.containsKey("rel") && attributesMap.get("rel").equals("@supertitle")) { //Article's Supertitle
                        if(attributesMap.containsKey("comment") && attributesMap.get("comment").equals("teaser")) {
                    		entry.setTeaserSupertitle(attributesMap.get("title"));
                    	}
                    	else {
                    		entry.setSupertitle(attributesMap.get("title"));
                    	}
                	}
                	if(attributesMap.containsKey("type") && attributesMap.get("type").equals("tag")) { //Article's Tags
                		AtomTag tag = new AtomTag();
                		tag.setName(attributesMap.get("title"));
                		tag.setDisplayName(attributesMap.get("display_name"));
                		entry.addTag(tag);
                	}
                	AtomLink link = new AtomLink();
                	link.setRel(attributesMap.get("rel"));
                	link.setType(attributesMap.get("type"));
                	link.setHref(attributesMap.get("href"));
                	link.setTitle(attributesMap.get("title"));
                	link.setComment(attributesMap.get("comment"));
                	link.setCredits(attributesMap.get("credits"));
                	link.setCaption(attributesMap.get("caption"));
                	link.setCode(attributesMap.get("code"));
                	entry.addLink(link);
                }
                else if(CATEGORY_TAG.equals(startTag)) {
                    getAttributeMap(parser);
                    AtomCategory category = new AtomCategory();
                    category.setLabel(attributesMap.get("label"));
                    category.setTerm(attributesMap.get("term"));
                    entry.addCategory(category);
                }
                else if(AUTHOR_TAG.equals(startTag)) {
                    processAuthor(entry, parser);
                }
                else if (ID_TAG.equals(startTag)) {
                    entry.setId(parser.nextText());
                }
                else if(PUBLISHED_TAG.equals(startTag)) {
                	String next = parser.nextText();
                	Calendar published = DateUtils.stringToCalendar(next);
                	if(published.getTimeInMillis()==0) {
                		logger.error("Publish Date Parsing Error For Entry : " + entry.getId() + ", publishedDate : " + next);
                	}
                	entry.setPublished(published);
                }
                else if(UPDATED_TAG.equals(startTag)) {
                	String next = parser.nextText();
                	Calendar updated = DateUtils.stringToCalendar(next);
                	if(updated.getTimeInMillis()==0) {
                		logger.error("Updated Date Parsing Error For Entry : " + entry.getId() + ", updatedDate : " + next);
                	}
                	entry.setUpdated(updated);
                }
                else if(SUMMARY_TAG.equals(startTag)) {
                    getAttributeMap(parser);
                	if(attributesMap.containsKey("comment") && attributesMap.get("comment").equals("teaser")) {
                		entry.setTeaserSummary(parser.nextText());
                	}
                	else {
                		entry.setSummary(parser.nextText());
                	}
                }
                else if(CONTENT_TAG.equals(startTag) || CONTENT_ENCODED_TAG.equals(startTag) || ENCODED_TAG.equals(startTag)) {
                	entry.setContent(parser.nextText());
                }
                else if(CREDITS_TAG.equals(startTag)) {
                	entry.setCredits(parser.nextText());
                }
                else if(CAPTION_TAG.equals(startTag)) {
                	entry.setCaption(parser.nextText());
                }
                else if(PRODUCT_TAG.equals(startTag)) {
                	getAttributeMap(parser);
                	entry.setPrice(attributesMap.get("price"));
                	entry.setLink(attributesMap.get("href"));
                }
            }
            else if(eventType == XmlPullParser.END_TAG) {
                String endTag = parser.getName();
                if(ENTRY_TAG.equals(endTag)) {
                    feed.addEntry(entry);
                    break;
                }
            }
            eventType = parser.next();
        }
	}
	
	/**
	 * Process Atom Entry's Author Tag
	 * @param entry Atom Entry
	 * @param parser XmlPullParser
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private void processAuthor(AtomEntry entry, XmlPullParser parser) throws XmlPullParserException, IOException {
		int eventType = parser.getEventType();
        String authorName = "";
        while(eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                String startTag = parser.getName();
                if(NAME_TAG.equals(startTag)) {
                    authorName = parser.nextText();
                }
            }
            else if(eventType == XmlPullParser.END_TAG) {
                String endTag = parser.getName();
                if(AUTHOR_TAG.equals(endTag)) {
                    entry.getAuthors().add(authorName);
                    break;
                }
            }
            eventType = parser.next();
        }
	}
	
	/**
	 * Return attributesMap For An Atom Entry Tag
	 * @param parser XmlPullParser
	 */
    private void getAttributeMap(XmlPullParser parser) {
        attributesMap.clear();
        for(int i = 0; i < parser.getAttributeCount(); i++) {
            attributesMap.put(parser.getAttributeName(i), parser.getAttributeValue(i));
        }
    }
}