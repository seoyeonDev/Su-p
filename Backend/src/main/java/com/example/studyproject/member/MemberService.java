package com.example.studyproject.member;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import com.example.studyproject.files.SupFilesService;
import com.example.studyproject.studylogs.StudyLogsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.studyproject.util.Sha256;

/**
 * @Class Name : MemberService.java
 * @Description : 멤버 관리를 위한 Service
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.15     김혜원        회원 탈퇴 비밀번호 검사 기능 추가
 *
 */

@Service
public class MemberService {

	// log4j2 로그 찍기
	public static final Logger LOGGER = LogManager.getLogger(MemberService.class);

	private final MemberDao memberDao;

	public MemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}


	@Value("${spring.servlet.multipart.location}")
	private String path;

	@Autowired
	private SupFilesService filesService;



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
			loginVo = getMemberById(vo.getUser_id());
			LOGGER.info("================ LoginVo: " + loginVo);
		}

		return loginVo;
	}

	// 수정
	public void updateMember(Member vo) throws NoSuchAlgorithmException {
		
		memberDao.updateMember(vo);
	}
	
	// 아이디로 유저정보 불러오기 
	public Member getUserInfoById(String user_id) {
		
		return memberDao.getUserInfoById(user_id);
	}
	
	// 아이디 중복검사
	public Member getMemberById(String user_id) {

		return memberDao.getMemberById(user_id);
	}

	// 닉네임 중복 검사 - 유저 검색용
	public Member getMemberByNickNm(String nickname) {
		
		return memberDao.getMemberByNickNm(nickname);
	}

	// 아이디찾기
	public String findId (String name, String email){

        return memberDao.findId(name, email);
	}

	// 비밀번호 찾기 - 계정찾기
	public int chkPwd (String id, String name, String email){
		return memberDao.chkPwd(id, name, email);
	}

	// 비밀번호 찾기 - 비밀번호 변경
	public void changePwd(Member vo) throws NoSuchAlgorithmException {

		// 비밀번호 단방향 암호화(SHA-256 알고리즘)
		String encPwd = Sha256.encrypt(vo.getPassword());
		vo.setPassword(encPwd);
		vo.setFail_num(0);
		vo.setLock_yn("N");
		memberDao.changePwd(vo);
	}
    
	// 회원 탈퇴 진행
	public boolean deleteMember(String user_id) {
		// 프로필 파일 조회하기
		HashMap<String, Object> map = new HashMap<>();
		map.put("fileId", user_id);
		List<HashMap<String, Object>> list = filesService.getProfileFile(map);
		// 프로필 파일 삭제하기
		if(list.size()>0){
			filesService.delProfileFile(map);

			String img_name = list.get(0).get("fileName") + "." +list.get(0).get("fileExt");
			String oldImg = path + "user_img/" + user_id + "/" + img_name;
			File dir = new File(path);	// 파일 경로 존재 여부 확인
			if (!dir.exists() && !dir.mkdirs()) {
				LOGGER.error("Failed to create directory: " + oldImg);
				throw new RuntimeException("Failed to create directory for user images");
			}
			File imgDel = new File(oldImg);
			imgDel.delete();
		}

		// joinedgroup, penaltylog, member 데이터 삭제
		int rowsAffected = memberDao.deleteMember(user_id);

		return rowsAffected > 0;
	}


	// 비밀번호 일치 여부
	public boolean isPasswordCorrect(String user_id, String password) throws NoSuchAlgorithmException {

		String encPwd = Sha256.encrypt(password);

		return (memberDao.isPasswordCorrect(user_id, encPwd) > 0);
  }
  
	// 사용자 계정 권한 확인
	public String chkAUTH (String user_id){
		return memberDao.chkAUTH(user_id);
	}
}