package com.example.studyproject.member;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.studyproject.util.Sha256;

@Service
public class MemberService {

	// log4j2 로그 찍기
	public static final Logger LOGGER = LogManager.getLogger(MemberService.class);

	private final MemberDao memberDao;

	public MemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	// 회원가입
	public void insertMember(Member vo) throws NoSuchAlgorithmException {

		// 비밀번호 단방향 암호화(SHA-256 알고리즘)
		String encPwd = Sha256.encrypt(vo.getPassword());
		vo.setPassword(encPwd);

		memberDao.insertMember(vo);
	}

	// 로그인
	public Member loginMember(Member vo) throws NoSuchAlgorithmException {

		// 입력받은 비밀번호 암호화
		String encPwd = Sha256.encrypt(vo.getPassword());
		vo.setPassword(encPwd);

		Member idVo = getMemberById(vo.getUser_id());
		Member loginVo = memberDao.loginMember(vo);

		if (idVo == null) {
			loginVo = null;
			LOGGER.info("================ idVo is null.");
		} else {
			if(loginVo == null ) {
				int cnt = idVo.getFail_num();
				vo.setFail_num(cnt + 1);
				vo.setLock_yn("N");
				LOGGER.info("================ Current Fail_num: " + cnt);
				if (cnt >= 2) {
					vo.setLock_yn("Y");
					LOGGER.info("================ Id locked by miss.");
				}
			} else {
				vo.setFail_num(0);
				vo.setLock_yn("N");
				if(idVo.getLock_yn().equals("Y")) {
					LOGGER.info("================ Id locked.");
				} else if(idVo.getLock_yn().equals("N")) {
					LOGGER.info("================ Login successed.");
				}
			}
			
			memberDao.updateFailNum(vo);
			memberDao.updateLockYn(vo);
			loginVo = idVo;
			LOGGER.info("================ LoginVo: " + loginVo);
		}

		return loginVo;
	}

	// 멤버수정
	public void updateMember(Member vo) throws NoSuchAlgorithmException {
		
		// 입력받은 비밀번호 암호화
		String encPwd = Sha256.encrypt(vo.getPassword());
		vo.setPassword(encPwd);
		
		memberDao.updateMember(vo);
	}
	
	// 유저 검색용
	public Member getMemberById(String user_id) {

		return memberDao.getMemberById(user_id);
	}

	// 아이디찾기
	public String findId (String name, String email){

        return memberDao.findId(name, email);
	}

	public int chkPwd (String id, String name, String email){
		return memberDao.chkPwd(id, name, email);
	}

	public void changePwd(Member vo) throws NoSuchAlgorithmException {

		// 비밀번호 단방향 암호화(SHA-256 알고리즘)
		String encPwd = Sha256.encrypt(vo.getPassword());
		vo.setPassword(encPwd);
		vo.setFail_num(0);
		vo.setLock_yn("N");
		memberDao.changePwd(vo);
	}
    
	// 회원 삭제
	public boolean deleteMember(String user_id) {
		int rowsAffected = memberDao.deleteMember(user_id);
		return rowsAffected > 0;
	}
}