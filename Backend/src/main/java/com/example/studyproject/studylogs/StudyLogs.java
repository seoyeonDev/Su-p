package com.example.studyproject.studylogs;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudyLogs {

    private String post_id;
    private String user_id;
    private String group_id;
    private String title;
    private String content;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
    private String file_id;
    private String img_id;

    public StudyLogs() {
		// TODO Auto-generated constructor stub
	}

	public StudyLogs(String post_id, String user_id, String group_id, String title, String content,
			LocalDateTime create_date, LocalDateTime update_date, String file_id, String img_id) {
		super();
		this.post_id = post_id;
		this.user_id = user_id;
		this.group_id = group_id;
		this.title = title;
		this.content = content;
		this.create_date = create_date;
		this.update_date = update_date;
		this.file_id = file_id;
		this.img_id = img_id;
	}

	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}

	public LocalDateTime getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(LocalDateTime update_date) {
		this.update_date = update_date;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getImg_id() {
		return img_id;
	}

	public void setImg_id(String img_id) {
		this.img_id = img_id;
	}

	@Override
	public String toString() {
		return "StudyLogs [post_id=" + post_id + ", user_id=" + user_id + ", group_id=" + group_id + ", title=" + title
				+ ", content=" + content + ", create_date=" + create_date + ", update_date=" + update_date
				+ ", file_id=" + file_id + ", img_id=" + img_id + "]";
	}


}
