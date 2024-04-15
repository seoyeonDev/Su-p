package com.example.studyproject.group;


import com.example.studyproject.member.Member;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private String group_id;    // 스터디그룹ID
    private Member leader_id;   // 스터디장ID
    private String title;       // 스터디제목
    private String name;        // 스터디명
    private String desc;        // 스터디 소개
    private String kind;        // 스터디 종류
    private boolean status;     // 활성화상태
    private int mem_cnt;        // 최대멤버수
    private Timestamp startdate;    // 스터디시작일
    private Timestamp enddate;      // 스터디종료일
    private int view_cnt;       // 조회수
    private char chk_m;         // 제출기준
    private String chk_d;       // 제출기준상세
    private int chk_cnt;        // 전체제출회수
}
