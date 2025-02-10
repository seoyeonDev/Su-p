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

import com.example.studyproject.assigncycle.AssignCycle;
import com.example.studyproject.assigncycle.AssignCycleService;
import com.example.studyproject.enums.JoinStatus;
import com.example.studyproject.enums.StudygroupStatus;
import com.example.studyproject.joinedgroup.Joinedgroup;
import com.example.studyproject.joinedgroup.JoinedgroupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Delete;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@Autowired AssignCycleService assignCycleService;
	
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
		if(maxGroupId != null && !maxGroupId.equals("")) {
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
		int totCnt = groupService.getTotCnt(vo.getChk_m(), vo.getChk_min_cnt(), vo.getStartdate(), vo.getEnddate());
		LOGGER.info("과제 총 제출 횟수: " + totCnt);
		vo.setChk_total_cnt(totCnt);
		
		// 시작일, 종료일, 제출기준(주, 월)으로 회차 생성
		List<AssignCycle> list = assignCycleService.createCycle(maxGroupId, vo.getStartdate(), vo.getEnddate(), vo.getChk_m());
		LOGGER.info("사이클 리스트: " + list);
		assignCycleService.insertAssignCycle(list);
		
		groupService.createGroup(vo);
		// ROLE10 : 그룹장
		joinedgroupService.createJoinedGroup(vo.getLeader_id(), vo.getGroup_id(), JoinStatus.ROLE10);
	}
	


    //  그룹 수정
	@PostMapping("/updateGroup")
    public void updateGroup(@RequestBody StudyGroup vo) {

		// 총제출횟수
		// 자바 타임 패키지 사용하여 날짜와 날짜 사이의 일 차이값, 월 차이값 구하기
		// 주단위: (종료일-시작일)/7 * 최소제출횟수
		// 월단위: 자바스크립트 Date util setMonth 기준 두 날짜 사이의 월 차이
		int totCnt = groupService.getTotCnt(vo.getChk_m(), vo.getChk_min_cnt(), vo.getStartdate(), vo.getEnddate());
		LOGGER.info("과제 총 제출 횟수: " + totCnt);
		vo.setChk_total_cnt(totCnt);
		
		// 회차 정보 삭제
		assignCycleService.deleteAssignCycle(vo.getGroup_id());
		
		// 시작일, 종료일, 제출기준(주, 월)으로 회차 새로 생성
		List<AssignCycle> list = assignCycleService.createCycle(vo.getGroup_id(), vo.getStartdate(), vo.getEnddate(), vo.getChk_m());
		LOGGER.info("사이클 리스트: " + list);
		assignCycleService.insertAssignCycle(list);


        groupService.updateGroup(vo);
    }

    // 그룹 목록 호출
    @GetMapping("/studyGroupList")
    public List<?> studyGroupList() {

		List<?> studyGroupList = groupService.selectListStudyGroup();
		
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
  
	// 그룹 상세 조회 - 수정페이지
	@GetMapping("/studyInfo")
	public Map<String, Object> studyDetailToUpdate(@RequestParam String group_id) {
		
		Map<String, Object> map = new HashMap<>();
		StudyGroup vo = groupService.selectStudyGroup(group_id);
		map.put("groupVo", vo);
		LOGGER.info("sg vo: " + vo);
		
		return map;
	}
	
	// 그룹 상세 조회 + 조회수 증가
	@GetMapping("/studyDetail")
	public Map<String, Object> studyDetail(@RequestParam String group_id, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<>();

		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
//		if (loginId.isEmpty()) loginId = null;

		// 조회수 +1 카운트
		groupService.updateViewCnt(group_id);
		
		StudyGroup vo = groupService.selectStudyGroup(group_id);
		LOGGER.info("sg vo: " + vo);
		map.put("vo", vo);
		
		int userCnt = joinedgroupService.selectJoinedListSize(group_id);
		if(vo.getLeader_id().equals(loginId)) {	// TODO if 부분 있어야 하는 로직이 맞는지 확인 필요
			// 상세 조회 클릭을 그룹장이 했을 때,
			LOGGER.info("0");
			if(userCnt > 1) {
				// 해당 그룹에 참여한 인원이 그룹장 이외에 더 있을 때,
				map.put("status", StudygroupStatus.STAT10); // 0
			} else {
				// 해당 그룹에 참여한 인원이 그룹장 외엔 없을 때 삭제 및 수정 버튼 보여주도록,
				map.put("status", StudygroupStatus.STAT00); // 1
			}
		} else {
			// 상세 조회 클릭을 일반 유저가 했을 때,
			LOGGER.info("1");
			Joinedgroup jgVo = joinedgroupService.getByUserIdAndGroupId(loginId, group_id);
			if(jgVo != null) {
				// 일반 유저가 이미 이 그룹에 신청을 했을 때,
				map.put("perm", JoinStatus.PERM10); // 2
			} else {
				// 일반 유저가 이 그룹에 신청을 아직 안했을 때,
				map.put("perm", JoinStatus.PERM00); // 3
			}
		}
		return map;
	}
	
	// 그룹 삭제
	@DeleteMapping("/deleteGroup")
	public void deleteGroup(@RequestBody StudyGroup vo) {

		try {
			// 그룹 삭제
			groupService.deleteGroup(vo);
			// joinedgroup 삭제
			joinedgroupService.deleteEveryJoinedGroup(vo.getGroup_id());
			// assigncycle 삭제
			assignCycleService.deleteAssignCycle(vo.getGroup_id());
			LOGGER.info("그룹 삭제");
		} catch(Exception e) {
			LOGGER.info("Error: " + e.getMessage());
		}
	}


    // 그룹 상태 변경
    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(cron = "0 0/1 * * * ?")    // 테스트용
    @PostMapping("/changeStatus")
    public void changeStatus() {
        groupService.changeStatus();
        LOGGER.info("Group Change Status success");
    }

    // 제목 & (날짜 or 조회수) 조회
    @GetMapping("/list")
    public ResponseEntity<List<?>> getStudyGroups(@RequestParam String title,
                                                           @RequestParam(required = false, defaultValue = "false") Boolean isSortByViews,
                                                           @RequestParam(required = false, defaultValue = "false") Boolean isSortByLatest) {
        try {
            List<?> studyGroups=groupService.getStudyGroups(title, isSortByViews, isSortByLatest);
            return ResponseEntity.ok(studyGroups);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
