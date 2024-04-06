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
	private String profileImg;
	private int failNum;
	private String lockYn;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}
	
	public Member(String userId, String password, String name, String nickname, String email, LocalDateTime joinDate,
			String profileImg, int failNum, String lockYn) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.joinDate = joinDate;
		this.profileImg = profileImg;
		this.failNum = failNum;
		this.lockYn = lockYn;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(LocalDateTime joinDate) {
		this.joinDate = joinDate;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public int getFailNum() {
		return failNum;
	}
	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}
	public String getLockYn() {
		return lockYn;
	}
	public void setLockYn(String lockYn) {
		this.lockYn = lockYn;
	}
	@Override
	public String toString() {
		return "Member [userId=" + userId + ", password=" + password + ", name=" + name + ", nickname=" + nickname
				+ ", email=" + email + ", joinDate=" + joinDate + ", profileImg=" + profileImg + ", failNum=" + failNum
				+ ", lockYn=" + lockYn + "]";
	}
	
}
