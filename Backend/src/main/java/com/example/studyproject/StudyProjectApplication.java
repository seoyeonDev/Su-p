package com.example.studyproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.studyproject")
public class StudyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyProjectApplication.class, args);
	}

}
