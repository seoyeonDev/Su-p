package com.example.studyproject.files;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Class Name : FilesDao.java
 * @Description : 파일 저장 Dao
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.07.13     봉선호        최초 생성
 */

@Mapper
public interface SupFilesDao {
	
	// 파일 테이블 추가
	void insertFiles(SupFiles vo);
}
