package com.sparta.mat_dil.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mat_dil.dto.KakaoUserInfoDto;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.entity.UserStatus;
import com.sparta.mat_dil.entity.UserType;
import com.sparta.mat_dil.jwt.JwtUtil;
import com.sparta.mat_dil.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j(topic = "KAKAO Login")
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;


    public List<String> kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String kakaoAccessToken = getToken(code);
        log.info("Access Token 요청 성공 : " + kakaoAccessToken);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken);
        log.info("User API 요청 성공 : " + kakaoUserInfo.getEmail());

        // 3. 필요시에 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);
        log.info("Access Token 요청 성공 : " + kakaoAccessToken);

        // 4. JWT 토큰 반환
//        String token=jwtUtil.createAccessToken(kakaoUser.getAccountId(), kakaoUser.getUserType());
        String accessToken = jwtUtil.createAccessToken(kakaoUser.getAccountId(), kakaoUser.getUserType());
        String refreshToken = jwtUtil.createRefreshToken(kakaoUser.getAccountId(), kakaoUser.getUserType());

        List<String> tokens = new ArrayList<>();

        tokens.add(URLEncoder.encode(accessToken, "utf-8").replaceAll("\\+", "%20"));
        tokens.add(URLEncoder.encode(refreshToken, "utf-8").replaceAll("\\+", "%20"));

        kakaoUser.refreshTokenReset(refreshToken);
        userRepository.save(kakaoUser);

        jwtUtil.addJwtToCookie(accessToken, refreshToken, response);
        /*
        user.refreshTokenReset(refreshToken);
        userRepository.save(user);

        jwtUtil.addJwtToCookie(accessToken, refreshToken, response);
         */

        return tokens;
    }

    private String getToken(String code) throws JsonProcessingException {
        log.info("인가 코드 : "+code);
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "b312817d461313a9f4a4538c94d1fdc8");
        body.add("redirect_uri", "http://localhost:8080/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);
        log.info("Access Token 요청_ restTemplate");
        // 카카오한테 Access Token 달라고 HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );
        log.info("Access Token 요청 성공_ restTemplate");

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        log.info("access 코드 : "+accessToken);
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoDto(id, nickname, email);
    }

    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();   // 카카오 DB 에서 가져온 User 'id'
        User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);
        // 카카오로 로그인, 우리 사이트는 처음인 상태
        log.info("kakaoUser 상태 : " + kakaoUser);
        if (kakaoUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getEmail();
            log.info("사용자 Email 확인");
            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                log.info("기존 사용자");
                // 이미 회원임, 근데 카카오 로그인만 처음
                kakaoUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                kakaoUser.kakaoIdUpdate(kakaoId);
            } else {
                log.info("신규 사용자");
                // 우리 사이트 회원가입을 하려는 상태
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                kakaoUser = new User(
                        kakaoUserInfo.getEmail(),              // 계정 아이디
                        encodedPassword,    // 계정 비밀번호
                        kakaoUserInfo.getNickname(),    // 계정 성명
                        kakaoUserInfo.getEmail(),              // 계정 이메일
                        UserType.CONSUMER,
                        UserStatus.ACTIVE,
                        kakaoUserInfo.getId()
                );
            }
            userRepository.save(kakaoUser);
        }
        log.info("완료");
        return kakaoUser;
    }
}
