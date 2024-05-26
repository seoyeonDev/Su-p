package com.example.studyproject.codemn;

import com.example.studyproject.codemn.CodeM;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CodeMnDao {

    // 기존 분류 조회 (분류 추가 가능여부 확인/유효성 검사)
    boolean chkCdType(String cd_type);

    // 분류 조회
    ArrayList<CodeM> selectCdType(String cd_type);    // 코드분류 검색 파라미터

    // 분류 추가
    void insertCdType(CodeM codeM);

    // 분류 수정
    void updateCdType(CodeM codeM);

    // 분류 삭제
    int deleteCdType(String cd_type);

    /**
     * 상세코드 관
     * @param cd_type
     * @return
     */

    // 분류 삭제 시 상세코드 전체삭제
    int deleteAllCommCd(String cd_type);

    // 상세코드 조회
    ArrayList<CodeD> selectCommCd(String cd_type);

    // 상세코드 값 중복조회
    boolean chkCommCd(String comm_cd);

    // 상세코드 추가
    void insertCommCd(CodeD codeD);

    // 상세코드 수정
    void updateCommCd(CodeD codeD);

    // 상세코드 하나삭제
    int deleteOneCommCd(String comm_cd, String cd_type);
}
