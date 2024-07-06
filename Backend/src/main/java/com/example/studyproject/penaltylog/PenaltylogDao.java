package com.example.studyproject.penaltylog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface PenaltylogDao {

    // 유저 아이디로 penaltylog 불러오기
    ArrayList<Penaltylog> getByUserId(@Param("user_id") String user_id);

    // 그룹 아이디로 penaltylog 불러오기
    ArrayList<Penaltylog> getByGroupId(@Param("group_id") String group_id);

    // assigncycle 기준 조회
    // 기준비교
    List<Map<String,Object>> selectPenalty(String group_id);

}