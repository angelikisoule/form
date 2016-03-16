package gr.media24.mSites.data.entities;

import gr.media24.mSites.data.enums.ArticleState;
import gr.media24.mSites.data.enums.ArticleType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.FilterJoinTable;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Article Bean
 * @author nk, npapadopoulos
 */
@NamedQuery(
		name = "getArticleByEceArticleId",
		query = "FROM Article WHERE eceArticleId=:eceArticleId")
@FilterDefs({
	@FilterDef(name = "homeCategory"),
	@FilterDef(name = "relatedByEnclosureComment", parameters = @ParamDef(name="enclosureComment", type = "int")),
	@FilterDef(name = "relatedByEnclosureComments", parameters = @ParamDef(name="enclosureComments", type = "int"))
})
@Entity
@Table(name = "article")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Article extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "eceArticleId", unique = true)
	private String eceArticleId;

	@NotNull
	@Column(name = "articleType")
	private ArticleType articleType;
	
	@NotNull
	@Column(name = "articleState")
    private ArticleState articleState;

	@Lob
	@NotNull
	@Column(name = "title")
    private String title;
	
	@Lob
	@Column(name = "supertitle")
	private String supertitle;
	
	@Lob
	@Column(name = "leadText")
	private String leadText;
	
	@Lob
	@Column(name = "teaserTitle")
    private String teaserTitle;
	
	@Lob
	@Column(name = "teaserSupertitle")
	private String teaserSupertitle;
	
	@Lob
	@Column(name = "teaserLeadText")
	private String teaserLeadText;

	@Lob
    @Column(name = "alternate")
    private String alternate;

	@FilterJoinTable(name = "homeCategory", condition = "categoryOrder=0")
	@ManyToMany(fetch = FetchType.LAZY)
    @OrderColumn(name = "categoryOrder")
    @JoinTable(name = "articleCategory",
    	joinColumns = {
    		@JoinColumn(name = "articleId", nullable = false)
    	},
    	inverseJoinColumns = {
    		@JoinColumn(name = "categoryId", nullable = false)
    	}
    )
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
    private List<Category> categories = new ArrayList<Category>();

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "articleTag",
    	joinColumns = {
    		@JoinColumn(name = "articleId", nullable = false)
    	},
    	inverseJoinColumns = {
    		@JoinColumn(name = "tagId", nullable = false)
    	}
    )
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
	private Set<Tag> tags = new HashSet<Tag>();
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "articleAuthor",
    	joinColumns = {
    		@JoinColumn(name = "articleId", nullable = false)
    	},
    	inverseJoinColumns = {
    		@JoinColumn(name = "authorId", nullable = false)
    	}
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
    private Set<Author> authors = new HashSet<Author>();
    
    @Filters({
    	@Filter(name = "relatedByEnclosureComment", condition = "enclosureComment=:enclosureComment"),
    	@Filter(name = "relatedByEnclosureComments", condition = "enclosureComment IN (:enclosureComments)")
    })
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
    private Set<ArticleRelatedArticle> relatedArticles = new LinkedHashSet<ArticleRelatedArticle>();
    
    //Do Not Define price As A Numeric Field, Editors Are Entering Text In It
    @Length(max = 45)
    @Column(name = "price")
    private String price;
    
	@Lob
    @Column(name = "link")
    private String link;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "datePublished")
    private Calendar datePublished;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "dateLastUpdated")
    private Calendar dateLastUpdated;

    public String getEceArticleId() {
    	return eceArticleId;
    }
    
    public void setEceArticleId(String eceArticleId) {
    	this.eceArticleId = eceArticleId;
    }
    
    public ArticleType getArticleType() {
    	return articleType;
    }
    
    public void setArticleType(ArticleType articleType) {
    	this.articleType = articleType;
    }
    
    public ArticleState getArticleState() {
    	return articleState;
    }
    
    public void setArticleState(ArticleState articleState) {
    	this.articleState = articleState;
    }    
    
    public String getTitle() {
    	return title;
    }

    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getSupertitle() {
    	return supertitle;
    }
    
    public void setSupertitle(String supertitle) {
    	this.supertitle = supertitle;
    }
    
    public String getLeadText() {
    	return leadText;
    }
    
    public void setLeadText(String leadText) {
    	this.leadText = leadText;
    }

    public String getTeaserTitle() {
    	return teaserTitle;
    }

    public void setTeaserTitle(String teaserTitle) {
    	this.teaserTitle = teaserTitle;
    }
    
    public String getTeaserSupertitle() {
    	return teaserSupertitle;
    }
    
    public void setTeaserSupertitle(String teaserSupertitle) {
    	this.teaserSupertitle = teaserSupertitle;
    }
    
    public String getTeaserLeadText() {
    	return teaserLeadText;
    }
    
    public void setTeaserLeadText(String teaserLeadText) {
    	this.teaserLeadText = teaserLeadText;
    }
    
    public String getAlternate() {
    	return alternate;
    }
    
    public void setAlternate(String alternate) {
    	this.alternate = alternate;
    }
    
    public List<Category> getCategories() {
    	return categories;
    }
    
    public void setCategories(List<Category> categories) {
    	this.categories = categories;
    }
    
    public Set<Author> getAuthors() {
    	return authors;
    }
    
    public Set<Tag> getTags() {
    	return tags;
    }
    
    public void setTags(Set<Tag> tags) {
    	this.tags = tags;
    }
    
    public void setAuthors(Set<Author> authors) {
    	this.authors = authors;
    }
    
    public Set<ArticleRelatedArticle> getRelatedArticles() {
    	return relatedArticles;
    }
    
    public void setRelatedArticles(Set<ArticleRelatedArticle> relatedArticles) {
    	this.relatedArticles = relatedArticles;
    }
    
    public String getPrice() {
    	return price;
    }
    
    public void setPrice(String price) {
    	this.price = price;
    }
    
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
    
    public Calendar getDatePublished() {
    	return datePublished;
    }
    
    public void setDatePublished(Calendar datePublished) {
    	this.datePublished = datePublished;
    }
    
    public Calendar getDateLastUpdated() {
    	return dateLastUpdated;
    }
    
    public void setDateLastUpdated(Calendar dateLastUpdated) {
    	this.dateLastUpdated = dateLastUpdated;
    }
    
    /**
     * Add ArticleRelatedArticle To Article's Relationships
     * @param relatedArticle RelatedArticle Object
     */
    public void addRelatedArticle(ArticleRelatedArticle relatedArticle){
    	if(!relatedArticles.contains(relatedArticle)) {
    		this.relatedArticles.add(relatedArticle);
    	}
    }
    
    /**
     * Add List Of ArticleRelatedArticles To Article's Relationships
     * @param relatedArticles List Of ArticleRelatedArticle Objects
     */
    public void addRelatedArticle(List<ArticleRelatedArticle> relatedArticles) {
    	for(ArticleRelatedArticle relatedArticle : relatedArticles) {
    		addRelatedArticle(relatedArticle);
    	}
    }
        
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Article Object
	 */
    @Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("eceArticleId", eceArticleId)
			.append("articleType", articleType)
			.append("articleState", articleState)
			.append("title", title)
			.append("supertitle", supertitle)
			.append("leadText", leadText)
			.append("teaserTitle", teaserTitle)
			.append("teaserSupertitle", teaserSupertitle)
			.append("teaserLeadText", teaserLeadText)
			.append("alternate", alternate)
			.append("price", price)
			.append("link", link)
			.append("datePublished", datePublished==null ? null : DateFormatUtils.format(datePublished, "yyyy-MM-dd HH:mm:SS"))
			.append("dateLastUpdated", dateLastUpdated==null ? null : DateFormatUtils.format(dateLastUpdated, "yyyy-MM-dd HH:mm:SS"))
			.toString();
	}
    
	/**
	 * Override The Default equals() Method
	 * @return TRUE If It's Equal, Else FALSE
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null) { return false; }
		if(obj==this) { return true; }
		if(obj.getClass()!=getClass()) {
			return false;
		}
		Article rhs = (Article) obj;
		return new EqualsBuilder()
			.append(eceArticleId, rhs.eceArticleId)
			.append(articleType, rhs.articleType)
			.append(articleState, rhs.articleState)
			.append(title, rhs.title)
			.append(supertitle, rhs.supertitle)
			.append(leadText, rhs.leadText)
			.append(teaserTitle, rhs.teaserTitle)
			.append(teaserSupertitle, rhs.teaserSupertitle)
			.append(teaserLeadText, rhs.teaserLeadText)
			.append(alternate, rhs.alternate)
			.append(price, rhs.price)
			.append(link, rhs.link)
			.append(datePublished, rhs.datePublished)
			.append(dateLastUpdated, rhs.dateLastUpdated)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(53, 59) //Two Randomly Chosen Prime Numbers
            .append(eceArticleId)
            .append(articleType)
            .append(articleState)
            .append(title)
            .append(supertitle)
            .append(leadText)
            .append(teaserTitle)
            .append(teaserSupertitle)
            .append(teaserLeadText)
            .append(alternate)
            .append(price)
            .append(link)
            .append(datePublished)
            .append(dateLastUpdated)
            .toHashCode();
    }
}