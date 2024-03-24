package com.example.studyproject.member;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;

	// log4j2 로그 찍기
	private static final Logger LOGGER = LogManager.getLogger(MemberController.class);
	
	@GetMapping("/member/{userId}")
	public void login(@PathVariable String userId) {
		Member member = memberService.getMemberById(userId);
		
		LOGGER.info("================[MemberController.login.userid] " + userId);
		
		if(member == null) {
			LOGGER.info("================[MemberController.login] Member null");
		} else {
			LOGGER.info("================[MemberController.login] Member not null");
		}
		
	}
}
