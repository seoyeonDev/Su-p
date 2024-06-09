package com.example.studyproject.studylogs;


import com.example.studyproject.studygroup.StudyGroupController;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/studylogs")
public class StudyLogsController {

    @Autowired
    private StudyLogsService studyLogsService;
	@Value("${spring.servlet.multipart.location}")
	private String path;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(StudyGroupController.class);





    
    
    
    
    
    
    
    
    
    
    
    // 스터디로그 상세보기 - 이미지
    @GetMapping("/getImage/{img_id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String img_id) {
        // 실제 이미지 파일이 저장된 경로
        String imagePath = path + "/" +img_id; // 스터디로그 등록하는 메서드가 아직 없어서 어떤 식으로 저장되는지 모르므로 임시로 경로 지정

        // 파일이 존재하는지 확인
        Path imageFilePath = Paths.get(imagePath);
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

        // 응답에 이미지 데이터와 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        
        String[] parts = img_id.split("\\.");
        String fileExtension = parts[parts.length - 1].toLowerCase(); // 확장자 추출 및 소문자로 변환

        MediaType mediaType;
        if ("jpg".equalsIgnoreCase(fileExtension) || "jpeg".equalsIgnoreCase(fileExtension)) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if ("png".equalsIgnoreCase(fileExtension)) {
            mediaType = MediaType.IMAGE_PNG;
        } else {
            // 기본적으로 JPEG로 설정
            mediaType = MediaType.IMAGE_JPEG;
        }

        headers.setContentType(mediaType);
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
    
    // 스터디로그 상세보기 - 첨부파일
    @GetMapping("/downloadFile/{file_id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String file_id) {
        // 파일의 절대 경로 생성.. 이미지와 마찬가지로 스터디로그 생성 메서드가 없어서 이미지, 파일 어떤 식으로 저장하는지 모르므로 임의로 설정
        String realFilePath = path + "/" + file_id;

        // 파일의 경로를 이용하여 FileSystemResource 객체 생성
        Path filePath = Paths.get(realFilePath);
        FileSystemResource fileResource = new FileSystemResource(filePath);

        // 파일이 존재하지 않는 경우 404 에러 반환
        if (!fileResource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 파일 다운로드를 위한 응답 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileResource.getFilename());

        // 파일을 응답 본문에 담아서 클라이언트에게 반환
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResource);
    }
}
