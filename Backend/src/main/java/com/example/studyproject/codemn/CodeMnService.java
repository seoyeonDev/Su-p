package com.example.studyproject.codemn;


import com.example.studyproject.codemn.CodeM;
import com.example.studyproject.codemn.CodeMnDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CodeMnService {

    // log4j2 로그 찍기
    public static final Logger LOGGER = LogManager.getLogger(CodeMnService.class);

    private final CodeMnDao codeMnDao;

    public CodeMnService(CodeMnDao codeMnDao) {
        this.codeMnDao = codeMnDao;
    }


    /**
     * 코드 대분류
     * @table code_m
     * @param cd_type, CodeM
     * @return
     */
    // 분류 조회
    public ArrayList<CodeM> selectCdType(String cd_type) {

        return codeMnDao.selectCdType(cd_type);
    };

    // 분류 추가
    public boolean insertCdType(CodeM codeM){
        // 중복 검사
        if (!codeMnDao.chkCdType(codeM.getCd_type())){
            return false;
        }
        codeMnDao.insertCdType(codeM);
        return true;
    }

    // 분류 수정
    public void updateCdType(CodeM codeM){
        codeMnDao.updateCdType(codeM);
    }

    // 분류 삭제
    public boolean deleteCdType(String cd_type){
        int affectedRow = codeMnDao.deleteCdType(cd_type);
        int affectedDetailRow = codeMnDao.deleteAllCommCd(cd_type);

        LOGGER.info(affectedRow + " 개의 코드분류와 " + affectedDetailRow + "개의 상세코드가 삭제되었습니다.");
        return affectedRow > 0;
    }


    /**
     * 코드 소분류
     * @table code_d
     * @return
     */

    // 분류 삭제 시 상세코드 전체삭제
//    public int deleteAllCommCd(String cd_type){
//        return codeMnDao.deleteAllCommCd(cd_type);
//    }

    // 상세코드 조회
    public ArrayList<CodeD> selectCommCd(String comm_cd){
        return codeMnDao.selectCommCd(comm_cd);
    }

    // 상세코드 추가
    public boolean insertCommCd(CodeD codeD) {
        if (!codeMnDao.chkCommCd(codeD.getComm_cd())){
            return false;
        }
        codeMnDao.insertCommCd(codeD);
        return true;
    }


    // 상세코드 수정
    public void updateCommCd(CodeD codeD){
        codeMnDao.updateCommCd(codeD);
    }

    // 상세코드 삭제
    public int deleteOneCommCd(String comm_cd, String cd_type){
        return codeMnDao.deleteOneCommCd(comm_cd, cd_type);
    }
}
