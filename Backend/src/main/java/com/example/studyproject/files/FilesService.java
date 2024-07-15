package com.example.studyproject.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


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
public class FilesService {

    public static final Logger LOGGER = LogManager.getLogger(FilesService.class);

    private final FilesDao filesDao;

    public FilesService(FilesDao filesDao){
        this.filesDao = filesDao;
    }
    
    // 파일 테이블 추가
    public void insertFiles(Files vo) {
    	
    	filesDao.insertFiles(vo);
    }
}
