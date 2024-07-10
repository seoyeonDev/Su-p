package com.example.studyproject.member;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Class Name : MemberController.java
 * @Description : 멤버 관리를 위한 Dao
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.15     김혜원        회원 탈퇴 비밀번호 검사 기능 추가
 *
 */

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
	
	// 아이디로 유저 정보 불러오기 
	Member getUserInfoById(String user_id);
	
	// 아이디 중복검사 
	Member getMemberById(String user_id);

	// 닉네임 중복검사
	Member getMemberByNickNm(String nickname);

	// 아이디찾기
	String findId(String name, String email);

	// 비밀번호 찾기 - 계정찾기
	public int chkPwd(String user_id, String name, String email);

	// 비밀전호 찾기 - 비밀번호 변경
	public void changePwd(Member vo);

	// 회원 탈퇴
	int deleteMember(String user_id);

	// 비밀번호 일치 여부
	int isPasswordCorrect(String user_id, String password);

	// 사용자 계정 권한 확인
	String chkAUTH(String user_id);

}