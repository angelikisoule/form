package gr.soule.form.core.service;

import gr.soule.form.data.entities.Role;

import java.util.List;

import org.springframework.validation.Errors;


/**
 * Role's Service Interface
 * @author asoule
 */
public interface RoleService {

	/**
	 * Save Role Object
	 * @param role Role Object
	 * @param errors BindingResult Errors Of Role Form
	 */
	void saveRole(Role role, Errors errors);
	
	/**
	 * Get Role Object Given It's Id
	 * @param id Role's Id
	 * @return Role Object
	 */
	Role getRole(Long id);
	
	/**
	 * Get All Role Objects
	 * @return List Of Roles
	 */
	List<Role> getRoles();
	
	/**
	 * Delete A Role Object Given It's Id
	 * @param id Role's Id
	 */
	void deleteRole(Long id);
}
