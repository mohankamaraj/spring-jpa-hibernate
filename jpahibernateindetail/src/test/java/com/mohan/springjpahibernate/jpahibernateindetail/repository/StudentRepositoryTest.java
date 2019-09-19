package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import org.hibernate.TransientPropertyValueException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.mohan.springjpahibernate.jpahibernateindetail.JpahibernateindetailApplication;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Address;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Passport;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JpahibernateindetailApplication.class)
public class StudentRepositoryTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	StudentRepository repository;
	
	@Autowired
	EntityManager em;
	
	//@Test
	@Commit
	@DirtiesContext
	public void testFindById(){
		Student student = repository.findById(20001L);
		assertEquals("Ranga", student.getName());
	}
	
	//@Test
	@Transactional
	public void retriveStudentAndPassportDetails(){
		Student student = repository.findById(20001L);
		logger.info("student -> {}", student);
		logger.info("passport -> {}", student.getPassport());
	}
	
	//@Test
	@Transactional
	public void retrivePassportAndAssociatedStudent(){
		Passport passport = em.find(Passport.class, 40001L);
		logger.info("passport -> {}", passport);
		logger.info("student -> {}", passport.getStudent());
	}
	
	
	@Transactional
	//@Test (expected = TransientPropertyValueException.class)
	@Commit
	@DirtiesContext
	// Make Sure no casecade operation configured for passport inside student entity
	// saving student will not persist passport. it will throw TransientPropertyValueException
	//@OneToOne(fetch=FetchType.LAZY)
	public void playWithStudentWithPassportSimpleAndNoCascade(){
		Student student = new Student("testing student");
		Passport passport = new Passport("testing passport");
		student.setPassport(passport);
		em.persist(student);
		logger.info("student passport -? {}", student.getPassport());
	}
	
	@Transactional
	//@Test
	@Commit
	@DirtiesContext
	// Make Sure no casecade operation configured for passport inside student entity
	// saving student will not persist passport, so you have to manually persist passport first
	//@OneToOne(fetch=FetchType.LAZY)
	public void playWithStudentWithPassportPersistBothAndNoCascade(){
		Student student = new Student("testing student");
		Passport passport = new Passport("testing passport");
		student.setPassport(passport);
		em.persist(passport);
		em.persist(student);
		
		student = em.find(Student.class, student.getId());
		logger.info("student passport -? {}", student.getPassport());
	}
	
	@Transactional
	//@Test
	@Commit
	@DirtiesContext
	// Make Sure casecade type persist configured for passport inside student entity
	// saving student will make sure, it will persist the passport first and then save by itself
	//@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	public void playWithStudentWithPassportSimpleWithCascade(){
		Student student = new Student("testing student");
		Passport passport = new Passport("testing passport");
		student.setPassport(passport);
		em.persist(student);
		
		student = em.find(Student.class, student.getId());
		logger.info("student passport -? {}", student.getPassport());
		assertNotNull(student.getPassport().getId());
	}
	
	//@Test
	@Commit
	@DirtiesContext
	@Transactional
	// Trying to delete the student object with casecade = null, passport object will still be available
	// only student object getting deleted
	// //@OneToOne(fetch=FetchType.LAZY)
	public void testDeleteById(){
		repository.deleteById(20001L);
		assertNull(repository.findById(20001L));
		em.flush();
		Passport passport = em.find(Passport.class, 40001L);
		logger.info("passport details -> {}", passport);
		assertNotNull(passport.getId());
	}
	
	//@Test
	@Commit
	@DirtiesContext
	@Transactional
	// Trying to delete the student object with casecade = remove, passport object will also be deleted
	// make sure cascade type is set as REMOVE
	// //@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	public void testDeleteByIdWithRemoveCascadeOn(){
		repository.deleteById(20001L);
		assertNull(repository.findById(20001L));
		em.flush();
		Passport passport = em.find(Passport.class, 40001L);
		logger.info("passport details -> {}", passport);
		assertNotNull(passport.getId());
	}
	
	//@Test
	@Commit
	@DirtiesContext
	@Transactional
	// make sure no orphanRemoval=true
	// Passport object will be still hanging arround
	//@OneToOne(fetch=FetchType.LAZY)
	
	public void testSetNullPassportByReplacingExistingPassportOrNullWithOrphanOff(){
		Student student = repository.findById(20001L);
		Passport passport = student.getPassport();
		student.setPassport(null);
		em.flush();
		passport = em.find(Passport.class, passport.getId());
		logger.info("passport details -> {}", em.find(Passport.class, passport.getId()));
		assertNotNull(passport.getId());
	}
	
	//@Test()
	@Commit
	@DirtiesContext
	@Transactional
	// make sure you have orphanRemoval=true
	// all un mapped child obejcts will be deleted from passport side
	//@OneToOne(fetch=FetchType.LAZY, orphanRemoval=true)
	public void testSetNullPassportByReplacingExistingPassportOrNullWithOrphanOn(){
		Student student = repository.findById(20001L);
		Passport passport = student.getPassport();
		student.setPassport(null);
		em.flush();
		passport = em.find(Passport.class, passport.getId());
		assertNull(passport);
	}
	
	//@Test
	@Commit
	@DirtiesContext
	@Transactional
	// Trying to delete the student object with casecade = null, passport object will still be available
	// only student object getting deleted
	// //@OneToOne(fetch=FetchType.LAZY)
	public void testDeleteByIdWithOrphanOn(){
		repository.deleteById(20001L);
		assertNull(repository.findById(20001L));
		em.flush();
		Passport passport = em.find(Passport.class, 40001L);
		assertNull(passport);
	}
	
	
	@Transactional
	//@Test
	@Commit
	@DirtiesContext
	// trying to add passport object by passing the new student object
	// No persist casecade or anything
	// Expectation student not saved. getting TransientPropertyValueException as expected
	//@OneToOne(fetch=FetchType.LAZY)
	public void addNewPassportwithStudentNoCascade(){
		try{
			Student student = new Student("testing student");
			Passport passport = new Passport("testing passport");
			passport.setStudent(student);
			em.persist(passport);
			//em.persist(student);
			
			/*student = em.find(Student.class, student.getId());
			logger.info("student passport -? {}", student.getPassport());*/
			
			passport = em.find(Passport.class, passport.getId());
			logger.info("student passport1 -? {}", passport.getStudent().getId());
		}catch(Exception exp){
			System.out.println(exp);
		}
		
	}
	
	@Transactional
	@Test
	@Commit
	@DirtiesContext
	// below example will work fine
	//@OneToOne(fetch=FetchType.LAZY)
	public void addNewPassportwithStudentNoCascadePersistBoth(){
		try{
			Student student = new Student("testing student");
			Passport passport = new Passport("testing passport");
			passport.setStudent(student);
			em.persist(passport);
			em.persist(student);
			
			/*student = em.find(Student.class, student.getId());
			logger.info("student passport -? {}", student.getPassport());*/
			
			passport = em.find(Passport.class, passport.getId());
			logger.info("student passport1 -? {}", passport.getStudent().getId());
		}catch(Exception exp){
			System.out.println(exp);
		}
		
	}
	
	@Transactional
	//@Test
	@Commit
	@DirtiesContext
	// trying to add passport object by passing the new student object
	// Have persist cascade or anything in passport side
	// Expectation student should be saved.
	//@OneToOne(fetch=FetchType.LAZY, mappedBy="passport", cascade=CascadeType.PERSIST)
	public void addNewPassportwithStudentWithCascadePersist(){
		Student student = new Student("testing student");
		Passport passport = new Passport("testing passport");
		passport.setStudent(student);
		em.persist(passport);
		//em.persist(student);
		
		/*student = em.find(Student.class, student.getId());
		logger.info("student passport -? {}", student.getPassport());*/
		
		passport = em.find(Passport.class, passport.getId());
		logger.info("student passport1 -? {}", passport.getStudent().getId());
		
	}
	
	//@Test
	@Commit
	@DirtiesContext
	@Transactional
	// Trying to delete the passport object with casecade not remove 
	// //@OneToOne(fetch=FetchType.LAZY)
	// Scenario1: When no casecade on the student side as well, it wont allow you to delete since the foreign key reference is avaialble
	// Scenario2: We can make dereference the student from the passport by student.setpaasport(null), em.merge(student), finally you can delete the passport
	// Scenario3: By having cascade=remove or orphan rmeoval on, your student object will also be deleted
	// Scenario4: By having cascade=remove or orphan rmeoval on, and student also having cascade remove for the passport. nothing happens
	//https://stackoverflow.com/a/43537393/5036740 (Thiis is called circular dependency)
	// one more related reference to above https://stackoverflow.com/questions/16898085/jpa-hibernate-remove-entity-sometimes-not-working
	public void testPassportDeleteById(){
		Passport passport = em.find(Passport.class, 40001L);
		Student student = passport.getStudent();
		logger.info("student -? {}", student);
		student.setPassport(null);
		em.merge(student);
		em.flush();
		em.remove(passport);
		em.flush();
		assertNotNull(passport);
		
		//assertNotNull(passport.getId());
	}
	
	@Transactional
	//@Test
	@Commit
	@DirtiesContext
	public void fetchCourseDetailsOfTheStudent(){
		
		Student student = repository.findById(20001L);
		logger.info("student -> {}", student);
		logger.info("courses -> {}", student.getCourses());
		
	}
	
	
	//@Test
	public void addStudentWithDetails(){
		Student student = new Student("Mohan");
		Address address = new Address();
		address.setLine1("line1");
		address.setLine2("line2");
		address.setLine3("line3");
		student.setAddress(address);
		repository.saveStudent(student);

		Student student1 = repository.findById(student.getId());
		logger.info("student -> {}", student1);
		logger.info("Address -> {}", student1.getAddress());
		
	}
	
	
}
