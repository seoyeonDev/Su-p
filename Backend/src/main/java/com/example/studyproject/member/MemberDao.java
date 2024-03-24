package com.example.studyproject.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

	Member getMemberById(String userId);
	
}
