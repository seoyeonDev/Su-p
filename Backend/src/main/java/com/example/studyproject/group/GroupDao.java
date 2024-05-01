package com.example.studyproject.group;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface GroupDao {

	// 그룹 추가
	public void createGroup(Group vo);
	
}
