package com.mohan.springjpahibernate.jpahibernateindetail.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({
    @NamedQuery(name="query_get_all_courses",
                query="select c from Course c"),
    @NamedQuery(name="query_get_all_courses_namecontains",
                query="select c from Course c where name like :code"),
}) 
@Cacheable
@SQLDelete(sql="update course set is_deleted=true where id=?")
@Where(clause="is_deleted = false")
public class Course {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy="course", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	private List<Review> reviews  = new ArrayList<>();
	
	@ManyToMany(mappedBy="courses", cascade=CascadeType.PERSIST)
	@JsonIgnore
	private List<Student> students  = new ArrayList<>();
	
	@UpdateTimestamp
	private LocalDateTime lastUpdatedDate;
	
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(nullable=false)
	private boolean isDeleted; 
	
	@PreRemove
	private void preRemove(){
		this.isDeleted = true;
	}
	
	public Course(){
		
	}
	
	public Course(String name){
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
	
	public List<Review> getReviews() {
		return reviews;
	}

	public void addReview(Review review) {
		this.reviews.add(review);
	}
	
	public void removeReview(Review review) {
		this.reviews.remove(review);
	}

	public List<Student> getStudents() {
		return students;
	}

	public void addStudent(Student student) {
		this.students.add(student);
	}
	
	public void removeStudent(Student student) {
		this.students.remove(student);
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return String.format("Course[%s]", name);
	}
}