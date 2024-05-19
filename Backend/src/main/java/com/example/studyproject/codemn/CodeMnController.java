package com.example.studyproject.codemn;

import com.example.studyproject.codemn.CodeMn;
import com.example.studyproject.member.Member;
import com.example.studyproject.member.MemberController;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/code")
public class CodeMnController {

    @Autowired
    private CodeMnService codeMnService;

    // log4j2 로그 찍기
    private static final Logger LOGGER = LogManager.getLogger(CodeMnService.class);


    // 분류 조회
    @GetMapping("/selectType")
    public Map selectCdType(String cd_type) {
        ArrayList<CodeMn> list = codeMnService.selectCdType(cd_type);
        Map map = new HashMap();
        map.put("list",list);
        return map;
    }

    // 분류 추가
    @PostMapping("/insertType")
    public void insertCdType(@RequestBody CodeMn codeMn){
        String cd_type = codeMn.getCd_type();
//        ArrayList<CodeMn> chk = codeMnService.selectCdType(cd_type);
        LOGGER.info(" ==== insert ing");
        boolean codemn = codeMnService.insertCdType(codeMn);
        if(codemn){
            LOGGER.info(codemn + " ========= INSERT SUCCESS");

        }else {
            LOGGER.debug("========== INSERT FAIL");
        }


    }

    // 분류 수정 (이름만 수정가능)
    @PostMapping("/type/update")
    public void updateCdType(CodeMn codeMn){
        // String cd_type = codeMn.getCd_type();
        codeMnService.updateCdType(codeMn);
    }


    // 분류 삭제
    @DeleteMapping("/type/{cd_type}")
    public String deleteCdType(@PathVariable String cd_type){
        boolean result = codeMnService.deleteCdType(cd_type);

        if (result){
            return "삭제 완료";
        } else {
            return "삭제 실패";
        }
    }


}
