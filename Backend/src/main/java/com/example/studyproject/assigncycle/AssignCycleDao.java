package com.example.studyproject.assigncycle;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssignCycleDao {

	// 그룹의 화차 정보 생성
	void insertAssignCycle(AssignCycle vo);
}
