package com.example.studyproject.codemn;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CodeD {
    private String cd_type;     // 코드 분류
    private String comm_cd;     // 상세코드
    private String comm_cdnm;   // 상세코드명
}
