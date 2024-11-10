package com.example.studyproject.penaltylog;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PenaltylogFetcher {
    private String user_id;
    private String name;
    private String nickname;
    private String profile_img;
    private String group_id;
    private int submission_cnt;
    private List<PenaltylogSummary> penaltylogList;

}
