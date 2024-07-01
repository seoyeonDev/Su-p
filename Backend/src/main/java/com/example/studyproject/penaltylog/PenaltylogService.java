package com.example.studyproject.penaltylog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
@Service
public class PenaltylogService {
	
    // log4j2 로그 찍기
    public static final Logger LOGGER = LogManager.getLogger(PenaltylogService.class);
	
	private final PenaltylogDao penaltyLogDao;

	public PenaltylogService(PenaltylogDao penaltyLogDao) {this.penaltyLogDao = penaltyLogDao;}

	// penaltylog 삭제
	public int deletePenaltyLog(String user_id, String group_id, int penalty_round){
		int deleteSuccess = 0;
		try{
			deleteSuccess= penaltyLogDao.deletePenaltyLog(user_id, group_id, penalty_round);
		} catch (Exception e){
			e.printStackTrace();
		}

		return deleteSuccess;
	}
    
}
