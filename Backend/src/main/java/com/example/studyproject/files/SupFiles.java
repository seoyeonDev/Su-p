package com.example.studyproject.files;

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
public class SupFiles {
	private String file_seq;
	private String file_id;
	private String file_name;
	private String file_ext;
	private String ins_id;
	private LocalDateTime ins_date;
	
//	public SupFiles() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public SupFiles(String file_seq, String file_id, String file_name, String file_ext, String ins_id,
//			LocalDateTime ins_date) {
//		super();
//		this.file_seq = file_seq;
//		this.file_id = file_id;
//		this.file_name = file_name;
//		this.file_ext = file_ext;
//		this.ins_id = ins_id;
//		this.ins_date = ins_date;
//	}
//
//	public String getFile_seq() {
//		return file_seq;
//	}
//
//	public void setFile_seq(String file_seq) {
//		this.file_seq = file_seq;
//	}
//
//	public String getFile_id() {
//		return file_id;
//	}
//
//	public void setFile_id(String file_id) {
//		this.file_id = file_id;
//	}
//
//	public String getFile_name() {
//		return file_name;
//	}
//
//	public void setFile_name(String file_name) {
//		this.file_name = file_name;
//	}
//
//	public String getFile_ext() {
//		return file_ext;
//	}
//
//	public void setFile_ext(String file_ext) {
//		this.file_ext = file_ext;
//	}
//
//	public String getIns_id() {
//		return ins_id;
//	}
//
//	public void setIns_id(String ins_id) {
//		this.ins_id = ins_id;
//	}
//
//	public LocalDateTime getIns_date() {
//		return ins_date;
//	}
//
//	public void setIns_date(LocalDateTime ins_date) {
//		this.ins_date = ins_date;
//	}
//
//	@Override
//	public String toString() {
//		return "SupFiles [file_seq=" + file_seq + ", file_id=" + file_id + ", file_name=" + file_name + ", file_ext="
//				+ file_ext + ", ins_id=" + ins_id + ", ins_date=" + ins_date + "]";
//	}
	
}
