package com.example.studyproject.member;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	private String userId;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private LocalDateTime joinDate;
	private String profile_img;
	private int failNum;
	private String lockYn;
	
}
