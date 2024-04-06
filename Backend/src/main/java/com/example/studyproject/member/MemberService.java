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

		Member idVo = getMemberById(vo.getUserId());
		Member loginVo = memberDao.loginMember(vo);

		if (idVo == null) {
			loginVo = null;
			LOGGER.info("================ 1");
		} else {
			if (loginVo != null) {
				vo.setFailNum(0);
				vo.setLockYn("N");
				LOGGER.info("================ 2");
			} else {
				int cnt = idVo.getFailNum();
				LOGGER.info("================ " + cnt);
				vo.setFailNum(cnt + 1);
				vo.setLockYn("N");
				LOGGER.info("================ 3");
				if (cnt >= 2) {
					vo.setLockYn("Y");
					LOGGER.info("================ 4");
				}
			}
			memberDao.updateFailNum(vo);
			memberDao.updateLockYn(vo);
			loginVo = idVo;
		}

		return loginVo;
	}

	// 유저 검색용
	public Member getMemberById(String userId) {

		LOGGER.info("================[MemberService.getMemberById.userid] " + userId);

		return memberDao.getMemberById(userId);
	}
}