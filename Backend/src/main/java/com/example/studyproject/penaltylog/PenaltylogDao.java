package com.example.studyproject.penaltylog;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface PenaltylogDao {
    // assigncycle 기준 조회
    // 기준비교
    List<Map<String,Object>> selectPenalty(String group_id);

    //

}
