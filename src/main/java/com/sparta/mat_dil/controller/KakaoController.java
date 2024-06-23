package com.sparta.mat_dil.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.mat_dil.enums.ResponseStatus;
import com.sparta.mat_dil.jwt.JwtUtil;
import com.sparta.mat_dil.service.KakaoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {
    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        List<String> tokens = kakaoService.kakaoLogin(code, response);

        log.info("로그인 성공");

        ResponseStatus status = ResponseStatus.LOGIN_SUCCESS;
        // HTML 포맷으로 응답 생성
        String htmlResponse = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; margin: 40px; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; }" +
                ".header { text-align: center; margin-bottom: 20px; }" +
                ".header h1 { margin: 0; font-size: 24px; color: #333; }" +
                ".content { margin-bottom: 20px; }" +
                ".content p { margin: 10px 0; font-size: 18px; }" +
                ".token { word-wrap: break-word; background-color: #f9f9f9; padding: 10px; border: 1px solid #ddd; border-radius: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>카카오 로그인 결과</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p><strong>Status:</strong> " + status.getHttpStatus().value() + "</p>" +
                "<p><strong>Message:</strong> " + status.getMessage() + "</p>" +
                "<p><strong>" + JwtUtil.AUTHORIZATION_HEADER + ":</strong></p>" +
                "<p class='token'>" + tokens.get(0) + "</p>" +
                "<p class='token'>" + tokens.get(1) + "</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        return htmlResponse;
    }

    @GetMapping("/login")
    public String kakaoLoginPage(){
        return "https://kauth.kakao.com/oauth/authorize?client_id=b312817d461313a9f4a4538c94d1fdc8&redirect_uri=http://localhost:8080/kakao/callback&response_type=code";
    }
}
