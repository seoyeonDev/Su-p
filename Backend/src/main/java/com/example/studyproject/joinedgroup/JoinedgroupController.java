package com.example.studyproject.joinedgroup;

import com.example.studyproject.member.MemberService;
import com.example.studyproject.member.Member;
import com.example.studyproject.penaltylog.PenaltylogFetcher;
import com.example.studyproject.penaltylog.PenaltylogService;
import com.example.studyproject.studygroup.StudyGroup;
import com.example.studyproject.studygroup.StudyGroupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Update;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
 * @ 2024.11.09     김혜원        joinedlist 가져오기
 */

@RestController
@RequestMapping("/joinedgroup")
public class JoinedgroupController {

    @Autowired
    private JoinedgroupService joinedgroupService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private StudyGroupService studyGroupService;

    @Autowired
    private PenaltylogService penaltylogService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(JoinedgroupController.class);

    public static final String JOIN_REQUEST = "PERM10"; // 가입대기
    public static final String JOIN_APPROVED = "PERM20"; // 가입승인
    public static final String RECRUITING = "STAT10"; // 모집중
    public static final String IN_PROGRESS = "STAT20"; // 진행중

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
    public Map updateJoinStatus(@RequestBody Joinedgroup vo, @PathVariable("status") String status){
        Map map = new HashMap();
        LOGGER.info("================ joinedgroup update");
        Joinedgroup joinedgroupVo = joinedgroupService.getByUserIdAndGroupId(vo.getUser_id(), vo.getGroup_id());
        // 나중 : status가 code 테이블에 있는지 검사

        boolean success = false;

        // 값이 있을 때에만 수정 가능
        if(joinedgroupVo != null){
            success = joinedgroupService.updateJoinedStatus(joinedgroupVo.getGroup_id(), joinedgroupVo.getUser_id(), status);
        }

        if(success){
            LOGGER.info("================ join update success");
        } else {
            LOGGER.info("================ join update failed");
        }

        map.put("success", success);

        return map;
    }

    // 조건에 따른 목록보기
    @GetMapping("/selectList")
    public Map selectList(String joinstatus, String role, String status, HttpServletRequest request){
        HttpSession session = request.getSession();
        Map map = new HashMap();
        if (session == null){
            return null;
        } else {
            String loginId = (String) session.getAttribute("loginId");
//            loginId = "sooyeon275";
            ArrayList<Joinedgroup> list = joinedgroupService.selectList(joinstatus, role, status, loginId);
            map.put("list",list);
        }

        return map;
    }

    /**
     * 그룹 오너가 해당 그룹의 멤버 목록과 관련된 정보를 조회하는 API입니다.
     *
     * 1. 로그인된 사용자의 ID가 해당 그룹의 오너와 일치하는지 확인합니다.
     * 2. 그룹이 오픈 전인지 후인지 확인한 후, 그에 따라 다른 데이터를 반환합니다.
     * - 오픈 전인 경우: 가입 신청 및 승인된 목록을 반환합니다.
     * - 오픈 후인 경우: 그룹 멤버들의 패널티 정보와 관련된 데이터를 반환합니다.
     * @param request
     * @param group_id
     * @return
     */
    @GetMapping("/groupOwnerJoinedList")
    public Map selectGroupOwnerJoinedList(HttpServletRequest request, String group_id) {
        HttpSession session = request.getSession();
        Map map = new HashMap();
        if (session == null) {
            return null;
        }
        String loginId = (String) session.getAttribute("loginId");
        // 1. userId가 groupId랑 같은지 비교
        StudyGroup studyGroup= studyGroupService.selectStudyGroup(group_id);

        if(studyGroup == null || !loginId.equals(studyGroup.getLeader_id())){
            return map;  // 그룹 정보가 없거나, 로그인 ID가 그룹장과 일치하지 않으면 빈 맵 반환
        }

        // 2. 맞다면 목록 가져오기
        if(studyGroup.getStatus().equals(RECRUITING)){ // 2-1. 오픈 전이라면
            // (1) 가입 신청한 목록 PERM10
            List<JoinedUserInfo> joinRequestList = joinedgroupService.selectListByGroupId(group_id, JOIN_REQUEST);
            // (2) 가입 승인된 목록 PERM20
            List<JoinedUserInfo> joinApprovedList = joinedgroupService.selectListByGroupId(group_id, JOIN_APPROVED);

            map.put("joinRequestList", joinRequestList);
            map.put("joinApprovedList", joinApprovedList);
        } else if(studyGroup.getStatus().equals(IN_PROGRESS)){ // 2-2. 모집중이라면
            List<PenaltylogFetcher> penaltylogInfoList = penaltylogService.getPanaltylogByGroupIdAndUserId(group_id);
            map.put("penaltylogInfoList", penaltylogInfoList);
        }

        return map;
    }
}