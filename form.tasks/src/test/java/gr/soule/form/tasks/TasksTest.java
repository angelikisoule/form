package gr.media24.mSites.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gr.media24.mSites.atom.domain.AtomEntry;
import gr.media24.mSites.data.dao.CategoryDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.dao.PublicationDao;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Publication;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.FeedStatus;
import gr.media24.mSites.tasks.api.ApiUpdaterFactory;
import gr.media24.mSites.tasks.escenic.EscenicUpdaterFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Test Base Class For Configuration And Entity / Entries Initialization
 * @author npapadopoulos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TasksTest {

	@Autowired private ApiUpdaterFactory apiUpdaterFactory;
	@Autowired private EscenicUpdaterFactory escenicUpdaterFactory;
	@Autowired private CategoryDao categoryDao;
	@Autowired private PublicationDao publicationDao;
	@Autowired private FeedDao feedDao;
	
	protected static boolean initialized = false;
	
    protected static List<AtomEntry> stories = new ArrayList<AtomEntry>();
    protected static List<AtomEntry> advertorials = new ArrayList<AtomEntry>();
    protected static List<AtomEntry> photostories = new ArrayList<AtomEntry>();
    protected static List<AtomEntry> pictures = new ArrayList<AtomEntry>();
    protected static List<AtomEntry> videos = new ArrayList<AtomEntry>();
    protected static List<gr.dsigned.atom.domain.AtomEntry> entries = new ArrayList<gr.dsigned.atom.domain.AtomEntry>();
    protected static List<gr.dsigned.atom.domain.AtomEntry> newspapers = new ArrayList<gr.dsigned.atom.domain.AtomEntry>();
    
	@Before
	public void atom() throws XmlPullParserException, IOException {
		/*
		 * Avoid Reading The Same Feeds More Than Once. A @BeforeClass Method Would Not Be Applicable Since getAtomEntries() Is Not static
		 */
		if(!initialized) {
			stories = apiUpdaterFactory.getAtomEntries(this.getClass().getClassLoader().getResource("apiStory.xml").openStream());
			advertorials = apiUpdaterFactory.getAtomEntries(this.getClass().getClassLoader().getResource("apiAdvertorial.xml").openStream());
			photostories = apiUpdaterFactory.getAtomEntries(this.getClass().getClassLoader().getResource("apiPhotostory.xml").openStream());
			pictures = apiUpdaterFactory.getAtomEntries(this.getClass().getClassLoader().getResource("apiPicture.xml").openStream());
			videos = apiUpdaterFactory.getAtomEntries(this.getClass().getClassLoader().getResource("apiMultipleTypeVideo.xml").openStream());
			entries = escenicUpdaterFactory.getAtomEntries(this.getClass().getClassLoader().getResource("escenicEntries.xml").openStream());
			newspapers = escenicUpdaterFactory.getAtomEntries(this.getClass().getClassLoader().getResource("escenicNewspaper.xml").openStream());
			
			initialized = true;
		}
	}

	@Before
	public void entities() {
		/*
		 * We'll Be Used As The Default Publication Where It's Needed
		 */
		Publication publication = new Publication();
		publication.setName("default");
		publicationDao.persistOrMerge(publication);
		
		/*
		 * Create A Category That You'll Use For The Tests
		 */
		Category category = new Category();
        category.setName("test");
        category.setSectionUniqueName("test");
        category.setPublication(publication);
        categoryDao.persistOrMerge(category);
        
        /*
         * Create Some Feeds That You'll Use For The Tests
         */
        Feed escenicFeed = new Feed();
        escenicFeed.setCategory(category);
        escenicFeed.setUrl("http://news247.gr/newspapers/Sport/");
        escenicFeed.setParams("?widget=rssfeed&view=feed&contentId=396336");
        escenicFeed.setFeedFlag(FeedFlag.ESCENIC);
        escenicFeed.setFeedStatus(FeedStatus.ENABLED);
		feedDao.persistOrMerge(escenicFeed);
		
		Feed sectionsFeed = new Feed();
		sectionsFeed.setCategory(category);
		sectionsFeed.setUrl("http://content.24media.gr/develop/articles/");
		sectionsFeed.setParams("?sections=609&profile=c3BvcnQyNHJvaQ==&types=picture&view=generic%2Fv2%2Flatest-atom&items=5");
		sectionsFeed.setFeedFlag(FeedFlag.API_SECTIONS);
		sectionsFeed.setFeedStatus(FeedStatus.ENABLED);
		sectionsFeed.setProfile("c3BvcnQyNHJvaQ==");
		feedDao.persistOrMerge(sectionsFeed);
		
		Feed poolFeed = new Feed();
		poolFeed.setCategory(category);
		poolFeed.setUrl("http://content.24media.gr/develop/pool/");
		poolFeed.setParams("?section=609&profile=cmFkaW8=&types=news,advertorial&areas=topStories&groups=topStories1&view=generic%2Fv2%2Fpool-atom");
		poolFeed.setFeedFlag(FeedFlag.API_POOL);
		poolFeed.setFeedStatus(FeedStatus.ENABLED);
		poolFeed.setProfile("c3BvcnQyNHJvaQ==");
		feedDao.persistOrMerge(poolFeed);
	}

	@Test
	public void empty() { //An Empty Test

	}
}