package com.example.studyproject.group;

import com.example.studyproject.member.Member;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private String group_id;    // 스터디그룹ID
    private Member leader_id;   // 스터디장ID
    private String title;       // 스터디제목
    private String name;        // 스터디명
    private String desc;        // 스터디 소개
    private String kind;        // 스터디 종류
    private boolean status;     // 활성화상태
    private int mem_cnt;        // 최대멤버수
    private Timestamp startdate;    // 스터디시작일
    private Timestamp enddate;      // 스터디종료일
    private int view_cnt;       // 조회수
    private char chk_m;         // 제출기준
    private String chk_d;       // 제출기준상세
    private int chk_cnt;        // 전체제출회수
	
    public Group() {
		// TODO Auto-generated constructor stub
	}

	public Group(String group_id, Member leader_id, String title, String name, String desc, String kind, boolean status,
			int mem_cnt, Timestamp startdate, Timestamp enddate, int view_cnt, char chk_m, String chk_d, int chk_cnt) {
		this.group_id = group_id;
		this.leader_id = leader_id;
		this.title = title;
		this.name = name;
		this.desc = desc;
		this.kind = kind;
		this.status = status;
		this.mem_cnt = mem_cnt;
		this.startdate = startdate;
		this.enddate = enddate;
		this.view_cnt = view_cnt;
		this.chk_m = chk_m;
		this.chk_d = chk_d;
		this.chk_cnt = chk_cnt;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public Member getLeader_id() {
		return leader_id;
	}

	public void setLeader_id(Member leader_id) {
		this.leader_id = leader_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getMem_cnt() {
		return mem_cnt;
	}

	public void setMem_cnt(int mem_cnt) {
		this.mem_cnt = mem_cnt;
	}

	public Timestamp getStartdate() {
		return startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	public Timestamp getEnddate() {
		return enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public int getView_cnt() {
		return view_cnt;
	}

	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
	}

	public char getChk_m() {
		return chk_m;
	}

	public void setChk_m(char chk_m) {
		this.chk_m = chk_m;
	}

	public String getChk_d() {
		return chk_d;
	}

	public void setChk_d(String chk_d) {
		this.chk_d = chk_d;
	}

	public int getChk_cnt() {
		return chk_cnt;
	}

	public void setChk_cnt(int chk_cnt) {
		this.chk_cnt = chk_cnt;
	}

	@Override
	public String toString() {
		return "Group [group_id=" + group_id + ", leader_id=" + leader_id + ", title=" + title + ", name=" + name
				+ ", desc=" + desc + ", kind=" + kind + ", status=" + status + ", mem_cnt=" + mem_cnt + ", startdate="
				+ startdate + ", enddate=" + enddate + ", view_cnt=" + view_cnt + ", chk_m=" + chk_m + ", chk_d="
				+ chk_d + ", chk_cnt=" + chk_cnt + "]";
	}
    
}
