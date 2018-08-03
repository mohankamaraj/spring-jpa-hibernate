package com.mohan.springjpahibernate.jdbcworldtojpa;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mohan.springjpahibernate.jdbcworldtojpa.entity.Person;
import com.mohan.springjpahibernate.jdbcworldtojpa.jdbc.PersonJdbcDAO;
import com.mohan.springjpahibernate.jdbcworldtojpa.jpa.PersonJpaRepository;

@SpringBootApplication
public class SpringJpaDemoApplication implements CommandLineRunner{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PersonJpaRepository dao;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("All users -> {}",dao.findAll());
		logger.info("User id 10001 -> {}",dao.findById(10001));
		
		logger.info("Inserting 10004 -> {}", 
				dao.insert(new Person(10004, "Tara", "Berlin", new Date())));
		
		logger.info("Update 10003 -> {}", 
				dao.update(new Person(10003, "Pieter", "Utrecht", new Date())));
		
		dao.deleteById(10002);
	}
}
