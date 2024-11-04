package com.example.studyproject.penaltylog;

import com.example.studyproject.studygroup.StudyGroup;
import com.example.studyproject.studygroup.StudyGroupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @ 2024.11.02     이서연        chkPenalty 생성
 */


@RestController
@RequestMapping("/penaltylog")
@SpringBootApplication
@EnableScheduling
public class PenaltylogController {

    @Autowired
    private PenaltylogService penaltyLogService;

    @Autowired
    private StudyGroupService studyGroupService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(PenaltylogController.class);



    /**
     * penaltylog 삭제
     *
     * @param user_id
     * @param group_id
     * @param penalty_round
     * @return
     */
    @DeleteMapping("/{user_id}/{group_id}/{penalty_round}")
    public Map deletePenaltyLog(@PathVariable String user_id, @PathVariable String group_id, @PathVariable int penalty_round){
        Map map = new HashMap();

        try{
            int deleteSuccess = penaltyLogService.deletePenaltyLog(user_id, group_id, penalty_round);
            map.put("success",deleteSuccess);
            // 1이면 성공, 0이면 실패
        } catch(Exception e){
            e.printStackTrace();
        }


        return map;
    }

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
    }

    /**
     * 테스트용 수동 컨트롤러
     * 제출개수 확인 후 비교, 기준 미달 시 penalty 추가
     * 매일 00시 실행
     *
     * 2024.11.02
     * @Author 이서연
     */

    @GetMapping("/penaltyScheduler")
    public void penaltyScheduler(){
        penaltyLogService.penaltyScheduler();
    }



}
