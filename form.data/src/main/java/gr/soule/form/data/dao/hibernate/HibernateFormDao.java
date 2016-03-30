package gr.soule.form.data.dao.hibernate;

import java.util.List;

import gr.soule.form.data.dao.FormDao;
import gr.soule.form.data.entities.Form;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Form's DAO Implementation
 * @author asoule
 */

@Repository
public class HibernateFormDao extends HibernateAbstractDao<Form> implements FormDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Form> get(int pagerSize, int pagerOffset) {
		Query query = getSession().createQuery("SELECT f FROM Form WHERE OFFSET=:pagerOffset LIMIT=:pagerSize");
		query.setParameter("pagerOffset", pagerOffset);
		query.setParameter("pagerSize", pagerSize);
		query.setCacheable(true);
		return (List<Form>) query.list();
	}

}
