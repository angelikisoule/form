package gr.media24.mSites.data.entities;

import java.io.Serializable;

import gr.media24.mSites.data.enums.EnclosureComment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * ArticleRelatedArticle Bean. Performance Wise (and Since You Need The EnclosureComment As  
 * An Extra Column On The Join Table) It's Better To Map The Article Relations As Entities
 * @author npapadopoulos
 */
@NamedQuery(
		name = "getArticleRelatedArticleByArticleAndRelatedAndEnclosureComment",
		query = "FROM ArticleRelatedArticle WHERE article=:article AND related=:related AND enclosureComment=:enclosureComment")
@Entity
@Table(name = "articleRelatedArticle", uniqueConstraints = { @UniqueConstraint(columnNames = { "articleId", "relatedArticleId", "enclosureComment" })})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class ArticleRelatedArticle extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "articleId")
	private Article article;
	
	@ManyToOne
	@JoinColumn(name = "relatedArticleId")
	private Article related;
	
	@Column(name = "enclosureComment")
	private EnclosureComment enclosureComment;

	public ArticleRelatedArticle() {
		
	}
	
	public ArticleRelatedArticle(Article article, Article related, EnclosureComment enclosureComment) {
		this.article = article;
		this.related = related;
		this.enclosureComment = enclosureComment;
	}
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Article getRelated() {
		return related;
	}

	public void setRelated(Article related) {
		this.related = related;
	}

	public EnclosureComment getEnclosureComment() {
		return enclosureComment;
	}

	public void setEnclosureComment(EnclosureComment enclosureComment) {
		this.enclosureComment = enclosureComment;
	}
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of ArticleRelatedArticle Object
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("article", article)
			.append("related", related)
			.append("enclosureComment", enclosureComment)
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
		ArticleRelatedArticle rhs = (ArticleRelatedArticle) obj;
		return new EqualsBuilder()
			.append(article, rhs.article)
			.append(related, rhs.related)
			.append(enclosureComment, rhs.enclosureComment)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(701, 719) //Two Randomly Chosen Prime Numbers
            .append(article)
            .append(related)
            .append(enclosureComment)
            .toHashCode();
    }
}