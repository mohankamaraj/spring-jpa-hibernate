package com.mohan.springjpahibernate.jpahibernateindetail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mohan.springjpahibernate.jpahibernateindetail.entity.Course;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.FullTimeEmployee;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.PartTimeEmployee;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Passport;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Review;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Student;
import com.mohan.springjpahibernate.jpahibernateindetail.repository.CourseRepository;
import com.mohan.springjpahibernate.jpahibernateindetail.repository.EmployeeRepository;
import com.mohan.springjpahibernate.jpahibernateindetail.repository.PassportRepository;
import com.mohan.springjpahibernate.jpahibernateindetail.repository.StudentRepository;

@SpringBootApplication
public class JpahibernateindetailApplication implements CommandLineRunner{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	PassportRepository passportRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpahibernateindetailApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*Course course = courseRepository.findById(10001L);
		logger.info("Course 10001 -> {}", course);
		//courseRepository.deleteById(10001L);
		courseRepository.save(new Course("Micro services"));
		courseRepository.playWithEntityManager();
		
		studentRepository.saveStudentWithPassport();*/
		
		
		/*Review review1 = new Review("3.8", "Good");
		Review review2 = new Review("4.8", "Very Good");
		List<Review> reviews = Arrays.asList(review1, review2);
		courseRepository.addReviewForCourse(10002L, reviews);
		//courseRepository.deleteById(10002L);
		Course course = courseRepository.findById(10002L);
		course.setReviews(null);
		courseRepository.save(course);
		
		Student student = new Student("test student");
		Passport passport = new Passport("gtdf324325432");
		student.setPassport(passport);
		
		studentRepository.saveStudent(student);
		
		Student student1 = studentRepository.findById(4L);
		student1.setPassport(null);
		studentRepository.saveStudent(student1);*/
		
		/*Student student2 = new Student("test me from pssport");
		Passport pp1 = new Passport("test me from pssport");
		pp1.setStudent(student2);
		passportRepository.savePassport(pp1);*/
		
		
		//studentRepository.playWithStudent();
		
		//studentRepository.deleteById(20001L);
		
		//studentRepository.palyWithPassport();
		
		//studentRepository.insertStudentAndCourse(new Student("New Course"), new Course("My awesome course"));
		
		
		//employeeRepository.insert(new FullTimeEmployee("Jack", new BigDecimal("10000")));
		//employeeRepository.insert(new PartTimeEmployee("Jill", new BigDecimal("50")));
		
		//logger.info("All employees -> {}", employeeRepository.retriveAllEmployees());
		
		
		
	}
}
