package com.example.studyproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

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


    @PostMapping("/test/saveData")
    public String saveData(@RequestBody String inputValue) {
        // 입력값 처리
        System.out.println("입력값: " + inputValue);

        // 필요한 작업 수행 후 응답 반환
        return "데이터 저장 완료";
    }
}
