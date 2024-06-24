package com.example.studyproject.studygroup;

/**
 * @Class Name : StudyGroup.java
 * @Description : StudyGroup VO
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.05.01     봉선호        최초 생성
 * @ 2024.05.17		봉선호		멤버변수 추가 및 부분 수정
 *
 */

import com.example.studyproject.member.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudyGroup {
    private String group_id;    	// 스터디그룹ID
    private String leader_id;   	// 스터디장ID
    private String title;       	// 스터디제목
    private String name;			// 스터디명
    private String study_desc;        	// 스터디 소개
    private String kind;        	// 스터디 종류 => 코드 테이블 참조
    private String status;   	  	// 활성화상태 => 코드 테이블 참조
    private int mem_cnt;        	// 최대멤버수
    private LocalDateTime startdate;	// 스터디시작일
    private LocalDateTime enddate;  	// 스터디종료일
    private int view_cnt;       	// 조회수
    private String chk_m;   	    // 제출기준 => 코드 테이블 참조
    private int chk_min_cnt;		// 제출최소횟수
    private int chk_total_cnt;		// 전체제출회수
    private int penalty;			// 페널티기준
	
//    public StudyGroup() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public StudyGroup(String group_id, String leader_id, String title, String name, String study_desc, String kind,
//			String status, int mem_cnt, LocalDateTime startdate, LocalDateTime enddate, int view_cnt, String chk_m,
//			int chk_min_cnt, int chk_total_cnt, int penalty) {
//		super();
//		this.group_id = group_id;
//		this.leader_id = leader_id;
//		this.title = title;
//		this.name = name;
//		this.study_desc = study_desc;
//		this.kind = kind;
//		this.status = status;
//		this.mem_cnt = mem_cnt;
//		this.startdate = startdate;
//		this.enddate = enddate;
//		this.view_cnt = view_cnt;
//		this.chk_m = chk_m;
//		this.chk_min_cnt = chk_min_cnt;
//		this.chk_total_cnt = chk_total_cnt;
//		this.penalty = penalty;
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
//	public String getLeader_id() {
//		return leader_id;
//	}
//
//	public void setLeader_id(String leader_id) {
//		this.leader_id = leader_id;
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
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//
//	public String getStudy_desc() {
//		return study_desc;
//	}
//
//	public void setStudy_desc(String study_desc) {
//		this.study_desc = study_desc;
//	}
//
//	public String getKind() {
//		return kind;
//	}
//
//	public void setKind(String kind) {
//		this.kind = kind;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//	public int getMem_cnt() {
//		return mem_cnt;
//	}
//
//	public void setMem_cnt(int mem_cnt) {
//		this.mem_cnt = mem_cnt;
//	}
//
//	public LocalDateTime getStartdate() {
//		return startdate;
//	}
//
//	public void setStartdate(LocalDateTime startdate) {
//		this.startdate = startdate;
//	}
//
//	public LocalDateTime getEnddate() {
//		return enddate;
//	}
//
//	public void setEnddate(LocalDateTime enddate) {
//		this.enddate = enddate;
//	}
//
//	public int getView_cnt() {
//		return view_cnt;
//	}
//
//	public void setView_cnt(int view_cnt) {
//		this.view_cnt = view_cnt;
//	}
//
//	public String getChk_m() {
//		return chk_m;
//	}
//
//	public void setChk_m(String chk_m) {
//		this.chk_m = chk_m;
//	}
//
//	public int getChk_min_cnt() {
//		return chk_min_cnt;
//	}
//
//	public void setChk_min_cnt(int chk_min_cnt) {
//		this.chk_min_cnt = chk_min_cnt;
//	}
//
//	public int getChk_total_cnt() {
//		return chk_total_cnt;
//	}
//
//	public void setChk_total_cnt(int chk_total_cnt) {
//		this.chk_total_cnt = chk_total_cnt;
//	}
//
//	public int getPenalty() {
//		return penalty;
//	}
//
//	public void setPenalty(int penalty) {
//		this.penalty = penalty;
//	}
//
//	@Override
//	public String toString() {
//		return "StudyGroup [group_id=" + group_id + ", leader_id=" + leader_id + ", title=" + title + ", name=" + name
//				+ ", study_desc=" + study_desc + ", kind=" + kind + ", status=" + status + ", mem_cnt=" + mem_cnt + ", startdate="
//				+ startdate + ", enddate=" + enddate + ", view_cnt=" + view_cnt + ", chk_m=" + chk_m + ", chk_min_cnt="
//				+ chk_min_cnt + ", chk_total_cnt=" + chk_total_cnt + ", penalty=" + penalty + "]";
//	}
}
