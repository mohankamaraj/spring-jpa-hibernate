package com.mohan.springjpahibernate.jpahibernateindetail.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

//@Entity
@MappedSuperclass
//@Inheritance(strategy=InheritanceType.JOINED)
//@DiscriminatorColumn(name="EmployeeType")
public abstract class Employee {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String name;

	protected Employee(){
		
	}
	
	protected Employee(String name){
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
	
	@Override
	public String toString() {
		return String.format("Employee[%s]", name);
	}
}
