package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mohan.springjpahibernate.jpahibernateindetail.JpahibernateindetailApplication;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Course;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Passport;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JpahibernateindetailApplication.class)
public class CriteriaQueryTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	EntityManager em;
	
	@Test
	public void all_course(){
		// 1. Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq =  builder.createQuery(Course.class);
		// 2. Define roots for tables which are involved in the query 
		Root<Course> cR = cq.from(Course.class);
		
		// 5. Build the typed query using the entity manager and criteria query
		TypedQuery<Course> query = em.createQuery(cq.select(cR));
		List<Course> courses = query.getResultList();
		logger.info("Courses {} "+courses);
		assertEquals(3, courses.size());
	}
	
	@Test
	public void all_course_having_100steps(){
		// 1. Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq =  builder.createQuery(Course.class);
		// 2. Define roots for tables which are involved in the query 
		Root<Course> cR = cq.from(Course.class);
		// 3. Define Predicates etc using Criteria Builder
		Predicate predicate = builder.like(cR.get("name"), "%100 Steps");
		// 4. Add Predicates etc to the Criteria Query
		cq.where(predicate);
		// 5. Build the typed query using the entity manager and criteria query
		TypedQuery<Course> query = em.createQuery(cq.select(cR));
		List<Course> courses = query.getResultList();
		logger.info("Courses {} "+courses);
		assertEquals(1, courses.size());
	}
	
	@Test
	public void get_passport_with_particulat_id_with_setpaaremeter(){
		// 1. Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Passport> cq =  builder.createQuery(Passport.class);
		// 2. Define roots for tables which are involved in the query 
		Root<Passport> cR = cq.from(Passport.class);
		ParameterExpression<Long> parameter = builder.parameter(Long.class);
		// 3. Define Predicates etc using Criteria Builder
		Predicate predicate = builder.equal(cR.get("id"), parameter);
		// 4. Add Predicates etc to the Criteria Query
		cq.where(predicate);
		// 5. Build the typed query using the entity manager and criteria query
		TypedQuery<Passport> query = em.createQuery(cq.select(cR));
		query.setParameter(parameter,40002L);
		Passport passport = query.getSingleResult();
		logger.info("Passport {} "+passport);
		assertEquals("F123456", passport.getNumber());
	}
	
	
	@Test
	public void all_courses_orderby(){
		// 1. Use Criteria Builder to create a Criteria Query returning the expected result object
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq =  builder.createQuery(Course.class);
		// 2. Define roots for tables which are involved in the query 
		Root<Course> cR = cq.from(Course.class);
		
		List<Order> orderList = new ArrayList<>();
		orderList.add(builder.desc(cR.get("name")));
		TypedQuery<Course> query = em.createQuery(cq.select(cR).orderBy(orderList));
		List<Course> courses = query.getResultList();
		logger.info("Courses {} "+courses);
		assertEquals("Spring in 50 Steps", courses.get(0).getName());
	}
	
	@Test
	public void all_courses_no_students(){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq =  builder.createQuery(Course.class);
		Root<Course> cR = cq.from(Course.class);
		Predicate  predicate = builder.isEmpty(cR.get("students"));
		cq.where(predicate);
		TypedQuery<Course> query = em.createQuery(cq.select(cR));
		List<Course> courses = query.getResultList();
		logger.info("Courses no students {} "+courses);
		assertEquals(1, courses.size());
	}
	
	@Test
	@Transactional
	// Whether it is lazy or eager fetch, hibernate will do N+1 fetches for child
	// To avoid it, we have to uses fetch join, it fetches all of them at single join query
	public void unique_courses_with_inner_join_students(){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq =  builder.createQuery(Course.class);
		Root<Course> cR = cq.from(Course.class);
		cR.join("students", JoinType.INNER);
		cq.distinct(true);
		TypedQuery<Course> query = em.createQuery(cq.select(cR));
		List<Course> courses = query.getResultList();
		logger.info("Courseswith join with students {} "+courses);
		assertEquals(2, courses.size());
		for(Course course : courses){
			logger.info("student without fetch join {}", course.getStudents());
		}
		
	}
	
	
	@Test
	// no need of @Transactional
	// Whether it is lazy or eager fetch, hibernate will do N+1 fetches for child
    // To avoid it, we have to uses fetch join, it fetches all of them at single join query
	public void unique_courses_with_inner_join_students_courses_at_one_join(){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq =  builder.createQuery(Course.class);
		Root<Course> cR = cq.from(Course.class);
		cR.join("students", JoinType.INNER);
		cR.fetch("students");
		cq.distinct(true);
		TypedQuery<Course> query = em.createQuery(cq.select(cR));
		List<Course> courses = query.getResultList();
		logger.info("Courseswith join with students {} "+courses);
		assertEquals(2, courses.size());
		for(Course course : courses){
			logger.info("student with fetch join {}", course.getStudents());
		}
	}
	
	
	
}
