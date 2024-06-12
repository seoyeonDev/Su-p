package com.example.studyproject.studylogs;


import com.example.studyproject.studygroup.StudyGroupController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Class Name : PenaltylogController.java
 * @Description : PenaltylogController CONTROLLER
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.06.12     김혜원        주석 수정
 *
 */

@RestController
@RequestMapping("/studylogs")
public class StudyLogsController {

    @Autowired
    private StudyLogsService studyLogsService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(StudyLogsController.class);

    // 결과물 신규추가
    @PostMapping("/insert")
    public void insertLogs(@RequestBody StudyLogs vo){
        studyLogsService.insertLogs(vo);
    }

    /**
     * 결과물 업데이트
     * @param post_id - 결과물 id
     * @param vo - 결과물 vo
     */
    @PutMapping("/update/{post_id}")
    public void updateLogs(@PathVariable String post_id, @RequestBody StudyLogs vo) {

        StudyLogs studyLogs = studyLogsService.selectStudyLogs(post_id);
        LOGGER.info("StudyLogs updated: " + studyLogs);

        if (studyLogs == null) {
            LOGGER.error("StudyLogs not found with post_id: " + post_id);
            throw new RuntimeException("StudyLogs not found with post_id: " + post_id);
        }

        // 기존의 게시물을 업데이트
        // 제목, 내용, 수정일은 db에서 now로 처리
        studyLogs.setTitle(vo.getTitle());
        studyLogs.setContent(vo.getContent());

        studyLogsService.updateStudyLogs(vo);
        LOGGER.info("StudyLogs updated: " + studyLogs);

    }



}
