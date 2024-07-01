package com.example.studyproject.member;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * @Class Name : MemberController.java
 * @Description : 멤버 관리를 위한 Controller
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.15     김혜원        회원 탈퇴 비밀번호 검사 기능 추가
 *
 */

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	@Value("${spring.servlet.multipart.location}")
	private String path;
	
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
	public void join(@RequestBody Member vo, MultipartFile f) throws NoSuchAlgorithmException {
		Member member = memberService.getMemberById(vo.getUser_id());
		if(member == null) {
			path = path + "user_img/" + vo.getUser_id();
			LOGGER.info("File path: " + path);
			File dir = new File(path);
			dir.mkdir();
			String fname = f.getOriginalFilename();
			
			String imgPath = "";
			if(fname != null && !fname.equals("")) {
				imgPath = path + "/" + fname;
				File imgSave = new File(imgPath);
				try {
					f.transferTo(imgSave);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			vo.setProfile_img(imgPath);
			memberService.insertMember(vo);
			LOGGER.info("================ " + "Join");
			LOGGER.info("================ " + vo);
		} else {	
			LOGGER.info("================ " + "Join failed");
		}
	}
	
	// 로그인
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Member vo, HttpServletRequest request) throws NoSuchAlgorithmException {
        Member member = memberService.loginMember(vo);
        Map<String, Object> map = new HashMap<>();
        
        String msg = "";
        HttpSession session = null;
        if (member != null && member.getLock_yn().equals("N")) {

			String auth = memberService.chkAUTH(vo.getUser_id());
			msg = "login success " + auth;

			session = request.getSession();
			session.setAttribute("loginSession", member);
          
			session.setAttribute("loginId", member.getUser_id());
			session.setAttribute("authorization", auth);	// 사용자 계정권한 세션에 추가
  
			LOGGER.info("================ session member: " + session.getAttribute("loginSession"));
			LOGGER.info("================ session id: " + session.getAttribute("loginId"));
			msg = "unlocked";

			// 틀린 횟수 알림
			if (member.getFail_num() > 0){
				msg = "비밀번호 오류입니다. 3회 이상 오류 시 계정이 잠금됩니다. \n" +
						"틀린 횟수 : " + member.getFail_num();
			}

			LOGGER.info("================ " + msg);
			map.put("msg", msg);
			map.put("loginId", member.getUser_id());
		} else if(member != null && member.getLock_yn().equals("Y")){
			msg = "잠금된 계정입니다. 비밀번호 찾기로 잠금 해제해주세요.";
			msg = "unlocked";
			LOGGER.info("================ " + msg);
			map.put("msg", msg);
			return map;
		} else {
			msg = "존재하지 않는 계정입니다. 회원가입을 진행해주세요.";
		}
		LOGGER.info("================ " + msg);
		return map;
	}

	// 마이페이지 정보 호출
	@GetMapping("/mypage")
	public Map<String, Object> getMemberInfo(@RequestParam String user_id) {
		Map<String, Object> map = new HashMap<>();
		Member member = memberService.getUserInfoById(user_id);
		LOGGER.info("member: " + member);
		map.put("member", member);
		
		return map;
	}
	
	// 수정
	@PostMapping("/update")
	public void update(@RequestBody Member vo, MultipartFile f) throws NoSuchAlgorithmException {
		Member member = memberService.getMemberById(vo.getUser_id());
		
		String oldImg = member.getProfile_img();
		String fname = f.getOriginalFilename();
		
		String imgPath = "";
		if(fname != null && !fname.equals("")) {
			imgPath = path + "/" + vo.getUser_id();
			File imgSave = new File(imgPath);
			File imgDel = new File(oldImg);
			imgDel.delete();
			try {
				f.transferTo(imgSave);
				vo.setProfile_img(imgPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			vo.setProfile_img(oldImg);
		}
		memberService.updateMember(vo);
	}
	
    /**
	 * 유저 검색_회원가입 시 아이디 중복 검사
	 * @param user_id - 유저 아이디
	 * @return chkImpl - 아이디 존재 유무에 따라 0 또는 1 리턴(임시)
	 */
	@GetMapping("/checkId/{user_id}")
	public int checkId(@PathVariable String user_id) {
		Member member = memberService.getMemberById(user_id);
		
		LOGGER.info("================ User_id: " + user_id);
		
		int chkImpl;
		if(member == null) {
			LOGGER.info("================ Member null");
			return chkImpl = 0;
		} else {
			LOGGER.info("================ Member not null");
			LOGGER.info("================ User_id: " + member.getUser_id());
			return chkImpl = 1;
		}
	}

    /**
	 * 유저 검색_회원가입 및 정보 수정 시 닉네임 중복 검사
	 * @param nickname - 유저 닉네임
	 * @return chkImpl - 닉네임 존재 유무에 따라 0 또는 1 리턴(임시)
	 */
	@GetMapping("/checkNickNm/{nickname}")
	public int checkNickNm(@PathVariable String nickname) {
		Member member = memberService.getMemberByNickNm(nickname);
		
		LOGGER.info("================ nickname: " + nickname);
		
		int chkImpl;
		if(member == null) {
			return chkImpl = 0;
		} else {
			LOGGER.info("================ nickname: " + member.getNickname());
			return chkImpl = 1;
		}
	}
	
	@GetMapping("/findId")
	public String findId(String name, String email){
		String user_id = memberService.findId(name, email);

		if (user_id == null || user_id.equals("N")){
			return "존재하지 않는 회원 정보입니다.";
		}
		LOGGER.info("=========== IDCHK : " + user_id);
		return user_id;
	}


	@GetMapping("/chkPwd")
	public int chkPwd(String id, String name, String email){
		int chk = memberService.chkPwd(id, name, email);
		return chk;
	}
  
  
  @PostMapping("/changePwd")
	public int changePwd(Member vo, String newPassword) throws NoSuchAlgorithmException {
		String id = vo.getUser_id();
		String name = vo.getName();
		String email = vo.getEmail();
		int chk = memberService.chkPwd(id, name, email);

		vo.setPassword(newPassword);
		if (chk == 1) {
			// 결과가 한개일 때
			memberService.changePwd(vo);
			return 1;
		}

		return 0;
	}
  
  	/**
	 * 멤버 탈퇴
	 * @param user_id - 사용자의 id
	 * @param password - input 창에서 입력받은 password
	 * @return - 회원 탈퇴 성공 여부에 대한 결과값 (String)
	 * @throws NoSuchAlgorithmException
	 */
	@DeleteMapping("/delete/{user_id}")
	public String deleteMember (@PathVariable String user_id, @RequestParam String password) throws NoSuchAlgorithmException {

		boolean success;

		if(memberService.isPasswordCorrect(user_id, password)){	// 비밀번호 일치
			success = memberService.deleteMember(user_id);
		} else{
			success = false;
			LOGGER.info("================ Member deleted: 비밀번호 일치하지 않음");
		}

		if (success) {
			LOGGER.info("================ Member delete success : " + user_id);
			return "회원 탈퇴 완료";
		} else {
			LOGGER.info("================ Member deletion failed: " + user_id);
			return "회원 탈퇴 실패";
		}

	}

}
