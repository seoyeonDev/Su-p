package com.example.studyproject.joinedgroup;

import com.example.studyproject.enums.JoinStatus;
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
 * @ 2024.11.16     김혜원        JoinStstus enum 추가
 */
@Mapper
public interface JoinedgroupDao {

    // 사용자별 가입한 그룹 목록
    ArrayList<Joinedgroup> selectJoinedList(String user_id);

    // 그룹 참여 신청 (참여자)
    boolean createJoinedGroup(Joinedgroup vo);

    // 그룹 삭제 시 함께 삭제
    void deleteEveryJoinedGroup(String group_id);

    // 가입 거절 & 가입 취소
    int deleteJoinedgroup(String user_id, String group_id);

    // 그룹 참여 상태 수정
    boolean updateJoinedStatus(Joinedgroup vo);

    // groupId & userId 로 불러오기
    Joinedgroup getByUserIdAndGroupId(String user_id, String group_id);

    // 조건에 따른 목록보기
    ArrayList<?> selectList(JoinStatus joinstatus, String role, String status, String user_id);
    
    // 그룹아이디로 리스트 사이즈 체크
    int selectJoinedListSize(String group_id);

    // 그룹 ID와 가입 상태에 따라 joinedgroup 목록을 조회합니다.
    ArrayList<JoinedUserInfo> selectJoinedListByGroupId(String group_id,  JoinStatus joinStatus);
}