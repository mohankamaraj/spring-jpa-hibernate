package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.mohan.springjpahibernate.jpahibernateindetail.JpahibernateindetailApplication;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Course;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JpahibernateindetailApplication.class)
public class JPQLTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	EntityManager em;
	
	@Test
	public void course_all(){
		
		//Example of non typed query
		// You will have get the following warning. The warning will show up even if you do explicit casting
		// Type safety: The expression of type List needs unchecked conversion to conform to List<Course>
		// Reference: https://stackoverflow.com/questions/16475436/warning-unchecked-unchecked-conversion
		/*Query q = em.createQuery("select c from Course c");
		List<Course> resultList1 = q.getResultList();*/
		
		// em.createQuery("select c from Course c", Course.class) gives the typed query
		//https://stackoverflow.com/questions/33236664/difference-between-query-native-query-named-query-and-typed-query
		
		List<Course> resultList = em.createQuery("select c from Course c", Course.class).getResultList();
		
		logger.info("results -> {}", resultList);
		assertEquals(3, resultList.size());
		
		TypedQuery<Course> tyQuery = em.createQuery("select c from Course c where name like '%100%'", Course.class);
		logger.info("results filtered -> {}", tyQuery.getResultList());
	}
	
	@Test
	public void course_contains_100(){
		List<Course> resultList = em.createQuery("select c from Course c where name like '%100%'", Course.class).getResultList();
		logger.info("results filtered -> {}", resultList);
		assertEquals(1, resultList.size());
	}
	
	@Test
	public void testJPQLNamedQuery(){
		TypedQuery<Course> tyQuery = em.createNamedQuery("query_get_all_courses", Course.class);
		logger.info("named query results -> {}", tyQuery.getResultList());
		
		tyQuery = em.createNamedQuery("query_get_all_courses_namecontains", Course.class).setParameter("code", "%100%");
		List<Course> resultList = tyQuery.getResultList();
		logger.info("named query results filtered -> {}", resultList);
		assertEquals(1, resultList.size());
	}
	
	@Test
	// My SQL : select * from course where course.id not in(select course_id from student_course, student where  student_course.student_id = student.id);
	// My SQL : select c.name, c.id from course c left join student_course sc on c.id = sc.course_id where sc.course_id is null;
	public void courses_without_students(){
		List<Course> resultList = em.createQuery("select c from Course c where c.students is empty", Course.class).getResultList();
		logger.info("courses with out students -> {}", resultList);
		assertEquals(1, resultList.size());
	}
	
	@Test
	// My SQL : select * from course where course.id in(
	//				select course_id from
    //						(select course_id, count(course_id) as count from  student_course,  student where student_course.student_id = student.id group by course_id) 
	//				where count >=2
	//			);
	
	// select c.id from course c left join student_course sc on c.id = sc.course_id group by c.id having count(sc.student_id)>=2;
	public void courses_with_minimum_two_students(){
		List<Course> resultList = em.createQuery("select c from Course c where size(c.students)>=2", Course.class).getResultList();
		logger.info("courses with minimum 2 students -> {}", resultList);
		assertEquals(1, resultList.size());
	}
	
	@Test
	public void courses_order_by_number_of_students(){
		List<Course> resultList = em.createQuery("select c from Course c order by size(c.students) desc", Course.class).getResultList();
		logger.info("courses order by students -> {}", resultList);
		assertEquals(3, resultList.size());
	}
	
	
	@Test
	public void students_with_specific_passport_pattern(){
		List<Student> resultList = em.createQuery("select s from Student s where  s.passport.number like '%F123%'", Student.class).getResultList();
		logger.info("students in particular passport number pattern -> {}", resultList);
		assertEquals(1, resultList.size());
	}
	
	
	@Test
	public void course_student_normal_or_inner_join(){
		Query query = em.createQuery("select c,s from Course c JOIN c.students s");
		List<Object []> resultList = query.getResultList();
		logger.info("Results Size -> {}", resultList.size());
		for(Object[] results : resultList){
			logger.info("join -> Course{} Student{}", results[0], results[1]);
		}
	}
	

	@Test
	public void leftjoin(){
		Query query = em.createQuery("select c,s from Course c LEFT JOIN c.students s");
		List<Object []> resultList = query.getResultList();
		logger.info("Results Size -> {}", resultList.size());
		for(Object[] results : resultList){
			logger.info("left outer join -> Course{} Student{}", results[0], results[1]);
		}
	}
	
	@Test
	public void crossjoin(){
		Query query = em.createQuery("select c,s from Course c, Student s");
		List<Object []> resultList = query.getResultList();
		logger.info("Results Size -> {}", resultList.size());
		for(Object[] results : resultList){
			logger.info("cross join -> Course{} Student{}", results[0], results[1]);
		}
	}
	
	@Test
	@Transactional(isolation=Isolation.READ_COMMITTED)
	// N+1 issues
	public void why_to_use_fetch_join(){
		List<Course> resultList = em.createQuery("select c from Course c", Course.class).getResultList();
		logger.info("results -> {}", resultList);
		for(Course course : resultList){
			logger.info("why_to_use_fetch_join students ->{}", course.getStudents());
		}
	}
	
	@Test
	// Avoinding N+1 issues using fetch join
	public void using_fetch_join_to_avoid_n_plus_one_issue(){
		List<Course> resultList = em.createQuery("select c from Course c JOIN FETCH c.students", Course.class).getResultList();
		logger.info("results -> {}", resultList);
		for(Course course : resultList){
			logger.info("using_fetch_join_to_avoid_n_plus_one_issue students ->{}", course.getStudents());
		}
	}
	
}
