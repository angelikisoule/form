package gr.soule.form.core.service.implementation;

import gr.soule.form.core.service.RoleService;
import gr.soule.form.data.dao.RoleDao;
import gr.soule.form.data.entities.Role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;


/**
 * Role's Service Implementation
 * @author asoule
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImplementation implements RoleService {

	@Autowired private RoleDao roleDao;

	@Override
	@Transactional(readOnly = false)
	public void saveRole(Role role, Errors errors) {
		validateForm(role, errors);
		if(!errors.hasErrors()) roleDao.persistOrMerge(role);
	}

	@Override
	public Role getRole(Long id) {
		return roleDao.get(id);
	}

	@Override
	public List<Role> getRoles() {
		return (List<Role>) roleDao.getAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteRole(Long id) {
		roleDao.deleteById(id);
	}
	
	/**
	 * Custom Validations For Form Fields
	 * @param role Role Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateForm(Role role, Errors errors) {
		validateName(role, errors);
	}

	/**
	 * Validate Uniqueness Of Role's Name
	 * @param role Role Object
	 * @param errors Form BidingResult Errors
	 */
	private void validateName(Role role, Errors errors) {
		Role existing = roleDao.getByName(role.getName());
		if(existing!=null && !existing.getId().equals(role.getId())) {
			errors.rejectValue("name", "error.duplicate");
		}
	}
}
