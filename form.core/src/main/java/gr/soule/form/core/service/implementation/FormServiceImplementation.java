package gr.soule.form.core.service.implementation;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import gr.soule.form.core.service.FormService;
import gr.soule.form.data.dao.FormDao;
import gr.soule.form.data.entities.Form;

@Service
@Transactional(readOnly = true)
public class FormServiceImplementation implements FormService{

	//private static Logger logger = Logger.getLogger(FormServiceImplementation.class.getName());
	
	@Autowired private FormDao formDao;
	
	@Override
	public void archiveForm(Long id) {
		formDao.deleteById(id);
	}

	@Override
	public Long count() {
		return formDao.count();
	}
	
	@Override
	public List<Form> get(){
		return formDao.getAll();
	}
	
	@Override
	public List<Form> get(int pagerSize, int pagerOffset) {
		return formDao.get(pagerSize, pagerOffset);
	}

	@Override
	public Form getById(Long id) {
		return formDao.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveForm(Form form, Errors errors) {
		validateForm(form, errors);
		if(!errors.hasErrors()) formDao.persistOrMerge(form);
		
	}

	private void validateForm(Form form, Errors errors) {
		if(form.getText().isEmpty() || form.getText().equalsIgnoreCase("")) { //Empty text
			errors.rejectValue("text", "error.empty");
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteForm(Long id) {
		formDao.deleteById(id);
	}
}
