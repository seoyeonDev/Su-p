package com.example.studyproject.penaltylog;

import com.example.studyproject.studygroup.StudyGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
	}

	// penaltylog 추가/ 241102 이서연
	public int insertPenaltyLog(Penaltylog penaltylog, int log_count, String penalty_round){
		String groupId = penaltylog.getGroup_id();
		String userId = penaltylog.getUser_id();
		int result = 0;

		boolean is_empty = penaltyLogDao.penaltyLogMultiChk(groupId, userId, penalty_round);
		if (is_empty){
			result = penaltyLogDao.insertPenaltyLog(penaltylog, log_count);	// 1 penaltylog 추가됨
		} else {
			result = 2;	// 이미 존재
		}
		return result;
	}

	/**
	 * 제출개수 확인 후 비교, 기준 미달 시 penalty 추가
	 * 매일 00시 실행
	 *
	 * 2024.11.02
	 * @Author 이서연
	 */
//    @Scheduled(fixedRate = 5000)  // 테스트용. 5초마다
	@Scheduled(cron = "0 0 0 * * ?")    // 매일 자정
	public void penaltyScheduler () {
		int result = 0;
		String msg = null;
		List<Map<String, Object>> penalty_chk = chkPenalty();
		for (Map<String, Object> penaltyMap : penalty_chk) {
			System.out.println(penaltyMap);

			String userId = (String) penaltyMap.get("user_id");
			String groupId = (String) penaltyMap.get("group_id");
			boolean penaltyChk = (boolean) penaltyMap.get("chk");

			// penaltylog 추가 파라메터 설정
			Penaltylog penaltylog = new Penaltylog(userId, groupId, null, null);
			int log_count = (int) penaltyMap.get("log_count");  // 기간 중 log 올린 횟수

			// assigncycle 저장
			String penalty_round = (String) penaltyMap.get("assigncycle");
			penaltylog.setPenalty_round(penalty_round);

			if (!penaltyChk) {  // 기준 미달 (penalty 추가 실행)
				System.out.println(userId + "   user_id");
				result = insertPenaltyLog(penaltylog, log_count, penalty_round);
			}
			// 0: 기준충족, 1: insert, 2: penalty 이미 존재
			System.out.println("-------- [penalty 스케줄러] " + userId + " 결과 : " + result);
		}
	}


	/**
	 * penaltylog 삭제
	 *
	 * 2024.11.08
	 * @Author 이서연
	 */
	public boolean deletePenaltyLog(String user_id, String group_id){
		boolean result = false;
		result = penaltyLogDao.deletePenaltyLog(user_id, group_id);
		return result;
	}

	// 주어진 그룹 ID에 대한 penaltylog, member, joinedgroup 정보를 조회하여 반환
	public List<PenaltylogFetcher> getPanaltylogByGroupIdAndUserId(String group_id){
		List<PenaltylogFetcher> penaltylogInfoList = new ArrayList<>();

		try{
			penaltylogInfoList = penaltyLogDao.selectPenaltylogWithJoinedgroup(group_id);
		} catch (Exception e){
			e.printStackTrace();
		}

		return penaltylogInfoList;
	}

	// 사용자에 대한 전체 패널티 개수를 반환하는 메서드
	public int getPenaltyCount(String user_id){
		int penaltyCount = 0;
		try{
			penaltyCount = penaltyLogDao.getPenaltyCount(user_id, null);
		} catch(Exception e) {
			throw new RuntimeException("Error fetching penalty count for user: " + user_id, e);
		}
		return penaltyCount;
	}

	//사용자와 특정 스터디 그룹에 대한 총 패널티 개수
	public int getPenaltyCount(String user_id, String group_id){
		int penaltyCount = 0;
		try{
			penaltyCount = penaltyLogDao.getPenaltyCount(user_id, group_id);
		} catch(Exception e) {
			throw new RuntimeException("Error fetching penalty count for user: " + user_id + " and group: " + group_id, e);
		}
		return penaltyCount;
	}
}
