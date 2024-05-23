package com.example.studyproject.joinedgroup;

import com.example.studyproject.group.Group;
import com.example.studyproject.member.Member;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Joinedgroup {
    private Group group_id;
    private Member user_id;
    private String role;
    private String joinstatus;
    private int submission_cnt;
}