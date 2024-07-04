package com.sparta.mat_dil.jwt;

import com.sparta.mat_dil.entity.UserType;
import com.sparta.mat_dil.enums.ErrorType;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 리프레시 헤더 값
    public static final String REFRESH_HEADER = "RefreshToken";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer "; //관습상 "Bearer+공백"을 붙이면 JWT Token 임을 알 수 있음
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분 ms단위라서 1000을 곱해야됨
    // 리프레시 토큰 만료시간 (7일)
    private static final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L;
    //로그아웃 토큰 블랙리스트
    private final Set<String> tokenBlacklist = ConcurrentHashMap.newKeySet();
    private final UserRepository userRepository;

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그"); // 시간순으로 로그를 볼 수 있게 해줌

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct // 해당 클래스를 초기화한 후 실행 매개변수를 사용할 수 없고, 리턴값을 사용할 수 없음
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes); // SecretKey를 가져와서 Key로 변환
    }


    // 토큰 생성 공통 로직
    private String createToken(String subject, long expirationTime, UserType userType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .claim(AUTHORIZATION_KEY, userType)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, signatureAlgorithm);
        return BEARER_PREFIX + builder.compact();
    }

    // 액세스 토큰 생성
    public String createAccessToken(String accountId, UserType userType) {
        return createToken(accountId, TOKEN_TIME, userType);
    }

    // 리프레시 토큰 생성
    public String createRefreshToken(String accountId, UserType userType) {
        return createToken(accountId, REFRESH_TOKEN_TIME, userType);
    }

    // JWT Cookie 에 저장
    public void addJwtToCookie(String accessToken, String refreshToken, HttpServletResponse res) {
        try {
            accessToken = URLEncoder.encode(accessToken, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
            refreshToken = URLEncoder.encode(refreshToken, "utf-8").replaceAll("\\+", "%20");
            log.info(accessToken);
            Cookie cookieAccess = new Cookie(AUTHORIZATION_HEADER, accessToken); // Name-Value
            Cookie cookieRefresh = new Cookie(REFRESH_HEADER, refreshToken);
            cookieAccess.setPath("/");
            cookieRefresh.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookieAccess);
            res.addCookie(cookieRefresh);
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

    // 토큰 검증
    public boolean validateToken(String token) {
        return validateTokenInternal(token);
    }

    // 리프레시 토큰 검증
    public boolean validateRefreshToken(String token) {
        return validateTokenInternal(token);
    }

    // 토큰 검증 공통 로직
    private boolean validateTokenInternal(String token) {
        if (isTokenBlacklisted(token)) {
            throw new CustomException(ErrorType.LOGGED_OUT_TOKEN);
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않은 JWT 서명 입니다.", e);
            throw new CustomException(ErrorType.INVALID_JWT);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
//            throw new CustomException(ErrorType.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
            throw new CustomException(ErrorType.INVALID_JWT);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.", e);
            throw new CustomException(ErrorType.INVALID_JWT);
        } catch (Exception e){
            log.error("잘못되었습니다.", e);
            throw new CustomException(ErrorType.INVALID_JWT);
        }
        return false;
    }

    //JWT access token 에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private String getTokenFromRequest(HttpServletRequest req, String header) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(header)) {
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

    //토큰 블랙리스트 검사
    private boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }

    public void addBlackListToken(String token){
        tokenBlacklist.add(token);
    }

}
