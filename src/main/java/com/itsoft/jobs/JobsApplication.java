package com.itsoft.jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
@EnableWebMvc
public class JobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobsApplication.class, args);
	}

}
