package com.example.studyproject.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
	
	// sql mapper의 id로 지정해줄 이름으로 메서드 생성
	
	// 회원가입
	public void insertMember(Member vo);
	
	// 로그인
	public Member loginMember(Member vo);
	
	// 실패횟수 업데이트
	public void updateFailNum(Member vo);
	
	// 잠금여부 업데이트
	public void updateLockYn(Member vo);
	
	// 유저검색
	public Member getMemberById(String user_id);

	// 아이디찾기
	public String findId(String name, String email);

	// 비밀번호찾기
	public String resetPwd();
	
}
