package com.example.studyproject.group;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class GroupService {
	
	// log4j2 로그 찍기
	public static final Logger LOGGER = LogManager.getLogger(GroupService.class);

	private final GroupDao groupDao;

	public GroupService(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	// 그룹 추가
	public void createGroup(Group vo) throws NoSuchAlgorithmException {
		
		groupDao.createGroup(vo);
	}
	
	
	// 그룹 삭제
	public void deleteGroup(Group vo) {
		
		groupDao.deleteGroup(vo);
	}
}
