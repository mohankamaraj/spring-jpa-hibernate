package com.mohan.springjpahibernate.simplespringbootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SimplespringbootappApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(SimplespringbootappApplication.class, args);
		/*for(String name : appContext.getBeanDefinitionNames()){
			System.out.println(name);
		}*/
	}
}
