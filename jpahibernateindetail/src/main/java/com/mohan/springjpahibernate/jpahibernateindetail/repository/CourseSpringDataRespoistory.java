package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mohan.springjpahibernate.jpahibernateindetail.entity.Course;

@RepositoryRestResource(path="/courses")
@Transactional
public interface CourseSpringDataRespoistory extends JpaRepository<Course, Long>{
	List<Course> findByName(String name);
	Long countByName(String name);
	Course findByNameAndId(String name, Long id);
	List<Course> findByNameOrderByIdDesc(String name);
	Long deleteByName(String name);
	
	@Query("select c from Course c where name like :code")
	List<Course> courseWith100Steps(@Param("code") String code);
	
	@Query(nativeQuery=true,value= "select * from course where name like :code and is_deleted = false")
	List<Course> courseWith100StepsNativeQuery(@Param("code") String code);
	
	@Query(name="query_get_all_courses_namecontains")
	List<Course> courseWith100StepsNamedQuery(@Param("code") String code);
}
