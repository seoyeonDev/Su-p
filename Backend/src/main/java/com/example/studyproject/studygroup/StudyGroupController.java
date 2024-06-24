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
 * @ 2024.06.02     이서연        그룹상태 변경 스케줄러 및 메서드 추가
 * @ 2024.06.03     김혜원       그룹 추가시 joinedgroup도 생성
 */

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.studyproject.joinedgroup.Joinedgroup;
import com.example.studyproject.joinedgroup.JoinedgroupService;
import org.apache.ibatis.annotations.Delete;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@SpringBootApplication
@RestController
@RequestMapping("/studygroup")
public class StudyGroupController {
	
	@Autowired
	private StudyGroupService groupService;

	@Autowired
	private JoinedgroupService joinedgroupService;
	
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
		LOGGER.info("year: " + year.substring(2, 4));
		String month = groupService.addZero(now.getMonthValue());
		LOGGER.info("month: " + month);
		String day = groupService.addZero(now.getDayOfMonth());
		LOGGER.info("day: " + day);
		String yrmd = year.substring(2, 4) + month + day;
		LOGGER.info("yrmd: " + yrmd);
		
		String maxGroupId = groupService.getMaxGroupId(yrmd);
		if(!maxGroupId.equals("") && maxGroupId != null) {
			long nextValue = Long.valueOf(maxGroupId) + 1;
			maxGroupId = String.valueOf(nextValue);
			vo.setGroup_id(maxGroupId);
		} else {
			maxGroupId = yrmd + "0001";
			vo.setGroup_id(maxGroupId);
		}
		
		// 총제출횟수
		// 자바 타임 패키지 사용하여 날짜와 날짜 사이의 일 차이값, 월 차이값 구하기
		// 주단위: (종료일-시작일)/7 * 최소제출횟수
		// 월단위: 자바스크립트 Date util setMonth 기준 두 날짜 사이의 월 차이
        LocalDateTime startDateTime = vo.getStartdate();
        LocalDateTime endDateTime = vo.getEnddate();

        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        int totCnt = 0;
        if ((vo.getChk_m()).equals("SUBM10")) {
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            totCnt = (int) ((daysBetween / 7) * vo.getChk_min_cnt());
            vo.setChk_total_cnt(totCnt);
        } else if ((vo.getChk_m()).equals("SUBM20")) {
            long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
            totCnt = (int) (monthsBetween * vo.getChk_min_cnt());
            vo.setChk_total_cnt(totCnt);
        }

		groupService.createGroup(vo);
		Joinedgroup joinedgroupVo = new Joinedgroup(vo.getGroup_id(), vo.getLeader_id(), null,null,0);
		// joinedgroupVo로 joinedgroup 생성, true : 그룹장
		joinedgroupService.createJoinedGroup(joinedgroupVo,true);
	}
	

    //  그룹 수정
    @PutMapping("(/updateGroup")
    public void updateGroup(@RequestBody StudyGroup vo) {

        // 총제출횟수
        // 자바 타임 패키지 사용하여 날짜와 날짜 사이의 일 차이값, 월 차이값 구하기
        // 주단위: (종료일-시작일)/7 * 최소제출횟수
        // 월단위: 자바스크립트 Date util setMonth 기준 두 날짜 사이의 월 차이
        LocalDateTime startDateTime = vo.getStartdate();
        LocalDateTime endDateTime = vo.getEnddate();

        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        int totCnt = 0;
        if ((vo.getChk_m()).equals("SUBM10")) {
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            totCnt = (int) ((daysBetween / 7) * vo.getChk_min_cnt());
            vo.setChk_total_cnt(totCnt);
        } else if ((vo.getChk_m()).equals("SUBM20")) {
            long monthsBetween = ChronoUnit.MONTHS.between(startDate, endDate);
            totCnt = (int) (monthsBetween * vo.getChk_min_cnt());
            vo.setChk_total_cnt(totCnt);
        }

        groupService.updateGroup(vo);
    }

    // 그룹 목록 호출
    @PostMapping("/studyGroupList")
    public List<?> studyGroupList() {

		List<?> studyGroupList = groupService.selectListStudyGroup();
		LOGGER.info("studyGroup 리스트: " + studyGroupList);
		
		// 리턴 변수 클라이언트단 작업하면서 수정 완료
		return studyGroupList;
    }  
  
	// 그룹 제목으로 검색 
	@GetMapping("/listByTitle/{title}")
	public Map<String, Object> listByTitle(@PathVariable String title) {
		List<?> listByTitle = groupService.selectListByTitle(title);
		LOGGER.info("studyGroup 리스트: " + listByTitle);
		
		Map<String, Object> map = new HashMap<>();
		map.put("listByTitle", listByTitle);
		
		// 리턴 변수 map으로 지정
		return map;
	}  
  
	// 그룹 상세 조회 + 조회수 증가
	@GetMapping("/studyDetail/{group_id}")
	public void studyDetail(@PathVariable String group_id) {

		// 조회수 +1 카운트
		groupService.updateViewCnt(group_id);
		
		StudyGroup vo = groupService.selectStudyGroup(group_id);
		LOGGER.info("sg vo: " + vo);
		
		// 리턴 변수 클라이언트단 작업하면서 수정(아마 map으로 보내지 않을까..)
	}
	
	// 그룹 삭제
	@Delete("/deleteGroup")
	public void deleteGroup(@RequestBody StudyGroup vo) {

		groupService.deleteGroup(vo);
	}

    // 그룹 상태 변경
    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(cron = "0 0/1 * * * ?")    // 테스트용
    @PostMapping("/changeStatus")
    public void changeStatus() {
        groupService.changeStatus();
        LOGGER.info("Group Change Status success");
    }
}
