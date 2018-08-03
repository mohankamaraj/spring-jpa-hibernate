package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mohan.springjpahibernate.jpahibernateindetail.entity.Employee;

@Repository
@Transactional
public class EmployeeRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	EntityManager em;
	
	public void insert(Employee employee){
		em.persist(employee);
	}
	
	public List<Employee> retriveAllEmployees(){
		return em.createQuery("select e from Employee e ", Employee.class).getResultList();
	}
	
}
