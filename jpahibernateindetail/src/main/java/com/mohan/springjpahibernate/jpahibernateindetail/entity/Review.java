package com.mohan.springjpahibernate.jpahibernateindetail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Review {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	@Enumerated(value=EnumType.STRING)
	//@Enumerated(value=EnumType.ORDINAL)
	private ReviewRating rating;

	@Column
	private String description;
	
	@ManyToOne
	private Course course; 
	
	public Review(){
		
	}
	
	public Review(ReviewRating rating, String description){
		this.description = description;
		this.rating = rating;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ReviewRating getRating() {
		return rating;
	}
	public void setRating(ReviewRating rating) {
		this.rating = rating;
	}
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return String.format("Review[%s %s]", rating, description);
	}
	
}
