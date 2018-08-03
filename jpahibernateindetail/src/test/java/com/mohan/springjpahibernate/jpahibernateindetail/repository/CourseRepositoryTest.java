package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

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
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JpahibernateindetailApplication.class)
public class CourseRepositoryTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	CourseRepository repository;
	
	@Autowired
	EntityManager em;
	
	@Test
	public void testFindById(){
		Course course = repository.findById(10001L);
		assertEquals("JPA in 50 Steps", course.getName());
	}
	
	@Test
	@DirtiesContext
	public void testDeleteById(){
		repository.deleteById(10001L);
		assertNull(repository.findById(10001L));
	}
	
	@Test
	@DirtiesContext
	public void testSave(){
		Course course = repository.findById(10001L);
		assertEquals("JPA in 50 Steps", course.getName());
		course.setName("JPA in 50 Steps - updated");
		repository.save(course);
		
		course = repository.findById(10001L);
		assertEquals("JPA in 50 Steps - updated", course.getName());
	}
	
	//@Test
	@DirtiesContext
	@Transactional
	public void retriveReviewsForCourse(){
		//Course course = repository.findById(10001L);
		
		Query query = em.createQuery("from Course course", Course.class);
		List<Course> courses =  query.getResultList();	
		for(Course course : courses){
			logger.info("course -> {}", course);
			logger.info("course.reviews -> {}", course.getReviews());
		}
		
		
	}
	
	//@Test
	@DirtiesContext
	@Transactional
	@Commit
	public void removeCourse(){
		Course course = repository.findById(10001L);
		logger.info("course -> {}", course);
		logger.info("course.reviews -> {}", course.getReviews());
		em.remove(course);
	
	}
	
	//@Test
	@DirtiesContext
	@Transactional
	@Commit
	public void retriveReviewsForParticularCourse(){
		Course course = repository.findById(10001L);
		logger.info("course -> {}", course);
		logger.info("course.reviews -> {}", course.getReviews());
	}
	
	@Transactional
	//@Test
	@Commit
	@DirtiesContext
	public void fetchCourseDetailsOfTheStudent(){
		
		Course course = repository.findById(10001L);
		logger.info("course -> {}", course);
		logger.info("students -> {}", course.getStudents());
		
	}
}
