package gr.media24.mSites.data.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

/**
 * Author Bean
 * @author npapadopoulos
 */
@NamedQuery(
		name = "getAuthorByName",
		query = "FROM Author WHERE LOWER(name)=:name"
		)
@Entity
@Table(name = "author")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Author extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Length(min = 5, max = 100)
	@Column(name = "name", unique = true)
	private String name;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
	private Set<Article> articles = new HashSet<Article>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Article> getArticles() {
		return articles;
	}
	
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	
	/**
	 * Add An Article To Author's Articles
	 * @param article Article Object
	 */
	public void addArticle(Article article) {
		if(!articles.contains(article)) {
			this.articles.add(article);
		}
	}
	
	/**
	 * Add A List Of Articles To Author's Articles
	 * @param articles List Of Articles
	 */
	public void addArticle(List<Article> articles) {
		for(Article article : articles) addArticle(article);
	}
	
	/**
	 * Override The Default toString() Method 
	 * @return String Representation Of Author Object
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("name", name)
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
		Author rhs = (Author) obj;
		return new EqualsBuilder()
			.append(name, rhs.name)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(3, 5) //Two Randomly Chosen Prime Numbers
            .append(name)
            .toHashCode();
    }
}
