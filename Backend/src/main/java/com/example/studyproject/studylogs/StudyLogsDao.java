package com.example.studyproject.studylogs;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface StudyLogsDao {

    // 결과물 신규추가
    void insertLogs(StudyLogs vo);

    // 결과물 신규추가 (post_id반환)
    void insertStudyLogs(StudyLogs vo);

    // 결과물 업데이트
    void updateStudyLogs(StudyLogs vo);

    // 회원 탈퇴 : 알수 없음으로 변경
    void anonymizeUserId(String user_id);

	  // 스터디로그 상세 조회
	  StudyLogs selectStudyLogs(String post_id);

    // 스터디로그 리스트
    ArrayList<StudyLogs> selectList();

    // 스터디로그 아이디 검사
    Long getNextPostIdIfPrefixExists(String group_id);

    // 그룹별, 사용자별 studylog 전체 목록
    ArrayList<StudyLogs> selectStudyLogsListByGroup(String group_id, String user_id);

    // 스터디 메인 글 LIMIT 5
    ArrayList<StudyLogs> studyMainListLimit5(String group_id);
}
