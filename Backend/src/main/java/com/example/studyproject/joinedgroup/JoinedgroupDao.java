package com.example.studyproject.joinedgroup;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Class Name : JoinedgroupDao.java
 * @Description : 그룹 가입 상태 정보 Dao
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.13     김혜원        최초 생성
 * @ 2024.05.14     김혜원        그룹 가입 신청하기
 *
 */
@Mapper
public interface JoinedgroupDao {

    // 그룹 참여 신청 (참여자)
    boolean createJoinedGroup(Joinedgroup vo);


    // 가입 거절 & 가입 취소
    int deleteJoinedgroup(String user_id, String group_id);

    // 그룹 참여 상태 수정
    boolean updateJoinedStatus(Joinedgroup vo);

}