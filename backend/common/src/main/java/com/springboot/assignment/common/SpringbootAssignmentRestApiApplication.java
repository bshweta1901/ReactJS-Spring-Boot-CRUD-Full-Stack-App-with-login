package com.springboot.assignment.common;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication(scanBasePackages = "com.springboot.assignment.*")
@EnableJpaRepositories(basePackages = "com.springboot.assignment.*")
@ComponentScan(basePackages = "com.springboot.assignment.*")
@EntityScan(basePackages = "com.springboot.assignment")
public class SpringbootAssignmentRestApiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAssignmentRestApiApplication.class, args);
	}

}
