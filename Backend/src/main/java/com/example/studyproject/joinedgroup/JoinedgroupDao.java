package com.example.studyproject.joinedgroup;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @Class Name : JoinedgroupDao.java
 * @Description : 그룹 가입 상태 정보 Dao
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.13     김혜원        최초 생성
 * @ 2024.05.14     김혜원        그룹 가입 신청하기
 * @ 2024.05.27     김혜원        get 메서드 추가
 * @ 2024.06.10     이서연        사용자별 가입한 그룹 목록 메서드 추가
 * @ 2024.06.24     이서연        조건에 따른 목록보기
 */
@Mapper
public interface JoinedgroupDao {

    // 사용자별 가입한 그룹 목록
    ArrayList<Joinedgroup> selectJoinedList(String user_id);

    // 그룹 참여 신청 (참여자)
    boolean createJoinedGroup(Joinedgroup vo);


    // 가입 거절 & 가입 취소
    int deleteJoinedgroup(String user_id, String group_id);

    // 그룹 참여 상태 수정
    boolean updateJoinedStatus(Joinedgroup vo);

    // groupId & userId 로 불러오기
    Joinedgroup getByUserIdAndGroupId(String user_id, String group_id);

    // 조건에 따른 목록보기
    ArrayList<Joinedgroup> selectList(String joinstatus, String role, String status, String user_id);
}