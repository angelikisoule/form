package gr.media24.mSites.data.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

/**
 * Section Bean
 * @author npapadopoulos
 */
@NamedQueries({
	@NamedQuery(
			name = "getSectionByNamePublicationName",
			query = "FROM Section WHERE name=:name AND publication.name=:publicationName"),
	@NamedQuery(
			name = "getSectionByNameLikePublicationName",
			query = "FROM Section WHERE LOWER(name) LIKE :name AND publication.name=:publicationName")
})
@Entity
@Table(name = "section", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "publication" })})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Section extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Length(min = 5, max = 255)
	@Column(name = "name")
	private String name;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "publication", referencedColumnName = "id", nullable = false)
	private Publication publication;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderColumn(name = "articleOrder")
    @JoinTable(name = "sectionArticle",
    	joinColumns = {
    		@JoinColumn(name = "sectionId", nullable = false)
    	},
    	inverseJoinColumns = {
    		@JoinColumn(name = "articleId", nullable = false)
    	}
    )
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
	private List<Article> articles = new ArrayList<Article>();

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Publication getPublication() {
		return publication;
	}
	
	public void setPublication(Publication publication) {
		this.publication = publication;
	}
	
	public List<Article> getArticles() {
		return articles;
	}
	
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
	/**
	 * Add An Article To Section's Articles
	 * @param article
	 */
	public void addArticle(Article article) {
		if(!articles.contains(article)) {
			this.articles.add(article);
		}
	}
	
	/**
	 * Add A List Of Articles To Section's Articles
	 * @param articles List Of Articles
	 */
	public void addArticle(List<Article> articles) {
		for(Article article : articles) addArticle(article);
	}
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Section Object
	 */
    @Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("name", name)
			.append("publication", publication)
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
		Section rhs = (Section) obj;
		return new EqualsBuilder()
			.append(name, rhs.name)
			.append(publication, rhs.publication)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(181, 191) //Two Randomly Chosen Prime Numbers
            .append(name)
            .append(publication)
            .toHashCode();
    }
}
