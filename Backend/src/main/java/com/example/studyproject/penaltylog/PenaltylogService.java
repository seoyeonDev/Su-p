package com.example.studyproject.penaltylog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Class Name : PenaltylogService.java
 * @Description : PenaltylogService SERVICE
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.06.03     봉선호        최초 생성
 *
 */

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class PenaltylogService {
	
    // log4j2 로그 찍기
    public static final Logger LOGGER = LogManager.getLogger(PenaltylogService.class);
	
	private final PenaltylogDao penaltyLogDao;

	public PenaltylogService(PenaltylogDao penaltyLogDao) {
		this.penaltyLogDao = penaltyLogDao;
	}


	// 유저 아이디로 penaltylog 불러오기
	public ArrayList<Penaltylog> getByUserId(String user_id){
		ArrayList<Penaltylog> penaltylogList = new ArrayList<>();

		try{
			penaltylogList = penaltyLogDao.getByUserId(user_id);
		} catch (Exception e){
			e.printStackTrace();
		}

		return penaltylogList;
	}

	// 그룹 아이디로 penaltylog 불러오기
	public ArrayList<Penaltylog> getByGroupId(String group_id){

		ArrayList<Penaltylog> penaltylogList = new ArrayList<>();

		try{
			penaltylogList = penaltyLogDao.getByGroupId(group_id);
		} catch (Exception e){
			e.printStackTrace();
		}

		return penaltylogList;
	}



	// assigncycle 기준 조회 기준비교
	// penalty insert
	public String chkPenalty(String group_id){
		String msg = "";

		System.out.println(group_id);
		List<Map<String,Object>> penalty_chk = penaltyLogDao.selectPenalty(group_id);
		for (Map<String, Object> penaltyMap : penalty_chk) {
			String userId = (String) penaltyMap.get("user_id");
			int penalty = (int) penaltyMap.get("penalty");
			Long logCount = (Long) penaltyMap.get("log_count");
			boolean penaltyChk = (boolean) penaltyMap.get("penalty_chk");

			if(!penaltyChk){	// penalty 기준 미만
				System.out.println(userId + "   user_id");
				msg += " " + userId;
				// TODO user_id 이용해서 PENALTY table 에 인서트하기
			}else {
				System.out.println(userId + "   XXX");
			}
		}
		
		return msg;
	}
    

}
