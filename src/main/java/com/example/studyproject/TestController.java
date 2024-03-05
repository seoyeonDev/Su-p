package com.example.studyproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {
    @GetMapping("/")
    public String test(){
        return "First Controller11";
    }

    @GetMapping("/test")
    public List<String> hello(){
        return Arrays.asList("하이1", "하이2");
    }
}
