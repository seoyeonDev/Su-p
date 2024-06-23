package com.example.studyproject.assigncycle;

/**
 * @Class Name : AssginCycle.java
 * @Description : AssginCycle VO
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.06.23     봉선호        최초 생성
 *
 */

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AssignCycle {

	private LocalDateTime startdate;
	private LocalDateTime enddate;
	private String group_id;
	private int assigncycle;
	
	public AssignCycle() {
		// TODO Auto-generated constructor stub
	}

	public AssignCycle(LocalDateTime startdate, LocalDateTime enddate, String group_id, int assigncycle) {
		super();
		this.startdate = startdate;
		this.enddate = enddate;
		this.group_id = group_id;
		this.assigncycle = assigncycle;
	}

	public LocalDateTime getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDateTime startdate) {
		this.startdate = startdate;
	}

	public LocalDateTime getEnddate() {
		return enddate;
	}

	public void setEnddate(LocalDateTime enddate) {
		this.enddate = enddate;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public int getAssigncycle() {
		return assigncycle;
	}

	public void setAssigncycle(int assigncycle) {
		this.assigncycle = assigncycle;
	}

	@Override
	public String toString() {
		return "AssginCycle [startdate=" + startdate + ", enddate=" + enddate + ", group_id=" + group_id
				+ ", assigncycle=" + assigncycle + "]";
	}
	
}
