package com.example.studyproject.group;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import org.apache.ibatis.annotations.Delete;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.studyproject.member.Member;
import com.example.studyproject.member.MemberController;
import com.example.studyproject.member.MemberService;

@RestController
@RequestMapping("/group")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	// log4j2 로그 찍기
	private static final Logger LOGGER = LogManager.getLogger(GroupController.class);
	
	
	// 그룹 추가
	@PostMapping("/createGroup")
	public void createGroup(@RequestBody Group vo) throws NoSuchAlgorithmException {
		
		// 그룹장의 id는 로그인 시 세션에 저장하고, 세션에서 불러와서 vo.set으로 넣기
		// 날짜형식 조율하기
		
		groupService.createGroup(vo);
		
	}
	
	
	// 그룹 삭제
	@Delete("/deleteGroup")
	public void deleteGroup(@RequestBody Group vo) {
		
		groupService.deleteGroup(vo);
	}
}
