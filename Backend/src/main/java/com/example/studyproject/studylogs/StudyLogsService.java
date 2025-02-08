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

    // 결과물 추가
    public String insertStudyLogs(StudyLogs vo) {
        Joinedgroup joinedgroupVo = joinedgroupService.getByUserIdAndGroupId(vo.getUser_id(), vo.getGroup_id());
        Long post_id = studyLogsDao.getNextPostIdIfPrefixExists(vo.getGroup_id());

        if (post_id == 0) {
            // 초기 studylogs id 지정
            vo.setPost_id(String.valueOf(vo.getGroup_id()) + "00001");
        } else {
            vo.setPost_id(String.valueOf(post_id));
        }

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

    // 회원 탈퇴 : 알수 없음으로 변경
    public void anonymizeUserId(String user_id) {
        studyLogsDao.anonymizeUserId(user_id);
    }

    // 스터디로그 
    public ArrayList<StudyLogs> selectList() {
        return studyLogsDao.selectList();

    }

    // 그룹별, 사용자별 studylog 전체 목록
    public ArrayList<StudyLogs> selectStudyLogsListByGroup(String group_id, String user_id){
        return studyLogsDao.selectStudyLogsListByGroup(group_id, user_id);
    }

    // 스터디 메인 글 LIMIT 5
    public ArrayList<StudyLogs> studyMainListLimit5 (String group_id){
        return studyLogsDao.studyMainListLimit5(group_id);
    }
}