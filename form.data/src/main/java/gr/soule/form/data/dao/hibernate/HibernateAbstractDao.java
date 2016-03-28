package gr.soule.form.data.dao.hibernate;

import gr.soule.form.data.dao.AbstractDao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;


/**
 * @author asoule
 * Generic Class's DAO Implementation
 * @param <T> Generic Class
 */
public abstract class HibernateAbstractDao<T extends Object> implements AbstractDao<T> {

	@Autowired private SessionFactory sessionFactory;
	
	private Class<T> domainClass;
	
	/**
	 * Protected Method Allowing Subclasses To Perform Persistent Operations Against The Hibernate Session
	 * @return Hibernate Session
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Reflection To Get Actual Domain Class
	 * @return Domain Class
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
		if(domainClass==null) {		
			ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
			this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}
	
	/**
	 * Reflection To Get Actual Domain Class Name
	 * @return Domain Class Name
	 */
	private String getDomainClassName() {
		return getDomainClass().getName();
	}

	/**
	 * Persist Generic Class Object. The Method Sets dateUpdated Property If It Exists
	 * @param t Object To Be Persisted
	 */
	public void persist(T t) {
		Method method = ReflectionUtils.findMethod(getDomainClass(), "setDateUpdated", new Class[] { Calendar.class });
		if(method!=null) {
			try {
				method.invoke(t, Calendar.getInstance());
			}
			catch(Exception exception) { /* Do Nothing */
			
			}
		}
		getSession().persist(t);
	}

	/**
	 * Merge Generic Class Object. The Method Sets dateUpdated Property If It Exists
	 * @param t Object To Be Merged
	 */
	public void merge(T t) {
		Method method = ReflectionUtils.findMethod(getDomainClass(), "setDateUpdated", new Class[] { Calendar.class });
		if(method!=null) {
			try {
				method.invoke(t, Calendar.getInstance());
			}
			catch(Exception exception) { /* Do Nothing */
			
			}
		}
		getSession().merge(t);
	}
	
	public void persistOrMerge(T t) {
		Method method = ReflectionUtils.findMethod(getDomainClass(), "getId");
		if(method!=null) {
			try {
				Long id = (Long) method.invoke(t);
				
				if(id==null) {
					persist(t);
				}
				else {
					merge(t);
				}
			}
			catch(Exception exception) { /* Do Nothing */

			}
		}	
	}
	
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T) getSession().get(getDomainClass(), id);
	}

	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		return (T) getSession().load(getDomainClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Query query = getSession().createQuery("FROM " + getDomainClassName());
		query.setCacheable(true);
		return (List<T>) query.list();
	}

	public void delete(T t) {
		getSession().delete(t);
	}

	public void deleteById(Serializable id) {
		delete(load(id));
	}

	public Long count() {
		return (Long) getSession().createQuery("SELECT COUNT(*) FROM " + getDomainClassName()).uniqueResult();
	}
	
	public void refresh(T t) {
		getSession().refresh(t);
	}	
	
	public void flush() {
		getSession().flush();
	}
	
	public void evict(T t) {
		getSession().evict(t);
	}
	
	public void clear() {
		getSession().clear();
	}
}