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


	@GetMapping("/member/test")
	public String test(){
		LOGGER.info ("ttttttteeeeeesssssssttttt");
        return "test success";
	}

	// 20240406 커밋 테스트
	
	// 회원가입
	@PostMapping("/join")
	public void join(@RequestBody Member vo) throws NoSuchAlgorithmException {
		Member member = memberService.getMemberById(vo.getUser_id());
		if(member == null) {
			memberService.insertMember(vo);
			LOGGER.info("================ " + "Join");
			LOGGER.info("================ " + vo);
		} else {	
			LOGGER.info("================ " + "Join failed");
		}
	}
	
	// 로그인
	@PostMapping("/login")
	public String login(@RequestBody Member vo, HttpServletRequest request) throws NoSuchAlgorithmException {
		Member member = memberService.loginMember(vo);
		
		String test = "";
		if(member != null && member.getLock_yn().equals("N")) {
			HttpSession session = request.getSession();
			session.setAttribute("loginSession", member);
			test = "unlocked";
			LOGGER.info("================ " + test);
			return test;
		} else {
			test = "locked";
			LOGGER.info("================ " + test);
			return test;
		}
	}
	
	// 유저검색
	@GetMapping("/{user_id}")
	public void login(@PathVariable String user_id) {
		Member member = memberService.getMemberById(user_id);
		
		LOGGER.info("================ User_id: " + user_id);
		
		if(member == null) {
			LOGGER.info("================ Member null");
		} else {
			LOGGER.info("================ Member not null");
			LOGGER.info("================ Fail_num: " + member.getFail_num());
			LOGGER.info("================ Member: " + member);
		}
		
	}

	@GetMapping("/findId")
	public String findId(String name, String email){
		String user_id = memberService.findId(name, email);

		LOGGER.info("=========== IDCHK : " + user_id);
		return user_id;
	}

}
