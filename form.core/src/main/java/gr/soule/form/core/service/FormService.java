package gr.soule.form.core.service;

import gr.soule.form.data.entities.Form;

import java.util.List;

import org.springframework.validation.Errors;

public interface FormService {
	
	Form getById(Long id);
	
	void archiveForm(Long id);
	
	Long count();
	
	List<Form> get();
	
	List<Form> get(int pagerSize, int pagerOffset);
	
	void saveForm(Form form, Errors errors);
	
	void deleteForm(Long id);
}
