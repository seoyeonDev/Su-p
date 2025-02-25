package com.example.studyproject.joinedgroup;

import com.example.studyproject.assigncycle.AssignCycleService;
import com.example.studyproject.enums.JoinStatus;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collections;
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
 * @ 2024.11.16     김혜원        JoinStatus enum 추가
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

    @Autowired
    private AssignCycleService assignCycleService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(JoinedgroupController.class);

    //public static final String JOIN_REQUEST = "PERM10"; // 가입대기
    //public static final String JOIN_APPROVED = "PERM20"; // 가입승인
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
     * study group 가입하기
     * @param user_id
     * @param group_id
     * @param role_status - 그룹장 or 참여자
     */
    @PostMapping("/join")
    public Map joinGroup(@RequestParam String user_id, @RequestParam String group_id, @RequestParam JoinStatus role_status){
        Map<String, Object> response = new HashMap<>();
        LOGGER.info("================ joinedgroup join, user_id :" + user_id + ", group_id :" + group_id + ", role_status :" + role_status);

        Member memberVo = memberService.getMemberById(user_id);
        // group getGroupById 도 만들면 검사하는 logic 넣기

        boolean success;

        if(memberVo != null){
            success = joinedgroupService.createJoinedGroup(user_id, group_id, role_status);
        } else {
            success = false;
        }

        if(success){
            LOGGER.info("================ join success");
            response.put("status", "success");
        } else {
            LOGGER.info("================ join failed");
            response.put("status", "fail");
        }

        return response;
    }

    /**
     * studygroup 가입 취소 및 탈퇴하기
     * @param user_id
     * @param group_id
     */
    @DeleteMapping("/user/{user_id}/group/{group_id}")
    public void deleteJoinedgroup(@PathVariable("user_id") String user_id, @PathVariable("group_id") String group_id){
        Joinedgroup joinedgroupVo = joinedgroupService.getByUserIdAndGroupId(user_id, group_id);

        int success = 0;

        LOGGER.info("================ deleteJoinedgroup joiendgroupVo : "+joinedgroupVo);

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
     * @param user_id
     * @param group_id
     * @param status
     * @return
     */
    @PutMapping("/updateStatus")
    public ResponseEntity<Map<String, Object>> updateJoinStatus(String user_id, String group_id, JoinStatus status){
        Map map = new HashMap();
        LOGGER.info("================ joinedgroup updateStatus");

        Joinedgroup joinedgroupVo = joinedgroupService.getByUserIdAndGroupId(user_id, group_id);
        // 나중 : status가 code 테이블에 있는지 검사 TODO.
        boolean success = false;

        // 값이 있을 때에만 수정 가능
        if(joinedgroupVo != null){
            success = joinedgroupService.updateJoinedStatus(joinedgroupVo.getGroup_id(),joinedgroupVo.getUser_id(), status);
        }

        if(success){
            LOGGER.info("================ join update success");
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } else {
            LOGGER.info("================ join update failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("success", false));
        }
    }

    // 조건에 따른 목록보기
    @GetMapping("/selectList")
    public Map selectList(JoinStatus joinstatus, String role, String status, HttpServletRequest request){
        HttpSession session = request.getSession();
        Map map = new HashMap();
        if (session == null){
            return null;
        } else {
            String loginId = (String) session.getAttribute("loginId");
            System.out.println(loginId + "  loginId");
//            loginId = "sooyeon275";
            ArrayList<?> list = joinedgroupService.selectList(joinstatus, role, status, loginId);
            map.put("list",list);
//            ResponseEntity.ok(list);
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
    public Map selectGroupOwnerJoinedList(HttpServletRequest request, String group_id, String user_id) {
        HttpSession session = request.getSession();
        Map map = new HashMap();
        if (session == null) {
            return null;
        }

        // 1. userId가 groupId랑 같은지 비교
        StudyGroup studyGroup= studyGroupService.selectStudyGroup(group_id);

        if(studyGroup == null || !user_id.equals(studyGroup.getLeader_id())){
            return map;  // 그룹 정보가 없거나, 로그인 ID가 그룹장과 일치하지 않으면 빈 맵 반환
        }

        // 2. 맞다면 목록 가져오기
        if(studyGroup.getStatus().equals(RECRUITING)){ // 2-1. 오픈 전이라면
            // (1) 가입 신청한 목록 PERM10
            List<JoinedUserInfo> joinRequestList = joinedgroupService.selectListByGroupId(group_id, JoinStatus.PERM10);
            // (2) 가입 승인된 목록 PERM20
            List<JoinedUserInfo> joinApprovedList = joinedgroupService.selectListByGroupId(group_id, JoinStatus.PERM20);

            map.put("joinRequestList", joinRequestList);
            map.put("joinApprovedList", joinApprovedList);
        } else if(studyGroup.getStatus().equals(IN_PROGRESS)){ // 2-2. 오픈 후라면
            List<PenaltylogFetcher> penaltylogInfoList = penaltylogService.getPanaltylogByGroupIdAndUserId(group_id);
            map.put("penaltylogInfoList", penaltylogInfoList);
        }

        return map;
    }

    /**
     * 전체 스터디의 평균 출석률 구하기
     * @param user_id
     * @return 출석율
     */
    @GetMapping("/overallAttendance")
    public ResponseEntity<?> getOverallAttendance(String user_id){
        try {
            // 출석률 계산
            double allAttendance = joinedgroupService.getAttendanceCalculation(user_id);

            Map<String, Object> response = new HashMap<>();
            response.put("attendance", allAttendance);
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 오류 발생 시 Map으로 반환
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error calculating attendance");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * 특정 스터디의 평균 출석률
     * @param user_id
     * @param group_id
     * @return 출석율 & 패널티 개수
     */
    @GetMapping("/individualStudyAttendance")
    public ResponseEntity<?> getIndividualStudyAttendance(String user_id, String group_id){
        try {
            Map<String, Object> response = new HashMap<>();
            // 특정 스터디의 출석률 계산
            StudyAttendanceResult studyAttendanceResult = joinedgroupService.getStudyAttendance(user_id, group_id);
            response.put("status", "success");
            response.put("studyAttendanceResult", studyAttendanceResult);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 오류 발생 시 Map으로 반환
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Error calculating attendance");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/detailGroupAttendance")
    public List<Map<String,Object>> getDetailGroupAttendance (String user_id, String group_id){
        return assignCycleService.getDetailGroupAttendance(user_id, group_id);
    }
}