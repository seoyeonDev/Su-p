package com.example.studyproject.studylogs;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface StudyLogsDao {

	// 스터디로그 상세 조회
	StudyLogs selectStudyLogs(String post_id);
	
}
