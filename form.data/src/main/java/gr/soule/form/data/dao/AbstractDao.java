package gr.soule.form.data.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author asoule
 * Generic Class's DAO Interface
 * @param <T> Generic Class
 */
public interface AbstractDao<T extends Object> {

	/**
	 * Persist Generic Class Object
	 * @param t Object To Be Persisted
	 */
	void persist(T t);
	
	/**
	 * Merge Generic Class Object
	 * @param t Object To Be Merged
	 */
	void merge(T t);

	/**
	 * Persist Or Merge Class Object Depending On It's Id Value
	 * @param t Object To Be Persisted Or Merged
	 */
	void persistOrMerge(T t);
	
	/**
	 * Get Generic Class Object
	 * @param id Object's Identifier
	 * @return Object or null If It's Not Found
	 */
	T get(Serializable id);
	
	/**
	 * Load Generic Class Object
	 * @param id Object's Identifier
	 * @return Object
	 */
	T load(Serializable id);
	
	/**
	 * Get All Generic Class Objects
	 * @return List Of Objects
	 */
	List<T> getAll();
	
	/**
	 * Delete Generic Class Object
	 * @param t Object To Be Deleted
	 */
	void delete(T t);
	
	/**
	 * Delete Generic Class Objects Given It's Id
	 * @param id Object's Id
	 */
	void deleteById(Serializable id);

	/**
	 * Count All Generic Class Objects
	 * @return Count Of Objects
	 */
	Long count();
	
	/**
	 * Refresh Generic Class Object
	 * @param t Object To Be Refreshed
	 */
	void refresh(T t);
	
	/**
	 * Synchronize Session's State With The Database
	 */
	void flush();
	
	/**
	 * Evict A Generic Class Object From Session's Cache
	 * @param t Object To Be Evicted
	 */
	void evict(T t);
	
	/**
	 * Evict All Objects From The Session's Cache
	 */
	void clear();
}
