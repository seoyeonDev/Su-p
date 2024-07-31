package com.example.studyproject.files;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Class Name : FilesService.java
 * @Description : 파일 저장 Service
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.07.13     봉선호        최초 생성
 */

@Service
public class SupFilesService {

    public static final Logger LOGGER = LogManager.getLogger(SupFilesService.class);

    private final SupFilesDao filesDao;

    public SupFilesService(SupFilesDao filesDao){
        this.filesDao = filesDao;
    }
    
    // 파일 테이블 추가
    public void insertFiles(SupFiles vo) {
    	filesDao.insertFiles(vo);
    }


    public int insertFileList(List<SupFiles> supFilesList){
        return filesDao.insertFileList(supFilesList);
    }

    
    // 파일 정보 불러오기
    public List<HashMap<String, Object>> getFile(HashMap<String, Object> map) {
    	return filesDao.getFile(map);
    }
    
    // 파일 삭제
    public void delFile(HashMap<String, Object> map) {
    	filesDao.delFile(map);
    }

}
