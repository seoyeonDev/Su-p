package com.example.studyproject.codemn;

import com.example.studyproject.codemn.CodeMn;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CodeMnDao {

    // 기존 분류 조회 (분류 추가 가능여부 확인/유효성 검사)
    boolean chkCdType(String cd_type);

    // 분류 조회
    ArrayList<CodeMn> selectCdType(String cd_type);    // 코드분류 검색 파라미터

    // 분류 추가
    void insertCdType(CodeMn codeMn);

    // 분류 수정
    CodeMn updateCdType(CodeMn codeMn);

    // 분류 삭제
    int deleteCdType(String cd_type);

    // 분류 삭제 시 상세코드 전체삭제
    int deleteAllCommCd(String cd_type);

    // 상세코드 조회
    CodeMn selectCommCd(String cd_type);    // 세부 코드는 검색 없이 구현 계획 (미정)

    // 상세코드 추가
    boolean insertCommCd(CodeMn vo);

    // 상세코드 수정
    CodeMn updateCommCd(CodeMn vo);

    // 상세코드 하나삭제
    boolean deleteOneCommCd(CodeMn vo);
}
