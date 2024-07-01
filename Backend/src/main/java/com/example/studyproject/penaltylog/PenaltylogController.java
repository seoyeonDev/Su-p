package com.example.studyproject.penaltylog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Class Name : PenaltylogController.java
 * @Description : PenaltylogController CONTROLLER
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.01     봉선호        최초 생성
 *
 */

@RestController
@RequestMapping("/penaltylog")
public class PenaltylogController {

    @Autowired
    private PenaltylogService penaltyLogService;

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
}
