package gr.media24.mSites.data.dao;

import gr.media24.mSites.data.dao.AuthorDao;
import gr.media24.mSites.data.dao.CategoryDao;
import gr.media24.mSites.data.dao.FeedDao;
import gr.media24.mSites.data.dao.PhotostoryDao;
import gr.media24.mSites.data.dao.PictureDao;
import gr.media24.mSites.data.dao.RoleDao;
import gr.media24.mSites.data.dao.SectionDao;
import gr.media24.mSites.data.dao.StoryDao;
import gr.media24.mSites.data.dao.UserDao;
import gr.media24.mSites.data.dao.VideoDao;
import gr.media24.mSites.data.entities.Advertorial;
import gr.media24.mSites.data.entities.Article;
import gr.media24.mSites.data.entities.ArticleRelatedArticle;
import gr.media24.mSites.data.entities.Author;
import gr.media24.mSites.data.entities.Category;
import gr.media24.mSites.data.entities.Feed;
import gr.media24.mSites.data.entities.Newspaper;
import gr.media24.mSites.data.entities.Photostory;
import gr.media24.mSites.data.entities.Picture;
import gr.media24.mSites.data.entities.Publication;
import gr.media24.mSites.data.entities.Role;
import gr.media24.mSites.data.entities.Section;
import gr.media24.mSites.data.entities.Story;
import gr.media24.mSites.data.entities.Tag;
import gr.media24.mSites.data.entities.User;
import gr.media24.mSites.data.entities.Video;
import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;
import gr.media24.mSites.data.enums.EnclosureComment;
import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.FeedStatus;
import gr.media24.mSites.data.enums.VideoType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * @author npapadopoulos
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class DaoTest {

	public static Calendar testDate = Calendar.getInstance();
	
	@Autowired private SessionFactory sessionFactory;
	@Autowired private FeedDao feedDao;
	@Autowired private AuthorDao authorDao;
	@Autowired private PublicationDao publicationDao;
	@Autowired private CategoryDao categoryDao;
	@Autowired private PictureDao pictureDao;
	@Autowired private VideoDao videoDao;
	@Autowired private NewspaperDao newspaperDao;
	@Autowired private PhotostoryDao photostoryDao;
	@Autowired private AdvertorialDao advertorialDao;
	@Autowired private StoryDao storyDao;
	@Autowired private SectionDao sectionDao;
	@Autowired private TagDao tagDao;
	@Autowired private RoleDao roleDao;
	@Autowired private UserDao userDao;

	protected List<Feed> feeds = new ArrayList<Feed>();
	protected List<Author> authors = new ArrayList<Author>();
	protected List<Publication> publications = new ArrayList<Publication>();
	protected List<Category> categories = new ArrayList<Category>();
	protected List<Picture> pictures = new ArrayList<Picture>();
	protected List<Video> videos = new ArrayList<Video>();
	protected List<Newspaper> newspapers = new ArrayList<Newspaper>();
	protected List<Photostory> photostories = new ArrayList<Photostory>();
	protected List<Advertorial> advertorials = new ArrayList<Advertorial>();
	protected List<Story> stories = new ArrayList<Story>();
	protected List<Section> sections = new ArrayList<Section>();
	protected List<Tag> tags = new ArrayList<Tag>();
	protected List<Article> articles = new ArrayList<Article>();
	protected List<Role> roles = new ArrayList<Role>();
	protected List<User> users = new ArrayList<User>();
	
	@Before
	public void initialize() {
		authors = insertAuthors();
		publications = insertPublications();
		categories = insertCategories(publications);
		insertFeeds(categories);
		pictures = insertPictures();
		videos = insertVideos(pictures);
		newspapers = insertNewspapers();
		photostories = insertPhotostories(pictures);
		advertorials = insertAdvertorials(pictures);
		stories = insertStories();
		insertSections(publications, stories);
		insertTags(publications);
		articles.addAll(pictures);
		articles.addAll(videos);
		articles.addAll(newspapers);
		articles.addAll(photostories);
		articles.addAll(advertorials);
		articles.addAll(stories);
		roles = insertRoles();
		users = insertUsers(roles);
		/*
		 * Relate A Story With An Author And Two Articles
		 */
		Story story = stories.get(0);
		Author author = authors.get(0);
		story.setAuthors(new HashSet<Author>(Arrays.asList(author)));
		story.addRelatedArticle(new ArticleRelatedArticle(story, stories.get(1), null));
		story.addRelatedArticle(new ArticleRelatedArticle(story, videos.get(0), null));
		author.addArticle(story);
		storyDao.persistOrMerge(story);
		/*
		 * Flush Session To Ensure That The Tests Will Read From Database
		 */
		sessionFactory.getCurrentSession().flush();
	}
	
	@Test
	public void empty() {
		
	}
	
	private List<Author> insertAuthors() {
		
		List<Author> authors = new ArrayList<Author>();
		
		Author author = new Author();
		author.setName("Νίκος Παπαδόπουλος");
		authorDao.persistOrMerge(author);
		authors.add(author);
		
		author = new Author();
		author.setName("Νίκος Κασταμούλας");
		authorDao.persistOrMerge(author);
		authors.add(author);
		
		return authors;
	}
	
	private List<Publication> insertPublications() {
		
		List<Publication> publications = new ArrayList<Publication>();
		
		Publication publication = new Publication();
		publication.setName("ladylike");
		publicationDao.persistOrMerge(publication);
		publications.add(publication);
		
		publication = new Publication();
		publication.setName("sport24");
		publicationDao.persistOrMerge(publication);
		publications.add(publication);
		
		return publications;
	}
	
	private List<Category> insertCategories(List<Publication> publications) {
		
		List<Category> categories = new ArrayList<Category>();
		
		Category category = new Category();
		category.setName("Shopping List");
		category.setSectionUniqueName("shopping-list");
		category.setPublication(publications.get(0));
		categoryDao.persistOrMerge(category);
		categories.add(category);
		
		category = new Category();
		category.setName("Celebrities");
		category.setSectionUniqueName("celebrities");
		category.setGroupName("@main1");
		category.setPublication(publications.get(0));
		categoryDao.persistOrMerge(category);
		categories.add(category);
				
		category = new Category();
		category.setName("Ποδόσφαιρο");
		category.setSectionUniqueName("football");
		category.setPublication(publications.get(1));
		categoryDao.persistOrMerge(category);
		categories.add(category);
		
		category = new Category();
		category.setName("Γυναίκες");
		category.setSectionUniqueName("Women");
		category.setGroupName("@main2");
		category.setPublication(publications.get(0));
		categoryDao.persistOrMerge(category);
		categories.add(category);
		
		return categories;
	}
	
    private void insertFeeds(List<Category> categories) {
    	
        Feed feed = new Feed();
        feed.setUrl("http://www.sport24.gr/");
        feed.setParams("?widget=rssfeed&view=feed&contentId=174866&rsspass=2210");
        feed.setCategory(categories.get(0));
        feed.setFeedFlag(FeedFlag.ESCENIC);
        feed.setFeedStatus(FeedStatus.ENABLED);
        feedDao.persistOrMerge(feed);

        feed = new Feed();
        feed.setUrl("http://www.sport24.gr/football/");
        feed.setParams("?widget=rssfeed&view=feed&contentId=174866&rsspass=2210");
        feed.setCategory(categories.get(0));
        feed.setFeedFlag(FeedFlag.ESCENIC);
        feed.setFeedStatus(FeedStatus.ENABLED);
        feedDao.persistOrMerge(feed);
        
        feed = new Feed();
        feed.setUrl("http://content.iballs.gr/develop/articles");
        feed.setParams("?sections=3671&profile=bWljcm9zb2Z0IGZlZWRz&types=news&view=microsoft%2Fv1%2Flatest-atom");
        feed.setProfile("bWljcm9zb2Z0IGZlZWRz");
        feed.setCategory(categories.get(0));
        feed.setFeedFlag(FeedFlag.API_SECTIONS);
        feed.setFeedStatus(FeedStatus.ENABLED);
        feedDao.persistOrMerge(feed);
    }
    
	private List<Picture> insertPictures() {
        
		List<Picture> pictures = new ArrayList<Picture>();
        
        Picture picture = new Picture();
        picture.setEceArticleId("3429416");
        picture.setArticleType(ArticleType.PICTURE);
        picture.setArticleState(ArticleState.NEW);
        picture.setTitle("Picture 1");
        picture.setAlternate("http://news247.gr/eidiseis/afieromata/article3429416.ece/BINARY/w620/t1.jpg");
        pictureDao.persistOrMerge(picture);
        pictures.add(picture);
        
        picture = new Picture();
        picture.setEceArticleId("3429417");
        picture.setArticleType(ArticleType.PICTURE);
        picture.setArticleState(ArticleState.NEW);
        picture.setTitle("Picture 2");
        picture.setAlternate("http://news247.gr/eidiseis/afieromata/article3429417.ece/BINARY/w620/t2.jpg");
        picture.setCredits("Picture Credits");
        pictureDao.persistOrMerge(picture);
        pictures.add(picture);
        
        picture = new Picture();
        picture.setEceArticleId("3429418");
        picture.setArticleType(ArticleType.PICTURE);
        picture.setArticleState(ArticleState.EDIT);
        picture.setTitle("Picture 3");
        picture.setAlternate("http://news247.gr/eidiseis/afieromata/article3429418.ece/BINARY/w620/t3.jpg");
        picture.setCredits("Picture Credits");
        picture.setCaption("Picture Caption");
        pictureDao.persistOrMerge(picture);
        pictures.add(picture);
   
        picture = new Picture();
        picture.setEceArticleId("3427664");
        picture.setArticleType(ArticleType.PICTURE);
        picture.setArticleState(ArticleState.NEW);
        picture.setTitle("Picture 4");
        picture.setAlternate("http://news247.gr/eidiseis/paraksena/article3427664.ece/BINARY/w620/5.jpg");
        pictureDao.persistOrMerge(picture);
        pictures.add(picture);

        return pictures;
    }

    private List<Video> insertVideos(List<Picture> pictures) {
        
    	List<Video> videos = new ArrayList<Video>();
        
    	Video video = new Video();
    	video.setEceArticleId("3431365");
    	video.setArticleType(ArticleType.VIDEO);
    	video.setArticleState(ArticleState.NEW);
        video.setTitle("System of a Down - Live in Armenia");
        video.setAlternate("http://news247.gr/multimedia/videos/system-of-a-down-live-in-armenia.3431365.html");
        video.setVideoId("D-LZBh7x9a8");
        video.setVideoType(VideoType.YOUTUBE);
        video.addRelatedArticle(new ArticleRelatedArticle(video, pictures.get(3), EnclosureComment.MAIN));
        videoDao.persistOrMerge(video);
        videos.add(video);
        
        video = new Video();
        video.setEceArticleId("3431071");
        video.setArticleType(ArticleType.VIDEO);
    	video.setArticleState(ArticleState.NEW);
        video.setTitle("Σπίγκελ");
        video.setAlternate("http://content.24media.gr/develop/article?article=3431071&profile=c3BvcnQyNHJvaQ==&view=generic/v2/article-atom");
        video.setVideoId("3431071");
        video.setVideoType(VideoType.ADVANCEDCODE);
        video.setEmbeddedCode("<blockquote class='twitter-tweet' lang='en'>A Tweet</blockquote>");
		videoDao.persistOrMerge(video);
        videos.add(video);
        
        video = new Video();
        video.setEceArticleId("3430051");
        video.setArticleType(ArticleType.VIDEO);
    	video.setArticleState(ArticleState.ARCHIVED);
        video.setTitle("Το τρίποντο του Πρίντεζη");
        video.setAlternate("http://www.sport24.gr/multimedia/video/to-triponto-toy-printezh-servikh-perigrafh.3430051.html");
        video.setVideoId("AlJOp80_hdc");
        video.setVideoType(VideoType.DAILYMOTION);
        List<ArticleRelatedArticle> relatedPictures = new ArrayList<ArticleRelatedArticle>();
        relatedPictures.add(new ArticleRelatedArticle(video, pictures.get(1), EnclosureComment.MAIN));
        relatedPictures.add(new ArticleRelatedArticle(video, pictures.get(3), EnclosureComment.MAIN));
        video.addRelatedArticle(relatedPictures);
        videoDao.persistOrMerge(video);
        videos.add(video);
        
        return videos;
    }
    
    private List<Newspaper> insertNewspapers() {
    
    	List<Newspaper> newspapers = new ArrayList<Newspaper>();
    	
    	Newspaper newspaper = new Newspaper();
    	newspaper.setEceArticleId("3430180");
    	newspaper.setArticleType(ArticleType.NEWSPAPER);
    	newspaper.setArticleState(ArticleState.NEW);
    	newspaper.setTitle("ESPRESSO");
    	newspaper.setAlternate("http://news247.gr/newspapers/Afternoon/espresso/espresso.3430180.html");
    	newspaper.setLink("http://news247.gr/newspapers/Afternoon/espresso/article3430180.ece/BINARY/w300/20610579.jpg");
    	newspaperDao.persistOrMerge(newspaper);
    	newspapers.add(newspaper);
    	
    	return newspapers;
    }
    
	private List<Photostory> insertPhotostories(List<Picture> pictures) {
		
    	List<Photostory> photostories = new ArrayList<Photostory>();
    	
    	Photostory photostory = new Photostory();
    	photostory.setEceArticleId("2496265");
    	photostory.setArticleType(ArticleType.PHOTOSTORY);
    	photostory.setArticleState(ArticleState.NEW);
    	photostory.setTitle("Εικόνες από τον 31ο ΚΜΑ");
    	photostory.setLeadText("Πραγματικοί ήρωες της ζωής, υπερήρωες των κόμικ, αρχαίοι Έλληνες και δεκάδες καροτσάκια.");
    	photostory.setAlternate("http://www.sport24.gr/multimedia/eikones-apo-ton-31o-kma.2496265.html");
    	photostory.addRelatedArticle(new ArticleRelatedArticle(photostory, pictures.get(1), EnclosureComment.MAIN));
    	photostoryDao.persistOrMerge(photostory);
    	photostories.add(photostory);
    	
    	return photostories;
	}
	
    private List<Advertorial> insertAdvertorials(List<Picture> pictures) {
    	
    	List<Advertorial> advertorials = new ArrayList<Advertorial>();
    	
    	Advertorial advertorial = new Advertorial();
    	advertorial.setEceArticleId("3432337");
    	advertorial.setArticleType(ArticleType.ADVERTORIAL);
    	advertorial.setArticleState(ArticleState.NEW);
    	advertorial.setTitle("Αγκαλιά με την πρόκριση");
    	advertorial.setSupertitle("ΡΕΚΟΡ ΡΙΜΠΑΟΥΝΤ ΑΠΟ ΤΟΝ HOWARD");
    	advertorial.setLeadText("Ο Harden σημείωσε ατομικό ρεκόρ πόντων στα Playoffs και το Houston επιβίωσε στο run and gun παιχνίδι που έγινε στο Dallas, κάνοντας το 3-0 στη σειρά.");
    	advertorial.setAlternate("http://nba.sport24.gr/article/3432326/agkalia-me-tin-prokrisi-to-houston");
    	advertorial.addRelatedArticle(new ArticleRelatedArticle(advertorial, pictures.get(3), EnclosureComment.MAIN));
    	advertorialDao.persistOrMerge(advertorial);
    	advertorials.add(advertorial);
    
    	return advertorials;
    }
    
    private List<Story> insertStories() {
        
    	List<Story> stories = new ArrayList<Story>();
        
    	Story story = new Story();
    	story.setEceArticleId("3429600");
    	story.setArticleType(ArticleType.STORY);
    	story.setArticleState(ArticleState.NEW);
    	story.setTitle("Παραλίγο να δει τα ραδίκια 'ανάποδα'... στην προσπάθειά του να τα μαζέψει"); //Pulitzer Prize Is Waiting For Us
    	story.setBody("Article's Body");
    	story.setAlternate("http://news247.gr/eidiseis/koinonia/eglima/paraligo-na-dei-ta-radikia-anapoda-sthn-prospatheia-toy-na-ta-mazepsei.3429600.html");
    	story.getCategories().add(categories.get(0));
    	story.getCategories().add(categories.get(3));
    	story.setDatePublished(testDate);
    	story.setDateLastUpdated(testDate);
    	storyDao.persistOrMerge(story);
        stories.add(story);
        
        story = new Story();
    	story.setEceArticleId("3431303");
    	story.setArticleType(ArticleType.STORY);
    	story.setArticleState(ArticleState.NEW);
    	story.setTitle("Έξαλλοι οι Ινδιάνοι με τη νέα ταινία του Άνταμ Σάντλερ");
    	story.setSupertitle("Έξαλλοι οι Ινδιάνοι με τη νέα ταινία του Άνταμ Σάντλερ");
    	story.setBody("Article's Body");
    	story.setAlternate("http://news247.gr/eidiseis/psixagogia/cinema/eksalloi-oi-indianoi-me-th-nea-tainia-toy-antam-santler.3431303.html");
    	story.getCategories().add(categories.get(0));
    	story.getCategories().add(categories.get(1));
    	story.setDatePublished(testDate);
    	story.setDateLastUpdated(testDate);
    	storyDao.persistOrMerge(story);
        stories.add(story);
        
        story = new Story();
    	story.setEceArticleId("3430210");
    	story.setArticleType(ArticleType.STORY);
    	story.setArticleState(ArticleState.NEW);
    	story.setTitle("Στριπτιζέζ κηδειών : Δεύτε τελευταίον ασπασμόν με εξωτικές χορεύτριες");
    	story.setSupertitle("Στριπτιζέζ κηδειών: Δεύτε τελευταίον ασπασμόν με εξωτικές χορεύτριες");
    	story.setLeadText("Νέα ήθη στην Κίνα. Προσλαμβάνουν στριπτιζέζ για να προσελκύουν κόσμο στις νεκρώσιμες τελετές, προκαλώντας τη οργισμένη αντίδραση των Αρχών");
    	story.setBody("Article's Body");
    	story.setAlternate("http://news247.gr/eidiseis/paraksena/striptizez-khdeiwn-deute-teleytaion-aspasmon-me-ekswtikes-xoreutries.3430210.html");
    	story.getCategories().add(categories.get(0));
    	story.getCategories().add(categories.get(1));
    	story.setDatePublished(testDate);
    	story.setDateLastUpdated(testDate);
    	storyDao.persistOrMerge(story);
        stories.add(story);
        
        return stories;
    }
    
	private void insertSections(List<Publication> publications, List<Story> stories) {
		
		Section section = new Section();
        section.setName("newsletter@topStories1");
        section.setPublication(publications.get(0));
		section.setArticles(Lists.newArrayList((Article) stories.get(0), stories.get(1), stories.get(2)));
		sectionDao.persistOrMerge(section);
		
		section = new Section();
		section.setName("newsletter@main1");
		section.setPublication(publications.get(0));
		section.addArticle(stories.get(0));
		sectionDao.persistOrMerge(section);
	}

	private void insertTags(List<Publication> publications) {
		
		Tag tag = new Tag();
		tag.setName("rihanna");
		tag.setDisplayName("Rihanna");
		tag.setPublication(publications.get(0));
		tagDao.persistOrMerge(tag);
		
		tag = new Tag();
		tag.setName("ronaldo");
		tag.setDisplayName("Christiano Ronaldo");
		tag.setPublication(publications.get(1));
		tagDao.persistOrMerge(tag);		
	}
	
	private List<Role> insertRoles() {
	
		List<Role> roles = new ArrayList<Role>();
		
		Role role = new Role();
		role.setName("Administrator");
		roleDao.persistOrMerge(role);
		roles.add(role);
		
		role = new Role();
		role.setName("Editor");
		roleDao.persist(role);
		roles.add(role);
		
		return roles;
	}
	
	private List<User> insertUsers(List<Role> roles) {
		
		Role role = roles.get(0);
		List<User> users = new ArrayList<User>();
		
		User user = new User();
		user.setUsername("npapadopoulos");
		user.setEmail("npapadopoulos@24media.gr");
		user.setEnabled(true);
		user.setRole(role);
		userDao.persistOrMerge(user);
		users.add(user);
		
		user = new User();
		user.setUsername("asoule");
		user.setEmail("asoule@24media.gr");
		user.setEnabled(false);
		user.setRole(role);
		userDao.persistOrMerge(user);
		users.add(user);
				
		return users;
	}
}
