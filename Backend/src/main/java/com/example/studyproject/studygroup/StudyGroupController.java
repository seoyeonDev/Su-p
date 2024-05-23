package com.example.studyproject.studygroup;

/**
 * @Class Name : StudyGroupController.java
 * @Description : StudyGroupController CONTROLLER
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.01     봉선호        최초 생성
 * @ 2024.05.17		봉선호		그룹 추가 메서드 수정
 *
 */

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/group")
public class StudyGroupController {
	
	@Autowired
	private StudyGroupService groupService;
	
	// log4j2 로그 찍기
	private static final Logger LOGGER = LogManager.getLogger(StudyGroupController.class);
	
	
    /**
     * 시퀀스 생성 및 전체체출횟수 계산해서 그룹 추가 & JoinedGroup에 그룹장 추가
	 * @param vo - StudyGroup vo
	 */
	@PostMapping("/createGroup")
	public void createGroup(@RequestBody StudyGroup vo) throws NoSuchAlgorithmException {
		
		// 그룹장의 id는 로그인 시 세션에 아이디 저장하고, 프론트단에서 아이디 세션 불러서 vo로 넘기기
		// 그룹ID 240517 + 0001 형식 로직.. 가장 큰 숫자 불러와서 없으면 만들어주고 있으면 +1
		LocalDateTime now = LocalDateTime.now();
		String year = String.valueOf(now.getYear());
		String month = groupService.addZero(now.getMonthValue());
		String day = groupService.addZero(now.getDayOfMonth());
		String yrmd = year.substring(2, 3) + month + day;
		
		String maxGroupId = groupService.getMaxGroupId(yrmd);
		if(!maxGroupId.equals("") && maxGroupId != null) {
			int nextValue = Integer.valueOf(maxGroupId) + 1;
			maxGroupId = String.valueOf(nextValue);
			vo.setGroup_id(maxGroupId);
		} else {
			maxGroupId = yrmd + "0001";
			vo.setGroup_id(maxGroupId);
		}
		
		// 총제출횟수: 제출기준 주단위 = (종료일-시작일)/제출기준 * 최소제출횟수 / 제출기준 월단위 = (종료월+12 - 시작월) * 최소제출횟수
		// vo.getChk_m() 제출기준 코드 테이블 참조 후 수정 예정
		int totalCnt;
		if((vo.getChk_m()).equals("주")) {
		 	String endDayChar = (String.valueOf(vo.getEnddate())).substring(8, 9);
		 	String startDayChar = (String.valueOf(vo.getStartdate())).substring(8, 9);
		 	int endDayInt = Integer.valueOf(endDayChar);
		 	int startDayInt = Integer.valueOf(startDayChar);
		 	totalCnt = ((endDayInt-startDayInt)/7) * vo.getChk_min_cnt();
		 	vo.setChk_total_cnt(totalCnt);
		} else if((vo.getChk_m()).equals("월")) {
			String endMonthChar = (String.valueOf(vo.getEnddate())).substring(5, 6);
			String startMonthChar = (String.valueOf(vo.getStartdate())).substring(5, 6);
			int endMonthInt = Integer.valueOf(endMonthChar);
			int startMonthInt = Integer.valueOf(startMonthChar);
			totalCnt = (endMonthInt+12-startMonthInt) * vo.getChk_min_cnt();
			vo.setChk_total_cnt(totalCnt);
		}
		groupService.createGroup(vo);
		
		// JoinedGroup에 그룹장ID로 동시에 추가
		// 혜원 pr 후 추가
	}
	
	//  그룹 수정
	@PostMapping("(/updateGroup")
	public void updateGroup(@RequestBody StudyGroup vo) {
		
		// 총제출횟수: 제출기준 주단위 = (종료일-시작일)/제출기준 * 최소제출횟수 / 제출기준 월단위 = (종료월+12 - 시작월) * 최소제출횟수
		// vo.getChk_m() 제출기준 코드 테이블 참조 후 수정 예정
		int totalCnt;
		if((vo.getChk_m()).equals("주")) {
		 	String endDayChar = (String.valueOf(vo.getEnddate())).substring(8, 9);
		 	String startDayChar = (String.valueOf(vo.getStartdate())).substring(8, 9);
		 	int endDayInt = Integer.valueOf(endDayChar);
		 	int startDayInt = Integer.valueOf(startDayChar);
		 	totalCnt = ((endDayInt-startDayInt)/7) * vo.getChk_min_cnt();
		 	vo.setChk_total_cnt(totalCnt);
		} else if((vo.getChk_m()).equals("월")) {
			String endMonthChar = (String.valueOf(vo.getEnddate())).substring(5, 6);
			String startMonthChar = (String.valueOf(vo.getStartdate())).substring(5, 6);
			int endMonthInt = Integer.valueOf(endMonthChar);
			int startMonthInt = Integer.valueOf(startMonthChar);
			totalCnt = (endMonthInt+12-startMonthInt) * vo.getChk_min_cnt();
			vo.setChk_total_cnt(totalCnt);
		}
		
		groupService.updateGroup(vo);
	}
}
