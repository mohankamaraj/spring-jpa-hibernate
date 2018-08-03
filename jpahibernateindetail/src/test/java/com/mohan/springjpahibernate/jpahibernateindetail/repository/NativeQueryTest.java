package com.mohan.springjpahibernate.jpahibernateindetail.repository;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.mohan.springjpahibernate.jpahibernateindetail.JpahibernateindetailApplication;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=JpahibernateindetailApplication.class)
public class NativeQueryTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	EntityManager em;
	
	@Test
	@Transactional
	public void testNativeQueryAllRowsUpdate(){
		Query query =  em.createNativeQuery("update course set last_updated_date=sysdate()", Course.class);
		int noOfRowsUpdated = query.executeUpdate();
		logger.info("noOfRowsUpdated -> {}", noOfRowsUpdated);
	}
	
	@Test
	public void testNativeQuery(){
		Query query =  em.createNativeQuery("select * from course where id=?", Course.class);
		query.setParameter(1, 1L);
		List<Course> resultList = query.getResultList();
		logger.info("results -> {}", resultList);
	}
	
	
	
	
}
