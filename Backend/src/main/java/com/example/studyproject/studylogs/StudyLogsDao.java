package com.example.studyproject.studylogs;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface StudyLogsDao {

    // 결과물 신규추가
    void insertLogs(StudyLogs vo);
}
