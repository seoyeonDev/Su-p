package com.example.studyproject.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/mail")
public class EmailVerificationController {

    @Autowired
    private EmailService emailService;
    private int number;

    RedisConfig rc = new RedisConfig();


    /**
     * 인증번호 이메일 전송
     * @param mail
     * @param request
     * @return
     */
    @PostMapping("/mailSend")
    public HashMap<String, Object> mailSend(String mail, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        try {
            number = emailService.sendMail(mail);
            String num = String.valueOf(number);

            // redis 저장
            rc.redisTemplate(rc.redisConnectionFactory()).opsForValue().set("emailNumChk",num);
//            HttpSession session = null;
//            session = request.getSession();
//            session.setAttribute("emailNum", num);

            map.put("success", Boolean.TRUE);
            map.put("number", num);
        } catch (Exception e){
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }
        return map;
    }

    /**
     * 인증번호 검사
     * @param userNumber
     * @return
     */
    @GetMapping("/mailCheck")
    public ResponseEntity<?> mailCheck(@RequestParam String userNumber){


        String value = (String) rc.redisTemplate(rc.redisConnectionFactory()).opsForValue().get("emailNumChk");
        boolean isMatch = userNumber.equals(value);
//        boolean isMatch = userNumber.equals(session.getAttribute("emailNum"));

        return ResponseEntity.ok(isMatch);
    }
}

