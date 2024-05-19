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
 *
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
    public boolean createJoinedGroup(Joinedgroup vo){

        vo.setSubmission_cnt(0); // 총 제출 횟수
        vo.setJoinstatus("0"); // 가입 신청 상태
        vo.setRole("200"); // 역할

        return joinedgroupDao.createJoinedGroup(vo);
    }

    /**
     * 그룹 상태 변경
     */
    public boolean updateJoinedStatus(Joinedgroup vo, String status){
        vo.setJoinstatus(status);

        return joinedgroupDao.updateJoinedStatus(vo);
    }
}