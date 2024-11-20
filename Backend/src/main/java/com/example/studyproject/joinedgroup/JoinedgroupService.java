package com.example.studyproject.joinedgroup;

import com.example.studyproject.enums.JoinStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    //public static final String JOIN_REQUEST = "PERM10"; // 가입대기
    //public static final String JOIN_APPROVED = "PERM20"; // 가입승인
    //public static final String JOIN_REJECTED = "PERM30"; // 가입승인

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
    ArrayList<?> selectList(String joinstatus, String role, String status, String user_id){

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
        return JoinStatus.PERM10.equals(status) ||  JoinStatus.PERM20.equals(status) ||  JoinStatus.PERM30.equals(status);
    }
}