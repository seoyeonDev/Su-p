package com.example.studyproject.penaltylog;

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
public class Penaltylog {

	private String user_id;
	private String group_id;
	private String title;
	private String logcontent;
	private int penalty_round;
	
//	public Penaltylog() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public Penaltylog(String user_id, String group_id, String title, String logcontent, LocalDateTime penaltydate) {
//		super();
//		this.user_id = user_id;
//		this.group_id = group_id;
//		this.title = title;
//		this.logcontent = logcontent;
//		this.penaltydate = penaltydate;
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
//	public String getGroup_id() {
//		return group_id;
//	}
//
//	public void setGroup_id(String group_id) {
//		this.group_id = group_id;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public String getLogcontent() {
//		return logcontent;
//	}
//
//	public void setLogcontent(String logcontent) {
//		this.logcontent = logcontent;
//	}
//
//	public LocalDateTime getPenaltydate() {
//		return penaltydate;
//	}
//
//	public void setPenaltydate(LocalDateTime penaltydate) {
//		this.penaltydate = penaltydate;
//	}
//
//	@Override
//	public String toString() {
//		return "Penaltylog [user_id=" + user_id + ", group_id=" + group_id + ", title=" + title + ", logcontent="
//				+ logcontent + ", penaltydate=" + penaltydate + "]";
//	}
	
}
