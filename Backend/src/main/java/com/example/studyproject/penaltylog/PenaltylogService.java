package com.example.studyproject.penaltylog;

import com.example.studyproject.studygroup.StudyGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Class Name : PenaltylogService.java
 * @Description : PenaltylogService SERVICE
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.06.03     봉선호        최초 생성
 * @ 2024.07.01 	김혜원        조회 및 초기화
 * @ 2024.07.01		이서연 		 데이터 조회 및 비교
 */

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
	public List<Map<String,Object>> chkPenalty() {
		String msg = "";
		List<Map<String, Object>> penalty_chk = penaltyLogDao.selectPenalty();
		return penalty_chk;


//		for (Map<String, Object> penaltyMap : penalty_chk) {
//
////			System.out.println(penaltyMap);
//			String userId = (String) penaltyMap.get("user_id");
//			String groupId = (String) penaltyMap.get("group_id");
//			int penalty = (int) penaltyMap.get("penalty");
//			boolean penaltyChk = (boolean) penaltyMap.get("penalty_chk");
//
//			Penaltylog penaltylogVo = new Penaltylog(userId, groupId, null, null);


//			if(!penaltyChk){	// penalty 기준 미만
//				System.out.println(userId + "   user_id");
//				msg += " " + userId;
//				int success = penaltyLogDao.insertPenaltylog(penaltylogVo);
//
//				if(success>=1){
//					LOGGER.info("================ penalty insert success" );
//				} else{
//					LOGGER.info("================ penalty insert fail" );
//				}
//			}else {
//				System.out.println(userId + "   XXX");
//			}
	}

	// penaltylog 추가/ 241102 이서연
	public int insertPenaltyLog(Penaltylog penaltylog, int log_count){
		String groupId = penaltylog.getGroup_id();
		String userId = penaltylog.getUser_id();
		int result = 0;

		boolean is_empty = penaltyLogDao.penaltyLogMultiChk(groupId, userId);
		if (is_empty){
			result = penaltyLogDao.insertPenaltyLog(penaltylog, log_count);	// 1 penaltylog 추가됨
		} else {
			result = 2;	// 이미 존재
		}
		return result;
	}

	// penaltylog 추가/ 240707 김혜원
//	public int insertPenaltylog(Penaltylog penaltylog){
//		int success=0;
//
//		try{
//			success = penaltyLogDao.insertPenaltylog(penaltylog);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//
//		return success;
//	}

}
