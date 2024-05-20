package com.example.studyproject.group;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.studyproject.member.Member;
import com.example.studyproject.member.MemberController;
import com.example.studyproject.member.MemberService;

@RestController
@RequestMapping("/group")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	// log4j2 로그 찍기
	private static final Logger LOGGER = LogManager.getLogger(GroupController.class);
	
	
	// 그룹 추가
	@PostMapping("/createGroup")
	public void createGroup(@RequestBody Group vo) throws NoSuchAlgorithmException {
		
		// 그룹장의 id는 로그인 시 세션에 저장하고, 세션에서 불러와서 vo.set으로 넣기
		// 날짜형식 조율하기
		
		groupService.createGroup(vo);
		
	}
	
	//  그룹 수정
	@PostMapping("(/updateGroup")
	public void updateGroup(@RequestBody Group vo) {
		
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
