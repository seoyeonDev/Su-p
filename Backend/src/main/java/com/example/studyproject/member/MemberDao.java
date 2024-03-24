package com.example.studyproject.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
	
	// sql mapper의 id로 지정해줄 이름으로 메서드 생성함
	Member getMemberById(String userId);
	
}
