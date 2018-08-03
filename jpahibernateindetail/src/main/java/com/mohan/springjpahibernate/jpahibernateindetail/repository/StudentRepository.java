package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mohan.springjpahibernate.jpahibernateindetail.entity.Course;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Passport;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Student;

@Repository
@Transactional
public class StudentRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	EntityManager em;
	
	public Student findById(Long id){
		return em.find(Student.class, id);
	}
	
	public void deleteById(Long id){
		Student student = findById(id);
		em.remove(student);
	}
	
	public void saveStudent(Student student){
		Passport ppt = student.getPassport(); 
		if(ppt != null){
			if(ppt.getId() != null){
				ppt = em.merge(ppt);
				student.setPassport(ppt);
			}else{
				em.persist(ppt);
			}
		}
		if(student.getId()!= null){
			em.merge(student);
		}else{
			em.persist(student);
		}
	}
	
	public void saveStudentWithPassport(){
		Passport passport = new Passport("ER3221321");
		em.persist(passport);
		Student student = new Student("Student1");
		student.setPassport(passport);
		em.persist(student);
	}
	
	
	public void playWithStudent(){
		
		// With out CascadeType.Persistance, passport will not be saved
		Student student = new Student("testing student");
		Passport passport = new Passport("testing passport");
		student.setPassport(passport);
		em.persist(student);
		logger.info("student passport -? {}", student.getPassport());
		
		// If no casecade, you have to persist the object on your own
		/*Passport passport1 = new Passport("testing passport1");
		em.persist(passport1);
		
		Student student1 = new Student("testing student1");
		student1.setPassport(passport1);
		
		em.persist(student1);
		
		logger.info("student passport -? {}", student1.getPassport());*/
	}
	
	public void palyWithPassport(){
		/*Passport passport = em.find(Passport.class, 40001L);
		Student student = passport.getStudent();
		logger.info("student -? {}", student);
		em.remove(passport);
		
		Passport passport1 = new Passport("testing passport1");
		em.persist(passport1);
		em.flush();
		//em.remove(passport1);
*/
		/*em.flush();
		student = findById(student.getId());
		logger.info("student passport id -? {}", student.getPassport());*/
		
		Passport passport = em.find(Passport.class, 40001L);
		Student student = passport.getStudent();
		logger.info("student -? {}", student);
		student.setPassport(null);
		em.merge(student);
		em.remove(passport);
		em.flush();
	}
	
	public void deleteStudent(){
		Student student =em.find(Student.class, 20001L);
		logger.info("student -? {}", student);
		em.remove(student);
	}
	
	public void insertStudentAndCourse(Student student, Course course){
		em.persist(student);
		em.persist(course);
		//student.addCourse(course);
		course.addStudent(student);
		
	}
	
}
