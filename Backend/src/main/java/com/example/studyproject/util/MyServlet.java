package com.example.studyproject.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 쿠키 생성
        Cookie cookie = new Cookie("loginId", "cookieValue");
        cookie.setMaxAge(3600); // 쿠키의 유효 기간 설정 (초 단위)
        cookie.setPath("/"); // 쿠키의 유효 경로 설정

        // 응답에 쿠키 추가
        response.addCookie(cookie);

        // 추가적인 응답 처리
        response.getWriter().println("쿠키가 설정되었습니다.");
    }
}
