package gr.soule.form.data.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@NamedQuery(
		name = "getChainById",
		query = "FROM Chain WHERE id=:id")
@Entity
@Table(name = "chain")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
public class Chain extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -491454815038753259L;
	
	@OneToMany(mappedBy = "chain", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SHORT_TERM")
	private List<Form> forms;
	public List<Form> getForms() { return forms; }
	public void setForms(List<Form> forms) { this.forms = forms; }

}
