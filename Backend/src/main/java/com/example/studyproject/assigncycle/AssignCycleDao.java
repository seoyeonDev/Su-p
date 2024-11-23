package com.example.studyproject.assigncycle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssignCycleDao {

	// 그룹의 화차 정보 생성
	void insertAssignCycle(List<AssignCycle> list);
	
	// 그룹 회차 정보 삭제(그룹 수정할 때)
	void deleteAssignCycle(String group_id);

	// 사용자별 모든 스터디그룹의 총 출석 개수를 구하기
	int getAssignCycleCountMultiple(List<String> groupIds);

	// 사용자별 특정 스터디그룹의 총 출석 개수를 구하기
	int getAssignCycleCount(String group_id);

	// 사용자별 전체 스터디그룹의 총 출석 개수를 구하기
	int getAssignCycleCountByUserId(String user_id);

}
