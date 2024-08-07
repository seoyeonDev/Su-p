package com.example.studyproject.assigncycle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}
