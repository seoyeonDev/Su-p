package com.example.studyproject.studylogs;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface StudyLogsDao {

    // 결과물 신규추가
    void insertLogs(StudyLogs vo);


    ArrayList<StudyLogs> selectList();
}
