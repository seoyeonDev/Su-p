package com.example.studyproject.joinedgroup;

import com.example.studyproject.member.Member;
import com.example.studyproject.studygroup.StudyGroup;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Joinedgroup {
    private StudyGroup group;
    private Member member;
    private String role;
    private String joinstatus;
    private int submission_cnt;
}