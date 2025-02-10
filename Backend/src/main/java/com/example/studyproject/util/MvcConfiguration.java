package com.example.studyproject.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /upload/** 경로를 src/main/resources/uploads 폴더로 매핑
        // /upload/** 로 시작하는 요청은 uploads/ 폴더에서 파일을 찾게 된다.
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/uploads/");
    }

}

