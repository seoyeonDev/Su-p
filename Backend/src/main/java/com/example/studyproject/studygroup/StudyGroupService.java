package com.example.studyproject.studygroup;

/**
 * @Class Name : StudyGroupService.java
 * @Description : StudyGroupService SERVICE
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.01     봉선호        최초 생성
 *
 */

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class StudyGroupService {
	
	// log4j2 로그 찍기
	public static final Logger LOGGER = LogManager.getLogger(StudyGroupService.class);

	private final StudyGroupDao groupDao;

	public StudyGroupService(StudyGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	// 그룹ID MAX값 찾기
	public String getMaxGroupId(String yrmd) {
		String maxGroupId = groupDao.selectMaxGroupId(yrmd);
		
		return maxGroupId;
	}
	
	// 날짜 10 미만값 0 붙이기
	public String addZero(int valueOfDate) {
		String returnValue;
		if(valueOfDate >= 1 && valueOfDate < 10) {
			returnValue = "0" + valueOfDate;
		} else {
			returnValue = String.valueOf(valueOfDate);
		}
		
		return returnValue;
	}
	
	// 그룹 추가
	public void createGroup(StudyGroup vo) throws NoSuchAlgorithmException {
		
		groupDao.createGroup(vo);
	}
	
}
