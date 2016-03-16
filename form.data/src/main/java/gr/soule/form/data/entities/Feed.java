package gr.media24.mSites.data.entities;

import java.io.Serializable;

import gr.media24.mSites.data.enums.FeedFlag;
import gr.media24.mSites.data.enums.FeedStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
 * Feed Bean
 * @author npapadopoulos
 */
@NamedQueries({
	@NamedQuery(
		name = "getFeedByUrlParams",
		query = "FROM Feed WHERE url=:url AND params=:params"),
	@NamedQuery(
		name = "getFeedByFeedFlag",
		query = "FROM Feed WHERE feedFlag=:feedFlag"),
	@NamedQuery(
		name = "getFeedByFeedFlagsAndFeedStatus",
		query = "FROM Feed WHERE feedFlag IN (:feedFlags) AND feedStatus=:feedStatus")
})
@Entity
@Table(name = "feed", uniqueConstraints = { @UniqueConstraint(columnNames = { "url", "params" })})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "LONG_TERM")
public class Feed extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Length(min = 5, max = 255)
	@Column(name = "url")
	private String url;
	
	@NotNull
	@Length(min = 5, max = 255)
	@Column(name = "params")
	private String params;
	
	@Length(max = 45)
	@Column(name = "profile")
	private String profile;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "category", referencedColumnName = "id", nullable = false)
	private Category category;
	
	@NotNull
	@Column(name = "feedFlag")
	private FeedFlag feedFlag;
	
	@NotNull
	@Column(name = "feedStatus")
	private FeedStatus feedStatus;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
	
	public String getProfile() {
		return profile;
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public FeedFlag getFeedFlag() {
		return feedFlag;
	}
	
	public void setFeedFlag(FeedFlag feedFlag) {
		this.feedFlag = feedFlag;
	}
	
	public FeedStatus getFeedStatus() {
		return feedStatus;
	}
	
	public void setFeedStatus(FeedStatus feedStatus) {
		this.feedStatus = feedStatus;
	}
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Feed Object
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("url", url)
			.append("params", params)
			.append("profile", profile)
			.append("category", category)
			.append("feedFlag", feedFlag)
			.append("feedStatus", feedStatus)
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
		Feed rhs = (Feed) obj;
		return new EqualsBuilder()
			.append(url, rhs.url)
			.append(params, rhs.params)
			.append(profile, rhs.profile)
			.append(category, rhs.category)
			.append(feedFlag, rhs.feedFlag)
			.append(feedStatus, rhs.feedStatus)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(271, 277) //Two Randomly Chosen Prime Numbers
            .append(url)
            .append(params)
            .append(profile)
            .append(category)
            .append(feedFlag)
            .append(feedStatus)
            .toHashCode();
    }
}
