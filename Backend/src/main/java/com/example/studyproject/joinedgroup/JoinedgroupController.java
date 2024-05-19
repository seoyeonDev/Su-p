package com.example.studyproject.joinedgroup;

import com.example.studyproject.member.MemberService;
import com.example.studyproject.member.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Class Name : JoinedgroupController.java
 * @Description : 그룹 가입 상태 정보 Controller
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.13     김혜원        최초 생성
 * @ 2024.05.14     김혜원        그룹 가입 신청하기
 *
 */

@RestController
@RequestMapping("/joinedgroup")
public class JoinedgroupController {

    @Autowired
    private JoinedgroupService joinedgroupService;

    @Autowired
    private MemberService memberService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(JoinedgroupController.class);


    /**
     * 그룹 가입 신청자
     * @param vo - Joinedgroup vo의 user_id, group_id
     */
    @PostMapping("/join")
    public void joinGroup(@RequestBody Joinedgroup vo){
        LOGGER.info("================ joinedgroup join");

        Member memberVo = memberService.getMemberById(vo.getUser_id().getUser_id());
        // group getGroupById 도 만들면 검사하는 logic 넣기

        boolean success;

        if(memberVo != null){
            success = joinedgroupService.createJoinedGroup(vo, false);
        } else {
            success = false;
        }

        if(success){
            LOGGER.info("================ join success");
        } else {
            LOGGER.info("================ join failed");
        }
    }
}