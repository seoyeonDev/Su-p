package com.example.studyproject.studylogs;


import com.example.studyproject.studygroup.StudyGroupController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/studylogs")
public class StudyLogsController {

    @Autowired
    private StudyLogsService studyLogsService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(StudyLogsController.class);

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
