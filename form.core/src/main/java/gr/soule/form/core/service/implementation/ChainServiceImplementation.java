package gr.soule.form.core.service.implementation;

import gr.soule.form.core.service.ChainService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ChainServiceImplementation implements ChainService{

}
