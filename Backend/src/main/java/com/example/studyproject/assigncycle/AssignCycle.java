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

	private String group_id;
	private String startdate;
	private String enddate;
	private String assigncycle;
	
	public AssignCycle() {
		// TODO Auto-generated constructor stub
	}

	public AssignCycle(String group_id, String startdate, String enddate, String assigncycle) {
		super();
		this.group_id = group_id;
		this.startdate = startdate;
		this.enddate = enddate;
		this.assigncycle = assigncycle;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getAssigncycle() {
		return assigncycle;
	}

	public void setAssigncycle(String assigncycle) {
		this.assigncycle = assigncycle;
	}

	@Override
	public String toString() {
		return "AssignCycle [group_id=" + group_id + ", startdate=" + startdate + ", enddate=" + enddate
				+ ", assigncycle=" + assigncycle + "]";
	}
	
}
