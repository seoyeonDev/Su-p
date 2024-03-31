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
	
	// 유저 검색용
	public Member getMemberById(String userId) {
		
		LOGGER.info("================[MemberService.getMemberById.userid] " + userId);
		
		return memberDao.getMemberById(userId);
	}
}