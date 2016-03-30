package gr.soule.form.data.dao;

import java.util.List;

import gr.soule.form.data.entities.Form;

public interface FormDao extends AbstractDao<Form>{
	
	List<Form> get(int pagerSize, int pagerOffset);

}
