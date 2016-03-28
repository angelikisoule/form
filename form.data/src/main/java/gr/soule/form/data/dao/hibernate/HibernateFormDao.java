package gr.soule.form.data.dao.hibernate;

import gr.soule.form.data.dao.FormDao;
import gr.soule.form.data.entities.Form;

import org.springframework.stereotype.Repository;

/**
 * Form's DAO Implementation
 * @author asoule
 */

@Repository
public class HibernateFormDao extends HibernateAbstractDao<Form> implements FormDao {

}
