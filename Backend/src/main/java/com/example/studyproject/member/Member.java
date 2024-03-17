package com.example.studyproject.member;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private String USERID;
    private String PASSWORD;
    private String NAME;
    private String NICKNAME;
    private String EMAIL;

    private LocalDateTime JOINDATE = LocalDateTime.now();

    @Column(nullable = true)
    private String PROFILE_IMG;

    private int FAIL_NUM = 0;
    private String LOCK_YN = "N";
}
