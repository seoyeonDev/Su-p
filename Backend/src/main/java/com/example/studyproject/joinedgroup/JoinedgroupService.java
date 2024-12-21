package com.example.studyproject.joinedgroup;

import com.example.studyproject.assigncycle.AssignCycleService;
import com.example.studyproject.enums.JoinStatus;
import com.example.studyproject.penaltylog.PenaltylogDao;
import com.example.studyproject.penaltylog.PenaltylogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Class Name : JoinedgroupService.java
 * @Description : 그룹 가입 상태 Service
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.13     김혜원        최초 생성
 * @ 2024.05.14     김혜원        그룹 가입 신청하기
 * @ 2024.05.27     김혜원        get 메서드 추가
 * @ 2024.06.10     이서연        사용자별 가입한 그룹 목록 메서드 추가
 * @ 2024.06.24     이서연        조건에 따른 목록보기
 * @ 2024.11.16     김혜원        JoinStsuts enum 추가
 */

@Service
public class JoinedgroupService {

    public static final Logger LOGGER = LogManager.getLogger(JoinedgroupService.class);

    private final JoinedgroupDao joinedgroupDao;

    public JoinedgroupService(JoinedgroupDao joinedgroupDao){
        this.joinedgroupDao = joinedgroupDao;
    }

    @Autowired
    private PenaltylogService penaltyLogService;

    @Autowired
    private AssignCycleService assignCycleService;

    // 사용자별 가입한 그룹 목록
    ArrayList<Joinedgroup> selectJoinedList(String user_id){
        return joinedgroupDao.selectJoinedList(user_id);
    }

    /**
     * 그룹 가입 신청
     */
    public boolean createJoinedGroup(Joinedgroup vo, boolean role){
        // 그룹장이라면 true, 참여자라면 false
        String roleStatus;
        JoinStatus joinStatus;

        // 그룹장(true) : ROLE10, 참여자(false) : ROLE20
        // PERM10 : 가입대기, PERM20 : 가입승인, PERM30 : 가입거절
        if(role){ // 그룹장 true : ROLE10
            // 코드 테이블로 검사하는 logic 구현해야함
            roleStatus = "ROLE10";
            joinStatus = JoinStatus.PERM20;
        } else { // 참여자 false : ROLE20
            roleStatus = "ROLE20";
            joinStatus = JoinStatus.PERM10;
        }

        vo.setSubmission_cnt(0); // 총 제출 횟수
        vo.setJoinstatus(joinStatus); // 가입 신청 상태
        vo.setRole(roleStatus); // 역할

        return joinedgroupDao.createJoinedGroup(vo);
    }

    // 그룹 삭제 시 함께 삭제
    public void deleteEveryJoinedGroup(String group_id) {
    	joinedgroupDao.deleteEveryJoinedGroup(group_id);
    }
    
    // 가입 거절, 취소
    public int deleteJoinedgroup(String user_id, String group_id){
        return joinedgroupDao.deleteJoinedgroup(user_id, group_id);
    }
  
    // 그룹 상태 변경
    public boolean updateJoinedStatus(String group_id, String user_id, JoinStatus status){
        if(!isValidStatus(status)){
            return false;
        }
        Joinedgroup joinedgroup = new Joinedgroup();
        joinedgroup.setGroup_id(group_id);
        joinedgroup.setUser_id(user_id);
        joinedgroup.setJoinstatus(status);

        return joinedgroupDao.updateJoinedStatus(joinedgroup);
    }

    /**
     * userId와 groupId 로 찾기
     */
    public Joinedgroup getByUserIdAndGroupId(String user_id, String group_id){
        LOGGER.info("================ vo : "+user_id + "   "+group_id);

        return joinedgroupDao.getByUserIdAndGroupId(user_id, group_id);
    }

    // 조건에 따른 목록보기
    ArrayList<?> selectList(JoinStatus joinstatus, String role, String status, String user_id){

        return joinedgroupDao.selectList(joinstatus, role, status, user_id);
    }
    
    // 그룹아이디로 리스트 사이즈 체크
    public int selectJoinedListSize(String group_id) {
    	
    	return joinedgroupDao.selectJoinedListSize(group_id);
    }

    // 그룹아이디로 목록보기
    public ArrayList<JoinedUserInfo> selectListByGroupId(String group_id, JoinStatus joinStatus) {
        return joinedgroupDao.selectJoinedListByGroupId(group_id, joinStatus);
    }

    // 주어진 status 값이 유효한 값인지 확인합니다. (유효하면 true, 그렇지 않다면 false)
    private boolean isValidStatus(JoinStatus status) {
        for (JoinStatus validStatus : JoinStatus.values()) {
            if (validStatus.equals(status)) {
                return true;
            }
        }
        return false;
    }

    // 모든 스터디그룹의 평균 출석률 구하기
    public double getAttendanceCalculation(String user_id) throws Exception {
        // 전체 패널티 개수
        int penaltyCount = penaltyLogService.getPenaltyCount(user_id);
        double result;

        // 사용자가 가입한 group_id 가져오와 전체 assignCycle 개수 구하기
        int assignCycleCount = assignCycleService.getAssignCycleCountByUserId(user_id);

        // 전체 제출 회수 - 패널티 회수 = 0 이면 0% 반환
        if (assignCycleCount - penaltyCount == 0) {
            return 0;
        } else {
            result = ((double) (assignCycleCount - penaltyCount) / assignCycleCount) * 100;
            return Math.round(result);
        }
    }

    // 특정 스터디그룹의 평균 출석률 구하기
    public StudyAttendanceResult getStudyAttendance(String user_id, String group_id){
        StudyAttendanceResult studyAttendanceResult = new StudyAttendanceResult();
        // 전체 패널티 개수
        int penaltyCount = penaltyLogService.getPenaltyCount(user_id, group_id);
        // 전체 총 개수 assignCycle
        int assignCycleCount = assignCycleService.getAssignCycleCount(group_id);

        double attendance  = 0;

        if (assignCycleCount - penaltyCount != 0) {
            attendance  = Math.round(((double)(assignCycleCount - penaltyCount) / assignCycleCount ) * 100) ;
        }

        studyAttendanceResult.setAttendance(attendance);
        studyAttendanceResult.setPenaltyCount(penaltyCount);

        return studyAttendanceResult;
    }
}