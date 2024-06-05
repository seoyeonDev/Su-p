package com.example.studyproject.penaltylog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
 *
 */

@RestController
@RequestMapping("/penaltylog")
public class PenaltylogController {

    @Autowired
    private PenaltylogService penaltyLogService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(PenaltylogController.class);
	
}
