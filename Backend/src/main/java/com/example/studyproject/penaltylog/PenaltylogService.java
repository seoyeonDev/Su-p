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


}
