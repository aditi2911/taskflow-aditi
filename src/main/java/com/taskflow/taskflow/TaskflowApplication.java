package com.taskflow.taskflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.taskflow")
@EnableJpaRepositories(basePackages = "com.taskflow")
@EntityScan(basePackages = "com.taskflow")
public class TaskflowApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskflowApplication.class, args);
	}
}