package com.example.studyproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.studyproject")
public class StudyProjectApplication {

	public static void main(String[] args) {
		// 운영체제 감지
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			System.setProperty("spring.profiles.active", "windows");
		} else if (os.contains("mac")) {
			System.setProperty("spring.profiles.active", "mac");
		} else {
			System.setProperty("spring.profiles.active", "default"); // 기타 운영체제
		}

		SpringApplication.run(StudyProjectApplication.class, args);
	}

}
