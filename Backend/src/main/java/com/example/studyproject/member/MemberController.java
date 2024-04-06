package com.example.studyproject.member;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	// log4j2 로그 찍기
	private static final Logger LOGGER = LogManager.getLogger(MemberController.class);
	
	// 회원가입
	@PostMapping("/join")
	public void join(@RequestBody Member vo) throws NoSuchAlgorithmException {
		Member member = memberService.getMemberById(vo.getUserId());
		if(member == null) {
			memberService.insertMember(vo);
			LOGGER.info("================ " + "Join");
			LOGGER.info("================ " + vo);
		} else {
			LOGGER.info("================ " + "Join failed");
		}
	}
	
	// 로그인
	@SuppressWarnings("null")
	@PostMapping("/login")
	public String login(@RequestBody Member vo, HttpServletRequest request) throws NoSuchAlgorithmException {
		Member member = memberService.loginMember(vo);
		
		String test = "";
		if(member != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginSession", member);
			test = "unlocked";
			LOGGER.info("================ " + test);
			return test;
		} else if(member.getLockYn().equals("Y")) {
			test = "locked";
			LOGGER.info("================ " + test);
			return test;
		} else {
			test = "null";
			LOGGER.info("================ " + test);
			return test;
		}
	}
	
	// 유저검색
	@GetMapping("/{userId}")
	public void login(@PathVariable String userId) {
		Member member = memberService.getMemberById(userId);
		
		LOGGER.info("================ " + userId);
		
		if(member == null) {
			LOGGER.info("================ Member null");
		} else {
			LOGGER.info("================ Member not null");
			LOGGER.info("================ " + member.getFailNum());
			LOGGER.info("================ " + member);
		}
		
	}
}
