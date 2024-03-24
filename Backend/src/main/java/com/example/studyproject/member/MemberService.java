package com.example.studyproject.member;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	// log4j2 로그 찍기
	public static final Logger LOGGER = LogManager.getLogger(MemberService.class);
	
	private final MemberDao memberDao;
	
	public MemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public Member getMemberById(String userId) {
		
		LOGGER.info("================[MemberService.getMemberById.userid] " + userId);
		
		return memberDao.getMemberById(userId);
	}
}
