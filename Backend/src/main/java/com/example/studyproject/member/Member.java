package com.example.studyproject.member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long USERID;
    private String PASSWORD;
    private String NAME;
    private String NICKNAME;
    private String EMAIL;
    private Date JOINDATE;

    private String PROFILE_IMG;

    private int FAIL_NUM;
    private String LOCK_YN;
}
