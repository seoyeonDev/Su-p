package com.example.studyproject.studylogs;


import com.example.studyproject.studygroup.StudyGroupController;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/studylogs")
public class StudyLogsController {

    @Autowired
    private StudyLogsService studyLogsService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(StudyGroupController.class);


    // 스터디 로그 상세 조회
    @GetMapping("/studylogsdetail/{post_id}")
    public Map<String, Object> getMethodName(@PathVariable String post_id) {
    	
    	Map<String, Object> map = new HashMap<>();
    	
    	StudyLogs vo = studyLogsService.selectStudyLogs(post_id);
    	map.put("vo", vo);
    	
        return map;
    }
    


    // 결과물 신규추가
    @PostMapping("/insert")
    public void insertLogs(@RequestBody StudyLogs vo){
        studyLogsService.insertLogs(vo);
    }

    @GetMapping("/selectList")
    public ArrayList<StudyLogs> selectList(){
        return studyLogsService.selectList();
    }



}
