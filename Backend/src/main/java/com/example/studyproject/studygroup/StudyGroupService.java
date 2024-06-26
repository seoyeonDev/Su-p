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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
	
	// 과제 총 제출 횟수 구하기
	public int getTotCnt(String chkM, int chkMinCnt, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        int totCnt = 0;
        if (chkM.equals("SUBM10")) {
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            LOGGER.info("주단위 날짜차이: " + daysBetween);
            totCnt = (int) (((daysBetween + 1) / 7) * chkMinCnt);
        } else if (chkM.equals("SUBM20")) {
            long monthsBetween = ChronoUnit.DAYS.between(startDate, endDate);
            LOGGER.info("월단위 날짜차이: " + monthsBetween);
            totCnt = (int) (((monthsBetween + 1) / 30) * chkMinCnt);
        }
        
        return totCnt;
	}
	
	// 그룹 추가
	public void createGroup(StudyGroup vo) throws NoSuchAlgorithmException {
		
		groupDao.createGroup(vo);
	}
	
	// 그룹 수정
	public void updateGroup(StudyGroup vo) {

		groupDao.updateGroup(vo);
	}
	
	// 그룹 삭제
	public void deleteGroup(StudyGroup vo) {

		groupDao.deleteGroup(vo);
	}
  
  	// 그룹 리스트 호출
	public List<?> selectListStudyGroup() {
		return groupDao.selectListStudyGroup();
	}  
	
	// 그룹 제목으로 검색 
	public List<?> selectListByTitle(String title) {
		return groupDao.selectListByTitle(title);
	}
	
	// 그룹 상세 조회
	public StudyGroup selectStudyGroup(String group_id) {
		return groupDao.selectStudyGroup(group_id);
	}
	
	// 조회수 업데이트
	public void updateViewCnt(String group_id) {
		groupDao.updateViewCnt(group_id);
	}
	
	// 그룹 상태 변경
	public void changeStatus(){
		groupDao.changeStatus();
	}
}
