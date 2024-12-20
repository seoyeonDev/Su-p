package com.example.studyproject.penaltylog;

import com.example.studyproject.studygroup.StudyGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Mapper
public interface PenaltylogDao {

    // penaltylog 삭제
    //@Delete("DELETE FROM penaltylog WHERE user_id = #{user_id} AND group_id = #{group_id} AND penalty_round = #{penalty_round}")
    int deletePenaltyLog(@Param("user_id") String user_id, @Param("group_id") String group_id, @Param("penalty_round") int penalty_round);

    // 유저 아이디로 penaltylog 불러오기
    ArrayList<Penaltylog> getByUserId(@Param("user_id") String user_id);

    // 그룹 아이디로 penaltylog 불러오기
    ArrayList<Penaltylog> getByGroupId(@Param("group_id") String group_id);

    // assigncycle 기준 조회
    // 기준비교
    List<Map<String,Object>> selectPenalty();

    // penaltylog 추가
    int insertPenaltyLog(Penaltylog vo, int log_count);

    // penaltylog 추가 유효성 검사
    boolean penaltyLogMultiChk(String group_id, String user_id, String penalty_round);


    // 그룹 ID에 대한 penaltylog, member, joinedgroup에 대한 정보를 조회합니다.
    List<PenaltylogFetcher> selectPenaltylogWithJoinedgroup(String group_id);

    // penaltylog 삭제
    boolean deletePenaltyLog (@Param("user_id") String user_id, @Param("group_id") String group_id);

    // 총 패널티 개수(사용자에 대한 or 사용자와 특정 스터디 그룹에 대한)
    int getPenaltyCount(String user_id, String group_id);
}

