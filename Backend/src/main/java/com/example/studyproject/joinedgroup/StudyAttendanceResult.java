package com.example.studyproject.joinedgroup;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudyAttendanceResult {
    private int penaltyCount;       // 패널티 개수
    private double attendance;  // 출석률
}
