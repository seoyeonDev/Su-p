package com.example.studyproject.joinedgroup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

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
 */

@Service
public class JoinedgroupService {

    public static final Logger LOGGER = LogManager.getLogger(JoinedgroupService.class);

    private final JoinedgroupDao joinedgroupDao;

    public JoinedgroupService(JoinedgroupDao joinedgroupDao){
        this.joinedgroupDao = joinedgroupDao;
    }

    /**
     * 그룹 가입 신청
     */
    public boolean createJoinedGroup(Joinedgroup vo, boolean role){
        // 그룹장이라면 true 넣으면 됩니다요

        String roleStatus;

        if(role){ // 그룹장 true : 11
            // 코드 테이블로 검사하는 logic 구현해야함
            roleStatus = "11";
        } else { // 참여자 false : 12
            roleStatus = "12";
        }

        vo.setSubmission_cnt(0); // 총 제출 횟수
        /*
        0 : 가입 대기
        1 : 가입 승인
         */
        vo.setJoinstatus("0"); // 가입 신청 상태
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