package com.example.studyproject.codemn;


import com.example.studyproject.codemn.CodeMn;
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


    // 분류 조회
    public ArrayList<CodeMn> selectCdType(String cd_type) {

        return codeMnDao.selectCdType(cd_type);
    };

    // 분류 추가
    public boolean insertCdType(CodeMn codeMn){
        // 중복 검사
        if (!codeMnDao.chkCdType(codeMn.getCd_type())){
            return false;
        }
        codeMnDao.insertCdType(codeMn);
        return true;
    }

    // 분류 수정
    public void updateCdType(CodeMn codeMn){
        codeMnDao.updateCdType(codeMn);
    }

    // 분류 삭제
    public boolean deleteCdType(String cd_type){
        int affectedRow = codeMnDao.deleteCdType(cd_type);
        int affectedDetailRow = codeMnDao.deleteAllCommCd(cd_type);

        LOGGER.info(affectedRow + " 개의 코드분류와 " + affectedDetailRow + "개의 상세코드가 삭제되었습니다.");
        return affectedRow > 0;
    }

    // 코드 조회
    public CodeMn selectCommCd(String cd_type){
        return codeMnDao.selectCommCd(cd_type);
    }

    // 코드 추가


    // 코드 수정


    // 코드 삭제

}
