package com.example.studyproject.studylogs;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface StudyLogsDao {


	// 스터디로그 상세 조회
	StudyLogs selectStudyLogs(String post_id);
	
  // 결과물 신규추가
  void insertLogs(StudyLogs vo);

  // 스터디로그 리스트
  ArrayList<StudyLogs> selectList();

}
