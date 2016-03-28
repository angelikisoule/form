package gr.soule.form.data.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@NamedQuery(
		name = "getFormById",
		query = "FROM Form WHERE id=:id")
@Entity
@Table(name = "form")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Form extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -3740206521051641989L;
	
	@Lob
	@Column(name = "text")
	private String text;
	public String getText() { return text; }
	public void setText(String text) { this.text = text; }

}
