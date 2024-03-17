package com.example.studyproject.mapper;

import com.example.studyproject.member.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface memberMapper {
    Member selectMember();
}
