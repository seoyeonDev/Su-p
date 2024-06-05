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

	private String user_id;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private LocalDateTime join_date;
	private String profile_img;
	private int fail_num;
	private String lock_yn;
	private String authorization;
	
//	public Member() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public Member(String user_id, String password, String name, String nickname, String email, LocalDateTime join_date,
//			String profile_img, int fail_num, String lock_yn) {
//		this.user_id = user_id;
//		this.password = password;
//		this.name = name;
//		this.nickname = nickname;
//		this.email = email;
//		this.join_date = join_date;
//		this.profile_img = profile_img;
//		this.fail_num = fail_num;
//		this.lock_yn = lock_yn;
//	}
//
//	public String getUser_id() {
//		return user_id;
//	}
//
//	public void setUser_id(String user_id) {
//		this.user_id = user_id;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getNickname() {
//		return nickname;
//	}
//
//	public void setNickname(String nickname) {
//		this.nickname = nickname;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public LocalDateTime getJoin_date() {
//		return join_date;
//	}
//
//	public void setJoin_date(LocalDateTime join_date) {
//		this.join_date = join_date;
//	}
//
//	public String getProfile_img() {
//		return profile_img;
//	}
//
//	public void setProfile_img(String profile_img) {
//		this.profile_img = profile_img;
//	}
//
//	public int getFail_num() {
//		return fail_num;
//	}
//
//	public void setFail_num(int fail_num) {
//		this.fail_num = fail_num;
//	}
//
//	public String getLock_yn() {
//		return lock_yn;
//	}
//
//	public void setLock_yn(String lock_yn) {
//		this.lock_yn = lock_yn;
//	}
//
//	@Override
//	public String toString() {
//		return "Member [user_id=" + user_id + ", password=" + password + ", name=" + name + ", nickname=" + nickname
//				+ ", email=" + email + ", join_date=" + join_date + ", profile_img=" + profile_img + ", fail_num="
//				+ fail_num + ", lock_yn=" + lock_yn + "]";
//	}
//
}
