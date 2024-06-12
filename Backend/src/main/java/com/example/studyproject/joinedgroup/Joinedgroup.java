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
    private String group_id;
    private String user_id;
    private String role;
    private String joinstatus;
    private int submission_cnt;
}