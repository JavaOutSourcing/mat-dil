package com.sparta.mat_dil.jwt;

import com.sparta.mat_dil.entity.UserType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer "; //관습상 "Bearer+공백"을 붙이면 JWT Token 임을 알 수 있음
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분 ms단위라서 1000을 곱해야됨

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그"); // 시간순으로 로그를 볼 수 있게 해줌

    @PostConstruct // 해당 클래스를 초기화한 후 실행 매개변수를 사용할 수 없고, 리턴값을 사용할 수 없음
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes); // SecretKey를 가져와서 Key로 변환
    }

    // JWT 생성

    // 토큰 생성
    public String createToken(String username, UserType userType) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, userType) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

<<<<<<< HEAD
    //생성된 JWT를 쿠키에 저장
=======
    // 액세스 토큰 생성
    public String createAccessToken(String accountId, UserType userType) {
        return createToken(accountId, TOKEN_TIME, userType);
    }

    // 리프레시 토큰 생성
    public String createRefreshToken(String accountId, UserType userType) {
        return createToken(accountId, REFRESH_TOKEN_TIME, userType);
    }
>>>>>>> 88e884f (jwt/login/logout)

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

    //쿠키에 들어있던 JWT 토큰을 Substring
    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    //JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    //JWT에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // HttpServletRequest 에서 Cookie Value : JWT 가져오기
    public String getTokenFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

<<<<<<< HEAD
=======
    // AccessToken 가져오기
    public String getAccessTokenFromRequest(HttpServletRequest req) {
        return getTokenFromRequest(req, AUTHORIZATION_HEADER);
    }

    // RefreshToken 가져오기
    public String getRefreshTokenFromRequest(HttpServletRequest req) {
        return getTokenFromRequest(req, REFRESH_HEADER);
    }

    // 토큰에서 username 가져오기
    public String getUsernameFromToken(String token) {
        return getUsernameFromClaims(token);
    }

    // 리프레시 토큰에서 username 가져오기
    public String getUsernameFromRefreshToken(String token) {
        return getUsernameFromClaims(token);
    }


    // 공통 로직 분리
    private String getUsernameFromClaims(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
    public String refreshAccessToken(String refreshToken) {
        if (validateRefreshToken(refreshToken)) {
            String accountId = getUsernameFromRefreshToken(refreshToken);
            User user=userRepository.findByAccountId(accountId).get();
            // 여기에서 필요한 경우 사용자 역할 정보를 가져올 수 있다.
            return createAccessToken(accountId, user.getUserType()); // 사용자 역할이 필요하면 두 번째 인자에 역할을 전달
        }
        return null;
    }
    //토큰 블랙리스트 검사
    private boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }

>>>>>>> 88e884f (jwt/login/logout)
}
