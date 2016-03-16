package gr.media24.mSites.tasks.escenic;

import gr.dsigned.atom.domain.AtomEntry;
import gr.dsigned.atom.domain.AtomFeed;
import gr.dsigned.atom.parser.AtomParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author nk, tk, npapadopoulos
 */
@Component
public class EscenicUpdaterFactory {

	private static final Logger logger = Logger.getLogger(EscenicUpdaterFactory.class.getName());

	public List<AtomEntry> getAtomEntries(String urlString) throws XmlPullParserException, IOException {
		List<AtomEntry> entries = new ArrayList<AtomEntry>();
		URL url = new URL(urlString);
		try {
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(15000);
			connection.setReadTimeout(15000);
			AtomFeed atomFeed = new AtomParser().parse(connection.getInputStream(), 30 * 1000);
			entries = atomFeed.getEntries();
			return entries;
		}
		catch(FileNotFoundException exception) {
			logger.error("Exception While Trying To Read A Feed, URL : " + url.toString());
			return entries;
		}
	}
	
	public List<AtomEntry> getAtomEntries(InputStream inputStream) throws XmlPullParserException, IOException {
		AtomFeed atomFeed = new AtomParser().parse(inputStream, 30 * 1000);
		return atomFeed.getEntries();
	}
}
