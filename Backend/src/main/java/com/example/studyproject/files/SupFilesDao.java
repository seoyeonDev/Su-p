package com.example.studyproject.files;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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


	// 파일 테이블 리스트 추가
	int insertFileList(List<SupFiles> supFilesList);

	
	List<HashMap<String, Object>> getFile(HashMap<String, Object> map);
	
	void delFile(HashMap<String, Object> map);

}
