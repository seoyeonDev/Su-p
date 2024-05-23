package com.example.studyproject.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
	
	// sql mapper의 id로 지정해줄 이름으로 메서드 생성
	
	// 회원가입
	void insertMember(Member vo);
	
	// 로그인
	Member loginMember(Member vo);
	
	// 수정
	void updateMember(Member vo);
	
	// 실패횟수 업데이트
	void updateFailNum(Member vo);
	
	// 잠금여부 업데이트
	void updateLockYn(Member vo);
	
	// 유저검색
	Member getMemberById(String user_id);

	// 아이디찾기
	String findId(String name, String email);

	// 비밀번호찾기
	public int chkPwd(String user_id, String name, String email);
	public void changePwd(Member vo);
	
	String resetPwd();

	// 회원 탈퇴
	int deleteMember(String user_id);
}
