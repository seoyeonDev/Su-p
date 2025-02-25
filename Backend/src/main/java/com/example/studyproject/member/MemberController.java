package com.example.studyproject.member;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.studyproject.studygroup.StudyGroupService;
import com.example.studyproject.studylogs.StudyLogsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.studyproject.files.SupFiles;
import com.example.studyproject.files.SupFilesService;

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
	@Autowired
	private SupFilesService filesService;

	@Autowired
	private StudyGroupService studyGroupService;

	@Autowired
	private StudyLogsService studyLogsService;
	
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

	@GetMapping("/loginChk")
	public String loginChk(HttpSession session){
		String loginId = (String) session.getAttribute("loginId");
		if (loginId != null){
			return "로그인 유지중 " + loginId;
		}
		return "로그인이 필요합니다.";
	}



	/**
	 * 회원가입
	 * @param vo
	 * @param f
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping("/join")
	public void join(@RequestPart("vo") Member vo, @RequestParam(value="file", required=false) MultipartFile f) throws NoSuchAlgorithmException {
		Member member = memberService.getMemberById(vo.getUser_id());

		if (member == null) {
			// 사용자별 경로 생성
			String userPath = path + "user_img/" + vo.getUser_id();
			LOGGER.info("File path: " + userPath);
			File dir = new File(userPath);

			// 디렉토리 생성
			if (!dir.exists() && !dir.mkdirs()) {
				LOGGER.error("Failed to create directory: " + userPath);
				throw new RuntimeException("Failed to create directory for user images");
			}

			String fname = f != null ? f.getOriginalFilename() : null;
			String imgPath = null;

			// 파일 처리
			if (fname != null && !fname.isEmpty()) {
				try {
					imgPath = userPath + "/" + fname;
					File imgSave = new File(imgPath);
					f.transferTo(imgSave);
				} catch (Exception e) {
					LOGGER.error("Error while saving file: " + fname, e);
					throw new RuntimeException("File save failed: " + fname, e);
				}
			}

			// 사용자 정보에 이미지 경로 설정
			vo.setProfile_img(imgPath);
			memberService.insertMember(vo);

			// 파일 테이블에 이미지 정보 추가
			if (imgPath != null) {
				try {
					SupFiles files = new SupFiles();
					String[] parts = fname.split("\\.");
					String fileName = parts.length > 1 ? parts[0] : fname; // 확장자가 없을 경우
					String fileExtension = parts.length > 1 ? parts[parts.length - 1].toLowerCase() : ""; // 확장자 추출

					LocalDateTime now = LocalDateTime.now();

					files.setFile_seq(UUID.randomUUID().toString());
					files.setFile_id(vo.getUser_id());
					files.setFile_name(fileName);
					files.setFile_ext(fileExtension);
					files.setIns_id(vo.getUser_id());
					files.setIns_date(now);
					files.setFile_type("profile");

					filesService.insertFiles(files);
				} catch (Exception e) {
					LOGGER.error("Error while inserting file metadata", e);
					throw new RuntimeException("File metadata insertion failed", e);
				}
			}


			LOGGER.info("Join successful for user: " + vo);
		} else {
			LOGGER.info("Join failed: User already exists");
		}

	}

	/**
	 * 로그인
	 * @param vo
	 * @param request
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Member vo, HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
        Member member = memberService.loginMember(vo);
        Map<String, Object> map = new HashMap<>();
        
        String msg = "";
        HttpSession session = null;
        if (member != null && member.getLock_yn().equals("N")) { // login 성공 시

			String auth = memberService.chkAUTH(vo.getUser_id());
			msg = "login success " + auth;

			session = request.getSession();
			session.setAttribute("loginSession", member);
          
			session.setAttribute("loginId", member.getUser_id());
			session.setAttribute("authorization", auth);	// 사용자 계정권한 세션에 추가

			// Cookie

			Cookie cookie = new Cookie("loginId", member.getUser_id());
//			cookie.setMaxAge(3600); // 유효 기간 설정
			cookie.setPath("/");
  
			LOGGER.info("================ session member: " + session.getAttribute("loginSession"));
			LOGGER.info("================ session id: " + session.getAttribute("loginId"));
			msg = "unlocked";

			// 틀린 횟수 알림
			if (member.getFail_num() > 0){
				msg = "비밀번호 오류입니다. 3회 이상 오류 시 계정이 잠금됩니다. \n" +
						"틀린 횟수 : " + member.getFail_num();
			}

			LOGGER.info("================ " + msg);
			map.put("loginId", member.getUser_id());
			response.addCookie(cookie);
//			response.getWriter().println(cookie + " cookie");


		} else if(member != null && member.getLock_yn().equals("Y")){
			msg = "잠금된 계정입니다. 비밀번호 찾기로 잠금 해제해주세요.";
			LOGGER.info("================ " + msg);

			return map;
		} else {
			msg = "존재하지 않는 계정입니다. 회원가입을 진행해주세요.";
		}
		map.put("msg", msg);

		LOGGER.info("================ " + msg);
		return map;
	}

	@PostMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "로그아웃 성공";
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

	/**
	 * 기본 이미지를 가져온다.
	 * @param map
	 * @return
	 */
	@GetMapping("/getDefaultImage")
	public ResponseEntity<byte[]> getDefaultImage(@RequestParam HashMap<String, Object> map) {
		String defaultImg = "uploads/default.jpeg"; // 기본 이미지 경로

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(defaultImg);
		byte[] imageBytes;
		try {
			imageBytes = inputStream.readAllBytes();
			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.IMAGE_JPEG);
			headers.setContentLength(imageBytes.length);

			return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 사용자의 프로필 이미지를 가져온다.
	 * @param map
	 * @param user_id
	 * @return
	 */
	@GetMapping("/getImage/{user_id}")
	public ResponseEntity<byte[]> getImage(@RequestParam HashMap<String, Object> map, @PathVariable String user_id) {
		map.put("fileId", user_id);
		List<HashMap<String, Object>> list = filesService.getProfileFile(map);
		String imagePath = null;
		String fileExtension = null;

		String defaultImg = "uploads/default.jpeg"; // 기본 이미지 경로

		if (list.isEmpty()){	// default 이미지일 경우
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(defaultImg);
			byte[] imageBytes;
			try {
				imageBytes = inputStream.readAllBytes();
				HttpHeaders headers = new HttpHeaders();

				headers.setContentType(MediaType.IMAGE_JPEG);
				headers.setContentLength(imageBytes.length);

				return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {				// default 이미지가 아닐 경우 / supfiles 에 파일이 있을 경우
			String img_name = list.get(0).get("fileName") + "." +list.get(0).get("fileExt");
			imagePath = path + "user_img/" + user_id + "/" + img_name;
			fileExtension = (String) list.get(0).get("fileExt"); // 이미지 확장자

			// 파일이 존재하는지 확인
			Path imageFilePath = Paths.get(imagePath);
			LOGGER.info("이미지파일 경로: " + imagePath);
			if (!Files.exists(imageFilePath)) {
				// 파일이 존재하지 않을 경우 404 에러를 반환
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			// 이미지 파일을 읽어와 byte 배열로 변환
			byte[] imageBytes;
			try {
				imageBytes = Files.readAllBytes(imageFilePath);
			} catch (IOException e) {
				// 이미지 파일을 읽어오는 중에 에러가 발생한 경우 500 에러를 반환
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}

			// 응답에 이미지 데이터와 헤더 설정
			HttpHeaders headers = new HttpHeaders();

			MediaType mediaType;
			if ("jpg".equalsIgnoreCase(fileExtension) || "jpeg".equalsIgnoreCase(fileExtension)) {
				mediaType = MediaType.IMAGE_JPEG;
			} else if ("png".equalsIgnoreCase(fileExtension)) {
				mediaType = MediaType.IMAGE_PNG;
			} else {
				// 기본적으로 JPEG로 설정
				mediaType = MediaType.IMAGE_JPEG;
			}

			headers.setContentType(mediaType);
			headers.setContentLength(imageBytes.length);

			return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
		}
	}

	/**
	 * 내 정보 수정
	 * @param vo
	 * @param isDeleteImg - 이미지 기본으로 변경 여부
	 * @param f
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping("/update")
	public void update(@RequestPart("vo") Member vo, @RequestPart("isDeleteImg") Boolean isDeleteImg, @RequestParam(value="file", required=false) MultipartFile f) throws NoSuchAlgorithmException {

		// 1. 파일이 없으면 기존 데이터만 업데이트 (기본 이미지)
		if(f == null && !isDeleteImg) {
			memberService.updateMember(vo);
			return;
		}

		HashMap<String, Object> map = new HashMap<>();
		map.put("fileId", vo.getUser_id());

		String updatePath = ""; // 최종 업데이트 파일 경로

		List<HashMap<String, Object>> list = filesService.getProfileFile(map); // 기존 프로필 이미지 정보 조회

		// 2. 사진 → 기본 이미지로 변경
		if (f== null && isDeleteImg){ // 사진 → 기본 이미지로 변경
			vo.setProfile_img(null);
			filesService.delProfileFile(map);
			memberService.updateMember(vo);
			return;
		}

		// 3. 사진 → 사진 or 기본 → 사진
		String img_name = null;
		String oldImg = null;
		if (list.isEmpty()){	// img 가 없을 경우
			oldImg = path + "default.jpeg";
		} else {
			img_name = list.get(0).get("fileName") + "." +list.get(0).get("fileExt");
			oldImg = path + "user_img/" + vo.getUser_id() + "/" + img_name;
		}

		String fname = f != null ? f.getOriginalFilename() : null;

		String imgPath = "";
		if(fname != null && !fname.isEmpty()) {
			updatePath = path + "user_img/" + vo.getUser_id(); // 최종 저장될 프로필 파일 경로

			File dir = new File(path);	// 파일 경로 존재 여부 확인
			if (!dir.exists() && !dir.mkdirs()) {
				LOGGER.error("Failed to create directory: " + updatePath);
				throw new RuntimeException("Failed to create directory for user images");
			}

			imgPath = updatePath + "/" + fname;
			File imgSave = new File(imgPath);

			// default 이미지는 삭제 대상에서 제외
			if(!oldImg.contains("default")){
				File imgDel = new File(oldImg);
				imgDel.delete();
			}

			filesService.delProfileFile(map);
			try {
				f.transferTo(imgSave);
				vo.setProfile_img(imgPath);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			vo.setProfile_img(oldImg);
		}

		// member 정보 update
		memberService.updateMember(vo);

		// 파일 테이블에 이미지 정보 추가
		try {
			SupFiles files = new SupFiles();
			String[] parts = null;

			if(fname != null){
				parts =  fname.split("\\.");
			} else {
				parts = new String[] {"default", "jpeg"};
			}

			String fileName = parts.length > 1 ? parts[0] : fname; // 확장자가 없을 경우
			String fileExtension = parts.length > 1 ? parts[parts.length - 1].toLowerCase() : ""; // 확장자 추출

			LocalDateTime now = LocalDateTime.now();

			files.setFile_seq(UUID.randomUUID().toString());
			files.setFile_id(vo.getUser_id());
			files.setFile_name(fileName);
			files.setFile_ext(fileExtension);
			files.setIns_id(vo.getUser_id());
			files.setIns_date(now);
			files.setFile_type("profile");

			filesService.insertFiles(files);
		} catch (Exception e) {
			LOGGER.error("Error while inserting file metadata", e);
			throw new RuntimeException("File metadata insertion failed", e);
		}
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
		if(member == null){
//				|| nickname.equals(member.getNickname())) {
			return 0;
		} else if(nickname.equals(member.getNickname())) {
			LOGGER.info("================ getnickname: " + member.getNickname());
			return 1;
		}
		LOGGER.info("================ checkNickNm: ERROR" );
		return 2;

	}

	/**
	 * 마이페이지 접속 - 비밀번호 검증
	 * @param vo
	 * @return
	 */
	@PostMapping("/inputPassword")
	public boolean inputPassword(@RequestBody Member vo) {
		boolean pwdChk = false;
		try {
			pwdChk = memberService.isPasswordCorrect(vo.getUser_id(), vo.getPassword());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pwdChk;
	}

	/**
	 * 아이디 찾기
	 * @param name
	 * @param email
	 * @return
	 */
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
	 * 비밀번호 찾기 - 계정찾기
	 * @param id
	 * @param name
	 * @param email
	 * @return
	 */
	@GetMapping("/chkPwd")
	public int chkPwd(String id, String name, String email){
		int chk = memberService.chkPwd(id, name, email);
		return chk;
	}

	/**
	 * 비밀번호 찾기 - 비밀번호 변경
	 * @param vo - id, password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping("/changePwd")
	public int changePwd(@RequestBody Member vo) throws NoSuchAlgorithmException {

		LOGGER.info("=========== ID : " + vo.getUser_id());
		LOGGER.info("=========== newPassword : " + vo.getPassword());

		try{
			memberService.changePwd(vo);
			return 1;
		} catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
  
  /**
	 * 멤버 탈퇴
	 * @param user_id - 사용자의 id
	 * @param password - input 창에서 입력받은 password
	 * @return - 회원 탈퇴 성공 여부에 대한 결과값 (String)
	 * @throws NoSuchAlgorithmException
	 */
	@DeleteMapping("/delete/{user_id}")
	public Map<String, Object>  deleteMember (@PathVariable String user_id, @RequestParam String password) throws NoSuchAlgorithmException {
		Map<String, Object> map = new HashMap<>();
		boolean success = false;
		String msg = "";

		if(memberService.isPasswordCorrect(user_id, password)){	// 비밀번호 일치
			int countActive = studyGroupService.countActiveStudyGroups(user_id);

			if(countActive > 0){
				success=false;
				msg = "운영중인 스터디가 존재하여 탈퇴가 불가능 합니다.";
			} else {
				success = memberService.deleteMember(user_id);
				if(success){
					// studylogs 알 수 없음으로 변경
					studyLogsService.anonymizeUserId(user_id);
					msg = "탈퇴가 완료되었습니다.";
				}
			}
		} else{
			msg = "비밀번호가 일치하지 않습니다.";
			success = false;
		}
		map.put("success", success);
		map.put("msg",msg);
		return map;
	}

}
