package com.example.studyproject.studylogs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class StudyLogsService {
    // log4j2 로그 찍기
    public static final Logger LOGGER = LogManager.getLogger(StudyLogsService.class);

    private final StudyLogsDao studyLogsDao;

    public StudyLogsService(StudyLogsDao studyLogsDao) {
        this.studyLogsDao = studyLogsDao;
    }

    // 스터디로그 상세 조회
    public StudyLogs selectStudyLogs(String post_id) {
    	return studyLogsDao.selectStudyLogs(post_id);
    }
}
