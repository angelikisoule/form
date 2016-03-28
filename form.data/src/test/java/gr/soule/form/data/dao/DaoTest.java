package gr.soule.form.data.dao;


import gr.soule.form.data.entities.Chain;
import gr.soule.form.data.entities.Form;
import gr.soule.form.data.entities.Role;
import gr.soule.form.data.entities.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author asoule
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class DaoTest {

	public static Calendar testDate = Calendar.getInstance();
	
	@Autowired private SessionFactory sessionFactory;
	@Autowired private RoleDao roleDao;
	@Autowired private UserDao userDao;
	@Autowired private FormDao formDao;
	@Autowired private ChainDao chainDao;

	protected List<Form> forms = new ArrayList<Form>();
	protected List<Chain> chains = new ArrayList<Chain>();
	protected List<Role> roles = new ArrayList<Role>();
	protected List<User> users = new ArrayList<User>();
	
	@Before
	public void initialize() {
		forms = insertForms();
		chains = insertChains(forms);
		roles = insertRoles();
		users = insertUsers(roles);
		/*
		 * Flush Session To Ensure That The Tests Will Read From Database
		 */
		sessionFactory.getCurrentSession().flush();
	}
	
	@Test
	public void empty() {
		
	}
	
	private List<Form> insertForms() {
		
		List<Form> forms = new ArrayList<Form>();
		
		Form form = new Form();
		form.setText("Η επιτάχυνση δημιουργίας των 30.000 θέσεων βρέθηκε στο επίκεντρο της πρωινής σύσκεψης στο Μαξίμου για το προσφυγικό. Ήδη έχουν κατατεθεί τα πρώτα αιτήματα για επανεισδοχή προς Τουρκία ");
		formDao.persistOrMerge(form);
		forms.add(form);
		
		form = new Form();
		form.setText("Η εισαγγελία έδωσε στη δημοσιότητα βίντεο του τρίτου υπόπτου για τις επιθέσεις στο αεροδρόμιο των Βρυξελλών");
		formDao.persistOrMerge(form);
		forms.add(form);
		
		form = new Form();
		form.setText("Από την Ιταλία με προορισμό την Ελλάδα φέρεται να πέρασε ο Χαλίντ Ελ Μπακράουι, ο οποίος ανατινάχθηκε στο μετρό των Βρυξελλών");
		formDao.persistOrMerge(form);
		forms.add(form);
		
		form = new Form();
		form.setText("Οι δύο άνδρες 30 και 37 χρόνων μαζί με δύο συνεργούς τους είχαν απαγάγει τη νεαρή γυναίκα από την πλατεία Μεταξουργείου. ");
		formDao.persistOrMerge(form);
		forms.add(form);
		
		form = new Form();
		form.setText("Τη νέα συνεδρίαση του ΔΣ της Τράπεζας Πειραιώς αναμένει η διοίκηση της Ελληνικής Βιομηχανίας Ζάχαρης καθώς το επιχειρησιακό πλάνο που κατέθεσαν δεν πήρε έγκριση");
		formDao.persistOrMerge(form);
		forms.add(form);
		
		return forms;
	}
	
	private List<Chain> insertChains(List<Form> forms){
		Chain chain = new Chain();
		List<Form> sampleForm = new ArrayList<Form>();
		sampleForm.add(forms.get(0));
		sampleForm.add(forms.get(1));
		sampleForm.add(forms.get(3));
		chain.setForms(sampleForm);
		chains.add(chain);

		chain = new Chain();
		sampleForm = new ArrayList<Form>();
		sampleForm.add(forms.get(2));
		sampleForm.add(forms.get(3));
		sampleForm.add(forms.get(4));
		chain.setForms(sampleForm);
		chains.add(chain);
		
		chain = new Chain();
		sampleForm = new ArrayList<Form>();
		sampleForm.add(forms.get(4));
		sampleForm.add(forms.get(0));
		chain.setForms(sampleForm);
		chains.add(chain);
		
		return chains;
		
	}
	
	private List<Role> insertRoles() {
	
		List<Role> roles = new ArrayList<Role>();
		
		Role role = new Role();
		role.setName("Administrator");
		roleDao.persistOrMerge(role);
		roles.add(role);
		
		role = new Role();
		role.setName("Editor");
		roleDao.persist(role);
		roles.add(role);
		
		return roles;
	}
	
	private List<User> insertUsers(List<Role> roles) {
		
		Role role = roles.get(0);
		List<User> users = new ArrayList<User>();
		
		User user = new User();
		user.setUsername("npapadopoulos");
		user.setEmail("npapadopoulos@24media.gr");
		user.setEnabled(true);
		user.setRole(role);
		userDao.persistOrMerge(user);
		users.add(user);
		
		user = new User();
		user.setUsername("asoule");
		user.setEmail("asoule@24media.gr");
		user.setEnabled(false);
		user.setRole(role);
		userDao.persistOrMerge(user);
		users.add(user);
				
		return users;
	}
}
