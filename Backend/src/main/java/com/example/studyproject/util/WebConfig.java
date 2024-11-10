package com.example.studyproject.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:3000","chrome-extension://coohjcphdfgbiolnekdpbcijmhambjff")
                // 백엔드, 프론트엔드, 크롬 확장프로그램
                .allowedMethods("GET","POST","DELETE")
                .allowCredentials(true)
                .maxAge(3000);

    }
}
