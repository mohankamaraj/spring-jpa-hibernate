package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mohan.springjpahibernate.jpahibernateindetail.entity.Course;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Review;

@Repository
@Transactional
public class CourseRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	public Course findById(Long id){
		return em.find(Course.class, id);
	}
	
	public void deleteById(Long id){
		Course course = findById(id);
		em.remove(course);
	}
	
	public Course save(Course course){
		if(course.getId() == null){
			em.persist(course);
		}else{
			em.merge(course);
		}
		return course;
	}
	
	public void playWithEntityManager(){
		Course course = new Course("My Services");
		em.persist(course);
		course.setName("My Services -updated");
		
		Course course1 = new Course("My Services persist1");
		em.persist(course1);
		course1.setName("My Services persist1 -updated");
		
		Course course2 = new Course("My Services merge");
		em.merge(course2);
		course2.setName("My Services merge -updated");
		
		Course course3 = new Course("My Services merge2");
		Course course4 = em.merge(course3);
		course4.setName("My Services merge2 -updated");
		
		em.remove(course4);
		course4.setName("final");
		
		Course course5 = new Course("My Services5");
		em.persist(course5);
		course5.setName("My Services5 -updated");
		
		Course course6 = new Course("My Services persist6");
		em.persist(course6);
		course6.setName("My Services persist6 -updated");
		
		em.clear();
		
		Course course7 = findById(10001L);
		course7.setName(course7.getName()+" - updated for date time stamp");
		
	}
	
	public void addReviewForCourse(Long courseId, List<Review> reviews){
		Course course = findById(courseId);
		logger.info("course -> {}", course);
		logger.info("course.reviews -> {}", course.getReviews());
		
		for(Review review : reviews){
			review.setCourse(course);
			em.persist(review);
			course.addReview(review);
		}
	}
}
