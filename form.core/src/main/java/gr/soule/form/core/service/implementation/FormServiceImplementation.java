package gr.soule.form.core.service.implementation;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.soule.form.core.service.FormService;

@Service
@Transactional(readOnly = true)
public class FormServiceImplementation implements FormService{

	private static Logger logger = Logger.getLogger(FormServiceImplementation.class.getName());
}
