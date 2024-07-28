package com.example.studyproject.studylogs;


import com.example.studyproject.studygroup.StudyGroupController;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

import com.example.studyproject.files.SupFiles;
import com.example.studyproject.files.SupFilesService;

/**
 * @Class Name : PenaltylogController.java
 * @Description : PenaltylogController CONTROLLER
 * @Modification Information
 * @
 * @ 수정일           수정자        수정내용
 * @ -----------    --------    ---------------------------
 * @ 2024.06.12     김혜원        주석 수정
 */

@RestController
@RequestMapping("/studylogs")
public class StudyLogsController {

    @Autowired
    private StudyLogsService studyLogsService;

    @Autowired
    private SupFilesService filesSerivce;

    @Value("${spring.servlet.multipart.location}")
    private String path;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(StudyGroupController.class);


    // 스터디 로그 상세 조회
    @GetMapping("/studylogsdetail/{post_id}")
    public Map<String, Object> getMethodName(@PathVariable String post_id) {

        Map<String, Object> map = new HashMap<>();

        StudyLogs vo = studyLogsService.selectStudyLogs(post_id);
        map.put("vo", vo);

        return map;
    }


    // 결과물 신규추가
    @PostMapping("/insert")
    public void insertLogs(@RequestPart("vo") StudyLogs vo, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws NoSuchAlgorithmException {
        studyLogsService.insertLogs(vo);

        String realPath = path + "study_logs/" + vo.getPost_id();
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String[] parts = originalFilename.split("\\.");
                String fileName = parts[0];
                String fileExtension = parts[parts.length - 1].toLowerCase();
                LocalDateTime now = LocalDateTime.now();

                // 파일 저장
                String filePath = realPath + "/" + originalFilename;
                File imgSave = new File(filePath);
                try {
                    file.transferTo(imgSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                SupFiles filesEntity = new SupFiles();
                filesEntity.setFile_seq(UUID.randomUUID().toString());
                filesEntity.setFile_id(vo.getPost_id());
                filesEntity.setFile_name(fileName);
                filesEntity.setFile_ext(fileExtension);
                filesEntity.setIns_id(vo.getUser_id());
                filesEntity.setIns_date(now);


                filesSerivce.insertFiles(filesEntity);
            }
        }
    }

    // 스터디로그 
    @GetMapping("/selectList")
    public ArrayList<StudyLogs> selectList() {
        return studyLogsService.selectList();
    }


    /**
     * 결과물 업데이트
     *
     * @param post_id - 결과물 id
     * @param vo      - 결과물 vo
     */
    @PutMapping("/update/{post_id}")
    public void updateLogs(@PathVariable String post_id, @RequestBody StudyLogs vo) {

        StudyLogs studyLogs = studyLogsService.selectStudyLogs(post_id);
        LOGGER.info("StudyLogs updated: " + studyLogs);

        if (studyLogs == null) {
            LOGGER.error("StudyLogs not found with post_id: " + post_id);
            throw new RuntimeException("StudyLogs not found with post_id: " + post_id);
        }

        // 기존의 게시물을 업데이트
        // 제목, 내용, 수정일은 db에서 now로 처리
        studyLogs.setTitle(vo.getTitle());
        studyLogs.setContent(vo.getContent());

        studyLogsService.updateStudyLogs(vo);
        LOGGER.info("StudyLogs updated: " + studyLogs);

    }

    // 스터디로그 상세보기 - 이미지
    @GetMapping("/getImage/{img_id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String img_id) {
        // 실제 이미지 파일이 저장된 경로
        LOGGER.info("이미지파일명: " + img_id);
        String imagePath = path + "sp-studylogs_img/" + img_id; // 스터디로그 등록하는 메서드가 아직 없어서 어떤 식으로 저장되는지 모르므로 임시로 경로 지정
        LOGGER.info("이미지파일 경로: " + imagePath);

        // 파일이 존재하는지 확인
        Path imageFilePath = Paths.get(imagePath);
        LOGGER.info("이미지파일 경로: " + imagePath);
        if (!Files.exists(imageFilePath)) {
            // 파일이 존재하지 않을 경우 404 에러를 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


        // 이미지 파일을 읽어와 byte 배열로 변환
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(imageFilePath);
        } catch (IOException e) {
            // 이미지 파일을 읽어오는 중에 에러가 발생한 경우 500 에러를 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        LOGGER.info("이미지파일 바이트배열: " + imageBytes);

        // 응답에 이미지 데이터와 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        String[] parts = img_id.split("\\.");
        String fileExtension = parts[parts.length - 1].toLowerCase(); // 확장자 추출 및 소문자로 변환
        LOGGER.info("이미지파일 확장자: " + parts);
        LOGGER.info("이미지파일 확장자 소문자: " + fileExtension);


        MediaType mediaType;
        if ("jpg".equalsIgnoreCase(fileExtension) || "jpeg".equalsIgnoreCase(fileExtension)) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if ("png".equalsIgnoreCase(fileExtension)) {
            mediaType = MediaType.IMAGE_PNG;
        } else {
            // 기본적으로 JPEG로 설정
            mediaType = MediaType.IMAGE_JPEG;
        }
        LOGGER.info("이미지파일 미디어타입: " + mediaType);


        headers.setContentType(mediaType);
        headers.setContentLength(imageBytes.length);
        LOGGER.info("이미지파일 헤더: " + headers);
        LOGGER.info("이미지 리턴: " + new ResponseEntity<>(imageBytes, headers, HttpStatus.OK));

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    // 스터디로그 상세보기 - 첨부파일
    @GetMapping("/downloadFile/{file_id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String file_id) {
        // 파일의 절대 경로 생성.. 이미지와 마찬가지로 스터디로그 생성 메서드가 없어서 이미지, 파일 어떤 식으로 저장하는지 모르므로 임의로 설정
        LOGGER.info("파일명: " + file_id);
        String realFilePath = path + "sp-studylogs_file/" + file_id;
        LOGGER.info("파일경로: " + realFilePath);

        // 파일의 경로를 이용하여 FileSystemResource 객체 생성
        Path filePath = Paths.get(realFilePath);
        LOGGER.info("파일경로 객체: " + filePath);

        FileSystemResource fileResource = new FileSystemResource(filePath);

        // 파일이 존재하지 않는 경우 404 에러 반환
        if (!fileResource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 파일 다운로드를 위한 응답 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileResource.getFilename());
        LOGGER.info("헤더: " + headers);
        LOGGER.info("헤더 리턴: " + ResponseEntity.ok().headers(headers).body(fileResource));

        // 파일을 응답 본문에 담아서 클라이언트에게 반환
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResource);
    }
}
