package gr.media24.mSites.data.entities;

import java.io.Serializable;

import gr.media24.mSites.data.enums.VideoType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
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
 * Video Bean
 * @author nk, npapadopoulos
 */
@Entity
@Table(name = "article")
@DiscriminatorValue(value = "VIDEO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Video extends Article implements Serializable {

	private static final long serialVersionUID = 1L;

	@Lob
	@Column(name = "embeddedCode")
	private String embeddedCode;

    @NotNull
    @Column(name = "videoType")
    private VideoType videoType;
    
    @Length(max = 45)
    @Column(name = "videoId")
    private String videoId;
    
    public String getEmbeddedCode() {
    	return embeddedCode;
    }
    
    public void setEmbeddedCode(String embeddedCode) {
    	this.embeddedCode = embeddedCode;
    }

	public VideoType getVideoType() {
		return videoType;
	}
	
	public void setVideoType(VideoType videoType) {
		this.videoType = videoType;
	}
	
	public String getVideoId() {
		return videoId;
	}
	
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
	/**
	 * Override The Default toString() Method
	 * @return String Representation Of Video Object
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append(super.toString())
			.append("embeddedCode", embeddedCode)
			.append("videoType", videoType)
			.append("videoId", videoId)
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
		Video rhs = (Video) obj;
		return new EqualsBuilder()
			.appendSuper(super.equals(obj))
			.append(embeddedCode, rhs.embeddedCode)
			.append(videoType, rhs.videoType)
			.append(videoId, rhs.videoId)
			.isEquals();
	}
	
	/**
	 * Override The Default hashCode() Method
	 * @return Object's Hash Code
	 */
	@Override
    public int hashCode() {
        return new HashCodeBuilder(719, 727) //Two Randomly Chosen Prime Numbers
            .appendSuper(super.hashCode())
            .append(embeddedCode)
            .append(videoType)
            .append(videoId)
            .toHashCode();
    }
}