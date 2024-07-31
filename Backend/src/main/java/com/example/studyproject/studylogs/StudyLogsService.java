package com.example.studyproject.studylogs;

import com.example.studyproject.joinedgroup.Joinedgroup;
import com.example.studyproject.joinedgroup.JoinedgroupService;
import com.example.studyproject.member.Member;
import com.example.studyproject.member.MemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class StudyLogsService {
    // log4j2 로그 찍기
    public static final Logger LOGGER = LogManager.getLogger(StudyLogsService.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private JoinedgroupService joinedgroupService;

    private final StudyLogsDao studyLogsDao;

    public StudyLogsService(StudyLogsDao studyLogsDao) {
        this.studyLogsDao = studyLogsDao;
    }

    // 스터디로그 상세 조회
    public StudyLogs selectStudyLogs(String post_id) {
        return studyLogsDao.selectStudyLogs(post_id);
    }

    // 결과물 추가
    public void insertLogs(StudyLogs vo) {
        Member memberVo = memberService.getMemberById(vo.getUser_id());
        Joinedgroup joinedgroupVo = joinedgroupService.getByUserIdAndGroupId(vo.getUser_id(), vo.getGroup_id());

        if (joinedgroupVo != null) {
            studyLogsDao.insertLogs(vo);
            LOGGER.info("================ " + "StudyLogs insert success");
        } else {
            // insert 실패
            LOGGER.info("================ " + "StudyLogs insert fail");
        }

    }

    public String insertStudyLogs(StudyLogs vo) {

        Member memberVo = memberService.getMemberById(vo.getUser_id());
        Joinedgroup joinedgroupVo = joinedgroupService.getByUserIdAndGroupId(vo.getUser_id(), vo.getGroup_id());

        if (joinedgroupVo != null) {
            studyLogsDao.insertStudyLogs(vo);

            LOGGER.info("================ " + "StudyLogs insert success");
        } else {
            LOGGER.info("================ " + "StudyLogs insert fail");
        }

        return vo.getPost_id(); // 자동으로 설정된 post_id를 반환
    }


    // 결과물 업데이트
    public void updateStudyLogs(StudyLogs vo) {
        studyLogsDao.updateStudyLogs(vo);
    }

    // 스터디로그 
    public ArrayList<StudyLogs> selectList() {
        return studyLogsDao.selectList();

    }
}