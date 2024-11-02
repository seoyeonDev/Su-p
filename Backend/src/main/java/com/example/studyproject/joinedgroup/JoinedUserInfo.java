package com.example.studyproject.joinedgroup;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JoinedUserInfo {
    private String group_id;
    private String user_id;
    private String role;
    private String joinstatus;
    private int submission_cnt;
    private String name;
    private String nickname;
    private String email;
    private String profile_img;
}
