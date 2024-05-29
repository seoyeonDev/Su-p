package com.example.studyproject.codemn;

import com.example.studyproject.codemn.CodeM;
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
        ArrayList<CodeM> list = codeMnService.selectCdType(cd_type);
        Map map = new HashMap();
        map.put("list",list);
        return map;
    }

    // 분류 추가
    @PostMapping("/insertType")
    public void insertCdType(@RequestBody CodeM codeM){
        String cd_type = codeM.getCd_type();
//        ArrayList<CodeMn> chk = codeMnService.selectCdType(cd_type);
        LOGGER.info(" ==== insert ing");
        boolean codem = codeMnService.insertCdType(codeM);
        if(codem){
            LOGGER.info(codem + " ========= INSERT SUCCESS");

        }else {
            LOGGER.debug("========== INSERT FAIL");
        }


    }

    // 분류 수정 (이름만 수정가능)
    @PostMapping("/type/update")
    public void updateCdType(CodeM codeM){
        // String cd_type = codeMn.getCd_type();
        codeMnService.updateCdType(codeM);
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

    /**
     * 코드 소분류
     * @table code_d
     * @return
     */

    // 상세코드 조회
    @GetMapping("/detail/{comm_cd}")
    public Map selectCommCd(String comm_cd) {
        ArrayList<CodeD> list = codeMnService.selectCommCd(comm_cd);
        Map map = new HashMap();
        map.put("list", list);
        return map;
    }

    // 상세코드 추가
    @PostMapping("/detail/insert")
    public void insertComCd(@RequestBody CodeD codeD){
        String comm_cd = codeD.getComm_cd();
        boolean coded = codeMnService.insertCommCd(codeD);
        if(coded){
            LOGGER.info(coded + " ===== INSERT SUCCESS");
        } else {
            LOGGER.debug("===== INSERT FAIL");
        }
    }

    // 상세코드 수정
    @PostMapping("/detail/update")
    public void updateCommCd(CodeD codeD){
        codeMnService.updateCommCd(codeD);
    }

    // 상세코드 하나삭제
    @DeleteMapping("/detail/{comm_cd}")
    public String deleteCommCd(@PathVariable String comm_cd, String cd_type) {
        int result = codeMnService.deleteOneCommCd(comm_cd, cd_type);
        if (result > 0){
            return "삭제 완료";
        }else {
            return "삭제 실패";
        }
    }
}
