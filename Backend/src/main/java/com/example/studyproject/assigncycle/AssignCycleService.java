package com.example.studyproject.assigncycle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class Name : AssginCycleService.java
 * @Description : AssginCycleService SERVICE
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.06.23     봉선호        최초 생성
 *
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AssignCycleService {

	// log4j2 로그 찍기
	public static final Logger LOGGER = LogManager.getLogger(AssignCycleService.class);

	private final AssignCycleDao assignCycleDao;

	public AssignCycleService(AssignCycleDao assignCycleDao) {
		this.assignCycleDao = assignCycleDao;
	}
	// 회차 계산
	public List<AssignCycle> createCycle(String group_id, LocalDateTime startdate, LocalDateTime lastdate, String chkM) {
		List<AssignCycle> list = new ArrayList<>();
		
		// 주 또는 월단위일 경우,
		if(chkM.equals("SUBM10")) {
			int assigncycle = 1;
			// 시작날짜가 종료날짜 이전일 동안
			while(!startdate.isAfter(lastdate)) {
				// n회차의 종료날짜를 계산하고
				LocalDateTime enddate = startdate.plusDays(6);
				if(enddate.isAfter(lastdate)) {
					enddate = lastdate;
				}
				// 리스트에 넣는다.
				list.add(new AssignCycle(group_id, startdate.toString(), enddate.withHour(11).withMinute(59).toString(), String.valueOf(assigncycle)));
				startdate = enddate.plusDays(1);
				assigncycle++;
			}
		} else if(chkM.equals("SUBM20")) {
			int assigncycle = 1;
			while(!startdate.isAfter(lastdate)) {
				LocalDateTime enddate = startdate.plusDays(29);
				if(enddate.isAfter(lastdate)) {
					enddate = lastdate;
				}
				list.add(new AssignCycle(group_id, startdate.toString(), enddate.withHour(11).withMinute(59).toString(), String.valueOf(assigncycle)));
				startdate = enddate.plusDays(1);
				assigncycle++;
			}
		}
		
		return list;
	}
	
	
	// 그룹의 회차 정보 생성
	public void insertAssignCycle(List<AssignCycle> list) {
		assignCycleDao.insertAssignCycle(list);
	}
	
	// 그룹 회차 정보 삭제 - 그룹 수정할 때
	public void deleteAssignCycle(String group_id) {
		assignCycleDao.deleteAssignCycle(group_id);
	}

	// 모든 스터디그룹의 총 출석 개수를 구하기
	public int getAssignCycleCountMultiple(List<String> groupIds){
		int getAssignCycleCount = 0;
		try {
			getAssignCycleCount = assignCycleDao.getAssignCycleCountMultiple(groupIds);
		} catch(Exception e){
			throw new RuntimeException("Error fetching assign cycle count for multiple groups", e);
		}
		return getAssignCycleCount;
	}

	// 특정 스터디그룹의 총 출석 개수를 구하기
	public int getAssignCycleCount(String group_id){
		int getAssignCycleCount = 0;
		try {
			getAssignCycleCount = assignCycleDao.getAssignCycleCount(group_id);
		} catch(Exception e){
			throw new RuntimeException("Error fetching assign cycle count for group: " + group_id, e);
		}
		return getAssignCycleCount;
	}
}
