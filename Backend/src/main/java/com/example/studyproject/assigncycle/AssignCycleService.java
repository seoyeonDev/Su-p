package com.example.studyproject.assigncycle;

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
	
	// 그룹의 회차 정보 생성
	public void insertAssignCycle(AssignCycle vo) {
		assignCycleDao.insertAssignCycle(vo);
	}
}
