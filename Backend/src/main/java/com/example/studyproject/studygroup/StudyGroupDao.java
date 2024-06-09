package com.example.studyproject.studygroup;

import java.util.List;

/**
 * @Class Name : StudyGroupDao.java
 * @Description : StudyGroupDao DAO
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.01     봉선호        최초 생성
 *
 */

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StudyGroupDao {

	// 그룹ID MAX값 찾기
	String selectMaxGroupId(String yrmd);
	
	// 그룹 추가
	void createGroup(StudyGroup vo);
	
  // 그룹 수정
	void updateGroup(StudyGroup vo);
	
  	// 그룹 목록
	List<?> selectListStudyGroup();
  
	// 그룹 삭제
	void deleteGroup(StudyGroup vo);

	// 그룹 상태 변경
	void changeStatus();
	
}
