package com.mohan.springjpahibernate.jpahibernateindetail.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mohan.springjpahibernate.jpahibernateindetail.entity.Passport;
import com.mohan.springjpahibernate.jpahibernateindetail.entity.Student;

@Repository
@Transactional
public class PassportRepository {
	
	@Autowired
	EntityManager em;
	
	public Passport findById(Long id){
		return em.find(Passport.class, id);
	}
	
	public void deleteById(Long id){
		Passport passport = findById(id);
		em.remove(passport);
	}
	
	public void savePassport(Passport passport){
		Student student = passport.getStudent(); 
		em.persist(passport);
	}
	
	
	
}
