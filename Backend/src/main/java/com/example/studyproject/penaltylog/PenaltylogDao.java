package com.example.studyproject.penaltylog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PenaltylogDao {


    // penaltylog 삭제
    //@Delete("DELETE FROM penaltylog WHERE user_id = #{user_id} AND group_id = #{group_id} AND penalty_round = #{penalty_round}")
    int deletePenaltyLog(@Param("user_id") String user_id, @Param("group_id") String group_id, @Param("penalty_round") int penalty_round);

}
