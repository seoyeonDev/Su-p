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

//    public Joinedgroup() {
//		// TODO Auto-generated constructor stub 
//	}
//
//	public Joinedgroup(String group_id, String user_id, String role, String joinstatus, int submission_cnt) {
//		super();
//		this.group_id = group_id;
//		this.user_id = user_id;
//		this.role = role;
//		this.joinstatus = joinstatus;
//		this.submission_cnt = submission_cnt;
//	}
//
//	public String getGroup_id() {
//		return group_id;
//	}
//
//	public void setGroup_id(String group_id) {
//		this.group_id = group_id;
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
//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}
//
//	public String getJoinstatus() {
//		return joinstatus;
//	}
//
//	public void setJoinstatus(String joinstatus) {
//		this.joinstatus = joinstatus;
//	}
//
//	public int getSubmission_cnt() {
//		return submission_cnt;
//	}
//
//	public void setSubmission_cnt(int submission_cnt) {
//		this.submission_cnt = submission_cnt;
//	}
//
//	@Override
//	public String toString() {
//		return "Joinedgroup [group_id=" + group_id + ", user_id=" + user_id + ", role=" + role + ", joinstatus="
//				+ joinstatus + ", submission_cnt=" + submission_cnt + "]";
//	}
    
}