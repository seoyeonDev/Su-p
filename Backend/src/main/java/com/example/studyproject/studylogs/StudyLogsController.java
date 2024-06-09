package com.example.studyproject.studylogs;


import com.example.studyproject.studygroup.StudyGroupController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studylogs")
public class StudyLogsController {

    @Autowired
    private StudyLogsService studyLogsService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(StudyGroupController.class);





}