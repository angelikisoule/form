package gr.soule.form.data.dao.hibernate;

import gr.soule.form.data.dao.ChainDao;
import gr.soule.form.data.entities.Chain;

import org.springframework.stereotype.Repository;

/**
 * Chain's DAO Implementation
 * @author asoule
 */

@Repository
public class HibernateChainDao extends HibernateAbstractDao<Chain> implements ChainDao{

}
