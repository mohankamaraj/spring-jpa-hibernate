package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.mohan.springjpahibernate.jpahibernateindetail.JpahibernateindetailApplication;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JpahibernateindetailApplication.class)
public class CourseSpringRespositoryTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseSpringDataRespoistory respoistory;
	
	
	@Test
	public void findById(){
		Optional<Course> course = respoistory.findById(10001L);
		logger.info("course available", course.isPresent());
		logger.info("Course id {}", course.get().getId());
	}
	
	
	@Test
	public void saveAndUpdate(){
		Course course = new Course("Microservices in 100 Steps");
		respoistory.save(course);
		course.setName("Microservices in 100 Steps - Updated");
		respoistory.save(course);
		respoistory.delete(course);
	}
	
	@Test
	public void findAll(){
		List<Course> courses = respoistory.findAll();
		assertEquals(3, courses.size());
	}
	
	
	@Test
	public void findAllWithSort(){
		Sort sort = new Sort(Direction.DESC, "name");
		List<Course> courses = respoistory.findAll(sort);
		assertEquals("JPA in 50 Steps", courses.get(2).getName());
	}
	
	
	@Test
	public void pagination(){
		Sort sort = new Sort(Direction.DESC, "name");
		PageRequest request = PageRequest.of(0, 2, sort);
		Page<Course> courses = respoistory.findAll(request);
		assertEquals("Spring Boot in 100 Steps", courses.getContent().get(1).getName());
	}
	
	@Test
	public void findUsingName(){
		List<Course> courses = respoistory.findByName("Spring Boot in 100 Steps");
		assertEquals(1, courses.size());
		assertEquals("Spring Boot in 100 Steps", courses.get(0).getName());
	}
	
	@Test
	public void countUsingName(){
		assertEquals(Long.valueOf(1L), respoistory.countByName("Spring Boot in 100 Steps"));
	}
	
	@Test
	public void testUsingfindByNameAndId(){
		assertEquals("JPA in 50 Steps", respoistory.findByNameAndId("JPA in 50 Steps", 10001L).getName());
		//assertEquals(null, respoistory.findByNameAndId("JPA in 50 Steps not avaialble", 10001L).getName());
	}
	
	@Test
	public void testUsingfindByNameOrderByIdDesc(){
		List<Course> courses = respoistory.findByNameOrderByIdDesc("JPA in 50 Steps");
		assertEquals("JPA in 50 Steps", courses.get(0).getName());
	}
	
	@Test
	public void testUsingDeleteByName(){
		Course course = new Course("Microservices in 100 Steps");
		respoistory.save(course);
		List<Course> courses = respoistory.findByNameOrderByIdDesc("Microservices in 100 Steps");
		assertEquals("Microservices in 100 Steps", courses.get(0).getName());
		long count  = respoistory.deleteByName("Microservices in 100 Steps");
		assertEquals(1, count);
		logger.info("course deleted -> {}", course.getId()+":"+course.getName());
		courses = respoistory.findByNameOrderByIdDesc("Microservices in 100 Steps");
		assertEquals(0, courses.size());
	}
	
	@Test
	public void courseWith100StepsUsingCustomQuery(){
		List<Course> courses = respoistory.courseWith100Steps("%100 Steps%");
		assertEquals(1, courses.size());
		assertEquals("Spring Boot in 100 Steps", courses.get(0).getName());
	}
	
	@Test
	public void courseWith100StepsUsingNativeQuery(){
		List<Course> courses = respoistory.courseWith100StepsNativeQuery("%100 Steps%");
		assertEquals(1, courses.size());
		assertEquals("Spring Boot in 100 Steps", courses.get(0).getName());
	}
	
	@Test
	public void courseWith100StepsUsingNamedQuery(){
		List<Course> courses = respoistory.courseWith100StepsNamedQuery("%100 Steps%");
		assertEquals(1, courses.size());
		assertEquals("Spring Boot in 100 Steps", courses.get(0).getName());
	}
	

}
