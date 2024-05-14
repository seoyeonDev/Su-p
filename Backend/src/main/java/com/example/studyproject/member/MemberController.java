package com.example.studyproject.member;

import java.io.File;
import java.security.NoSuchAlgorithmException;

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
			path = path + "/" + vo.getUser_id();
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

		if (user_id == null || user_id.equals("N")){
			return "존재하지 않는 회원 정보입니다.";
		}
		LOGGER.info("=========== IDCHK : " + user_id);
		return user_id;
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
