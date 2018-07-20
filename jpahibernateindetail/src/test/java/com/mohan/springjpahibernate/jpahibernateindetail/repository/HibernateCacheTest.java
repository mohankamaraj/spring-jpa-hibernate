package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mohan.springjpahibernate.jpahibernateindetail.JpahibernateindetailApplication;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JpahibernateindetailApplication.class)
public class HibernateCacheTest {
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseSpringDataRespoistory respoistory;
	
	@Test
	// Since there is no Transaction, two select queries executed in the background
	// So no first level caching here
	public void findByIdTwiceNonTransactional(){
		Optional<Course> course = respoistory.findById(10001L);
		logger.info("First Course {}", course.get());
		
		Optional<Course> course1 = respoistory.findById(10001L);
		logger.info("First Course again {}", course1.get());
	}
	
	@Test
	@Transactional
	// Since there is Transaction, only one select query executed in the background
	// So first level caching is working as expected with in the transaction
	public void findByIdTwice(){
		Optional<Course> course = respoistory.findById(10001L);
		logger.info("First Course {}", course.get());
		
		Optional<Course> course1 = respoistory.findById(10001L);
		logger.info("First Course again {}", course1.get());
	}
}
