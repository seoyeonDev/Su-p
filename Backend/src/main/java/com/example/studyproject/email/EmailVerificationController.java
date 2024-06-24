package com.example.studyproject.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class EmailVerificationController {

    @Autowired
    private EmailService emailService;
    private int number;
//
//    @PostMapping("/{email}")
//    public String verifyEmail(@PathVariable String email){
//        // 인증 코드 생성 로직 (예시로 간단히 UUID를 사용함)
//        String verificationCode = UUID.randomUUID().toString();
//        // 인증 코드를 이메일로 전송
//        emailService.sendSimpleMessage(email, "이메일 인증", "인증 코드: " + verificationCode);
//        return "인증 코드가 이메일로 전송되었습니다.";
//    }

    @PostMapping("/mailSend")
    public HashMap<String, Object> mailSend(String mail, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        try {
            number = emailService.sendMail(mail);
            String num = String.valueOf(number);

            HttpSession session = null;
            session = request.getSession();
            session.setAttribute("emailNum", num);

            map.put("success", Boolean.TRUE);
            map.put("number", num);
        } catch (Exception e){
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }
        return map;
    }

    @GetMapping("/mailCheck")
    public ResponseEntity<?> mailCheck(@RequestParam String userNumber, HttpServletRequest request){
        HttpSession session = request.getSession();
        boolean isMatch = userNumber.equals(session.getAttribute("emailNum"));
        //        boolean isMatch = userNumber.equals(String.valueOf(number));
        return ResponseEntity.ok(isMatch);
    }
}

