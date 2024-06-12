package com.example.studyproject.studylogs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class StudyLogsService {
    // log4j2 로그 찍기
    public static final Logger LOGGER = LogManager.getLogger(StudyLogsService.class);

    private final StudyLogsDao studyLogsDao;

    public StudyLogsService(StudyLogsDao studyLogsDao) {
        this.studyLogsDao = studyLogsDao;
    }


    // 결과물 추가
    public void insertLogs(StudyLogs vo) {
        studyLogsDao.insertLogs(vo);
    }

    public ArrayList<StudyLogs> selectList(){
        return studyLogsDao.selectList();

    }

}
