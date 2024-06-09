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


}
