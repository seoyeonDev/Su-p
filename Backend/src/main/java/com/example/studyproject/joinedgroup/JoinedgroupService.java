package com.example.studyproject.joinedgroup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
 */

@Service
public class JoinedgroupService {

    public static final Logger LOGGER = LogManager.getLogger(JoinedgroupService.class);

    private final JoinedgroupDao joinedgroupDao;

    public JoinedgroupService(JoinedgroupDao joinedgroupDao){
        this.joinedgroupDao = joinedgroupDao;
    }

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
        String joinStatus;

        // 그룹장(true) : ROLE10, 참여자(false) : ROLE20
        // PERM10 : 가입대기, PERM20 : 가입승인, PERM30 : 가입거절
        if(role){ // 그룹장 true : ROLE10
            // 코드 테이블로 검사하는 logic 구현해야함
            roleStatus = "ROLE10";
            joinStatus = "PERM20";
        } else { // 참여자 false : ROLE20
            roleStatus = "ROLE20";
            joinStatus = "PERM10";
        }

        vo.setSubmission_cnt(0); // 총 제출 횟수
        vo.setJoinstatus(joinStatus); // 가입 신청 상태
        vo.setRole(roleStatus); // 역할

        return joinedgroupDao.createJoinedGroup(vo);
    }


    public int deleteJoinedgroup(String user_id, String group_id){
        return joinedgroupDao.deleteJoinedgroup(user_id, group_id);
    }
  
    /**
     * 그룹 상태 변경
     */
    public boolean updateJoinedStatus(Joinedgroup vo, String status){
        vo.setJoinstatus(status);

        return joinedgroupDao.updateJoinedStatus(vo);

    }

    /**
     * userId와 groupId 로 찾기
     */
    public Joinedgroup getByUserIdAndGroupId(String user_id, String group_id){
        LOGGER.info("================ vo : "+user_id + "   "+group_id);

        return joinedgroupDao.getByUserIdAndGroupId(user_id, group_id);
    }
}