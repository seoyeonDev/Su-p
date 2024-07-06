package com.example.studyproject.penaltylog;

import com.example.studyproject.studygroup.StudyGroup;
import com.example.studyproject.studygroup.StudyGroupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Class Name : PenaltylogController.java
 * @Description : PenaltylogController CONTROLLER
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.01     봉선호        최초 생성
 * @ 2024.07.01     김혜원        getByUserId, getByGroupId 생성
 */

@RestController
@RequestMapping("/penaltylog")
public class PenaltylogController {

    @Autowired
    private PenaltylogService penaltyLogService;

    @Autowired
    private StudyGroupService studyGroupService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(PenaltylogController.class);


    /**
     * 유저 아이디로 penaltylog 불러오기
     *
     * @param user_id
     * @return
     */
    @GetMapping("/userId/{user_id}")
    public Map getByUserId(@PathVariable String user_id){
        Map map = new HashMap();
        List<Penaltylog> penaltylogList = penaltyLogService.getByUserId(user_id);
        map.put("penaltylogList",penaltylogList);

        return map;
    }

    /**
     * 그룹 아이디로 penaltylog 불러오기
     *
     * @param group_id
     * @param request
     * @return
     */
    @GetMapping("/groupId/{group_id}")
    public Map getByGroupId(@PathVariable String group_id, HttpServletRequest request){
        Map map = new HashMap();

        HttpSession session = request.getSession();

        if(session == null){
            return null;
        } else{
            String loginId = (String) session.getAttribute("loginId");
            StudyGroup studyGroup = studyGroupService.selectStudyGroup(group_id);
            // studygroup leader_id와 현재 아이디와 일치하는지 검사
            if(loginId.equals(studyGroup.getLeader_id())){
                List<Penaltylog> penaltylogList = penaltyLogService.getByGroupId(group_id);
                map.put("penaltylogList",penaltylogList);
            } else {
                map.put("penaltylogList",null);
            }
        }

        return map;

    @GetMapping("/chkPenalty")
    public String chkPenalty (String group_id){
        return penaltyLogService.chkPenalty(group_id);

    }
}
