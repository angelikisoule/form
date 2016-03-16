package gr.media24.atom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import gr.media24.mSites.atom.domain.AtomFeed;
import gr.media24.mSites.atom.parser.AtomParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author npapadopoulos
 */
public class AtomTestBase {

	@Before
	public void initialize() {

	}

	@After
	public void clear() {

	}

	@Test
	public void parseOnlineTest() throws XmlPullParserException, IOException {
		AtomParser parser = new AtomParser();
		URL url = new URL("http://content.24media.gr/develop/articles/?sections=609&profile=c3BvcnQyNHJvaQ==&types=news&view=generic%2Fv2%2Flatest-atom&items=10");
		AtomFeed feed = parser.parse(url.openConnection().getInputStream());
		assertNotNull(feed);
		assertNotNull(feed.getLinks());
		assertNotNull(feed.getEntries());
		assertTrue(feed.getEntries().size() > 0);
		assertNotNull(feed.getTitle());
		assertNotNull(feed.getSubtitle());
		assertNotNull(feed.getId());
		assertNotNull(feed.getIcon());
		assertNotNull(feed.getUpdated());
	}
	
	@Test
	public void parseFileTest() throws IOException, XmlPullParserException {
		AtomParser parser = new AtomParser();
        AtomFeed feed = parser.parse(this.getClass().getClassLoader().getResource("AtomFeed.xml").openStream());
        assertNotNull(feed);
        assertNotNull(feed.getLinks());
        assertEquals(feed.getLinks().size(), 5);
        assertNotNull(feed.getEntries());
        assertTrue(feed.getEntries().size() > 0);
        assertNotNull(feed.getUpdated());
        assertEquals("sport24", feed.getTitle());
		assertEquals("sport24", feed.getSubtitle());
		assertEquals("http://www.sport24.gr/", feed.getId());
		assertEquals("http://www.sport24.gr/skins/bugs-fixed/img/logo-big-transparent_1.png", feed.getIcon());
		assertNotNull(feed.getUpdated());
        /*
         * Test Some Entries
         */
		assertTrue(feed.getEntries().get(0).getCategories().size() == 8);
        assertTrue(feed.getEntries().get(0).getLinks().size() == 4);
        assertEquals(feed.getEntries().get(0).getAuthors().get(0), "Γιώτα Κουνάλη");
        assertTrue(feed.getEntries().get(0).getContent().contains("Ράικονεν"));
        //Product Fields
        assertTrue(feed.getEntries().get(19).getPrice().equals("50")); //Price Is Defined As A String
        assertTrue(feed.getEntries().get(19).getLink().equals("http://go.linkwi.se/z/10526-0/CD14896/?lnkurl=http%3A%2F%2Fedhardystyle.gr"));
        //Tag Fields
        assertTrue(feed.getEntries().get(2).getTags().size()==2);
        assertTrue(feed.getEntries().get(2).getTags().get(1).getName().equals("free-the-nipple"));
	}

    @Test(expected = RuntimeException.class)
    public void timeoutExceptionTest() throws XmlPullParserException, IOException {
        AtomParser parser = new AtomParser();
        InputStream input = new DelayingInputStream();
        assertNull(parser.parse(input, 100)); //Timeout After 100 MILLISECONDS
    }

    private class DelayingInputStream extends InputStream {
        @Override
        public int read() throws IOException {
            while(true) { //Endless Loop
            
            }
        }
    }
}
