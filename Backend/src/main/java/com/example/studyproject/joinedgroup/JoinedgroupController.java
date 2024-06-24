package com.example.studyproject.joinedgroup;

import com.example.studyproject.member.MemberService;
import com.example.studyproject.member.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Update;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * @Class Name : JoinedgroupController.java
 * @Description : 그룹 가입 상태 정보 Controller
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.13     김혜원        최초 생성
 * @ 2024.05.14     김혜원        그룹 가입 신청하기
 * @ 2024.05.19     김혜원        그룹 상태 변경
 * @ 2024.05.27     김혜원        get 메서드 추가
 * @ 2024.06.10     김혜원        joinedgroup 타입 바꾸기
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


    @GetMapping("/joinedList")
    public Map selectJoinedList(HttpServletRequest request){
        HttpSession session = request.getSession();
        Map map = new HashMap();
        if (session == null){
            return null;
        } else {
            String loginId = (String) session.getAttribute("loginId");
            ArrayList<Joinedgroup> list = joinedgroupService.selectJoinedList(loginId);
            map.put("joinedlist", list);
        }
        return map;
    }

    /**
     * 그룹 가입 신청자
     * @param vo - Joinedgroup vo의 user_id, group_id
     */
    @PostMapping("/join")
    public void joinGroup(@RequestBody Joinedgroup vo){
        LOGGER.info("================ joinedgroup join");

        Member memberVo = memberService.getMemberById(vo.getUser_id());
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


    @DeleteMapping("/user/{user_id}/group/{group_id}")
    public void deleteJoinedgroup(@PathVariable("user_id") String user_id, @PathVariable("group_id") String group_id){
        Joinedgroup joinedgroupVo = joinedgroupService.getByUserIdAndGroupId(user_id, group_id);

        int success = 0;

        LOGGER.info("================ vo : "+joinedgroupVo);

        if(joinedgroupVo != null){
            success = joinedgroupService.deleteJoinedgroup(user_id, group_id);
        }

        if(success>0){
            LOGGER.info("================ joingroup delete success");
        }else{
            LOGGER.info("================ joingroup delete failed");
        }
    }


    /**
     * 그룹 상태 변경
     * @param vo - joinedgroup의 vo
     * @param status - 수정할 값
     */
    @PutMapping("/updateStatus/{status}")
    public void updateJoinStatus(@RequestBody Joinedgroup vo, @PathVariable("status") String status){
        LOGGER.info("================ joinedgroup join");
        Joinedgroup joinedgroupVo = joinedgroupService.getByUserIdAndGroupId(vo.getUser_id(), vo.getGroup_id());
        // 나중 : status가 code 테이블에 있는지 검사

        boolean success = false;

        // 값이 있을 때에만 수정 가능
        if(joinedgroupVo != null){
            success = joinedgroupService.updateJoinedStatus(vo, status);
        }


        if(success){
            LOGGER.info("================ join update success");
        } else {
            LOGGER.info("================ join update failed");
        }

    }


}