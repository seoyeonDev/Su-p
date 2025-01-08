package com.example.studyproject.joinedgroup;

import com.example.studyproject.enums.JoinStatus;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Joinedgroup {
    private String group_id;
    private String user_id;
    private JoinStatus role;
    private JoinStatus joinstatus;
    private int submission_cnt;
}