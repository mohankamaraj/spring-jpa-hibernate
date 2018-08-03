package com.mohan.springjpahibernate.jpahibernateindetail.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Student {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@OneToOne(fetch=FetchType.LAZY,  cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	private	Passport passport;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="STUDENT_COURSE",
		joinColumns = @JoinColumn(name="STUDENT_ID"),
		inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
	private List<Course> courses  = new ArrayList<>();
	
	@Embedded
	private Address address;
	
	public Student(){
		
	}
	
	public Student(String name){
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void addCourse(Course course) {
		this.courses.add(course);
	}
	
	public void removeCourse(Course course) {
		this.courses.remove(course);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return String.format("Student[%s]", name);
	}
	
}
