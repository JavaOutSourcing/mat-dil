package com.sparta.mat_dil.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.mat_dil.dto.ExceptionDto;
import com.sparta.mat_dil.dto.LoginRequestDto;
import com.sparta.mat_dil.dto.ResponseMessageDto;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.entity.UserType;
import com.sparta.mat_dil.enums.ErrorType;
import com.sparta.mat_dil.enums.ResponseStatus;
import com.sparta.mat_dil.jwt.JwtUtil;
import com.sparta.mat_dil.repository.UserRepository;
import com.sparta.mat_dil.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/users/login"); //UsernamePasswordAuthenticationFilter을 상속 받으면 사용할수잇음
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getAccountId(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        String accountId = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserType userType = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUserType();

        String accessToken = jwtUtil.createAccessToken(accountId, userType);
        String refreshToken = jwtUtil.createRefreshToken(accountId, userType);

        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        user.refreshTokenReset(refreshToken);
        userRepository.save(user);

        jwtUtil.addJwtToCookie(accessToken, refreshToken, response);
        log.info(accessToken);

        //http reqeust test를 위해 header에 토큰값을 추가하였습니다. http 테스트를 하지 않을 경우 주석해야합니다.
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_HEADER, refreshToken);

        // 로그인 성공 메시지
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new ResponseMessageDto(ResponseStatus.LOGIN_SUCCESS)));
        response.getWriter().flush();
        }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        ErrorType errorType = ErrorType.NOT_FOUND_AUTHENTICATION_INFO;
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(new ExceptionDto(errorType)));
        response.getWriter().flush();
    }
}
