package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.PasswordRequestDto;
import com.sparta.mat_dil.dto.ProfileRequestDto;
import com.sparta.mat_dil.dto.ProfileResponseDto;
import com.sparta.mat_dil.dto.UserRequestDto;
import com.sparta.mat_dil.entity.PasswordHistory;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.entity.UserStatus;
import com.sparta.mat_dil.enums.ErrorType;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.jwt.JwtUtil;
import com.sparta.mat_dil.repository.PasswordHistoryRepository;
import com.sparta.mat_dil.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    //회원가입
    @Transactional
    public void createUser(UserRequestDto requestDto) {
        //동일 아이디 검증
        validateUserId(requestDto.getAccountId());

        //동일 이메일 검증
        validateUserEmail(requestDto.getEmail());

        //비밀번호 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(password);
        userRepository.save(new User(requestDto));

    }

    //회원 탈퇴
    @Transactional
    public void withdrawUser(PasswordRequestDto requestDTO, User curruntUser) {

        User user = userRepository.findByAccountId(curruntUser.getAccountId()).orElse(null);
        if (user == null) {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
        //회원 상태 확인
        checkUserType(curruntUser.getUserStatus());

//        //비밀번호 일치 확인
        if (!passwordEncoder.matches(requestDTO.getPassword(), curruntUser.getPassword())) {
            throw new CustomException(ErrorType.INVALID_PASSWORD);
        }

        //회원 상태 변경
        user.withdrawUser();
    }

    //동일 이메일 검증
    private void validateUserEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);

        if (findUser.isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_EMAIL);
        }
    }

    //동일 아이디 검증
    private void validateUserId(String id) {
        Optional<User> findUser = userRepository.findByAccountId(id);

        if (findUser.isPresent()) {
            throw new CustomException(ErrorType.DUPLICATE_ACCOUNT_ID);
        }
    }

    private void checkUserType(UserStatus userStatus) {
        if (userStatus.equals(UserStatus.DEACTIVATE)) {
            throw new CustomException(ErrorType.DEACTIVATE_USER);
        }
    }

    //회원 정보 수정
    @Transactional
    public ProfileResponseDto update(Long userId, ProfileRequestDto requestDto) {
        User user = findById(userId);
        String newEncodePassword = null;

        // 비밀번호 수정 시
        if (requestDto.getPassword() != null) {
            // 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우
            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                throw new CustomException(ErrorType.INVALID_PASSWORD);
            }
            //현재 비밀번호와 동일한 비밀번호로는 변경할 수 없음
            if (requestDto.getPassword().equals(requestDto.getNewPassword())) {
                throw new CustomException(ErrorType.PASSWORD_RECENTLY_USED);
            }
            System.out.println("adasdadasda\n\n\n\n");
            // 최근 3번 안에 사용한 비밀번호는 사용할 수 없도록 제한
            List<PasswordHistory> recentPasswords = passwordHistoryRepository.findTop3ByUserOrderByChangeDateDesc(user);
            boolean isInPreviousPasswords = recentPasswords.stream()
                    .anyMatch(pw -> passwordEncoder.matches(requestDto.getNewPassword(), pw.getPassword()));
            if (isInPreviousPasswords) {
                throw new CustomException(ErrorType.PASSWORD_RECENTLY_USED);
            }

            newEncodePassword = passwordEncoder.encode(requestDto.getNewPassword());

            PasswordHistory passwordHistory = new PasswordHistory(user, newEncodePassword);
            passwordHistoryRepository.save(passwordHistory);
        }

        user.update(
                Optional.ofNullable(newEncodePassword),
                Optional.ofNullable(requestDto.getName()),
                Optional.ofNullable(requestDto.getIntro())
        );

        return new ProfileResponseDto(user);
    }

    @Transactional
    public void logout(User user, HttpServletResponse res, HttpServletRequest req) {
        user.logout();
        Cookie[] cookies = req.getCookies();
        String accessToken=jwtUtil.getAccessTokenFromRequest(req);
        jwtUtil.addBlackListToken(accessToken.substring(7));

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setPath("/");
                cookie.setMaxAge(0);

                res.addCookie(cookie);
            }
        }


    }

    public ProfileResponseDto getProfile(Long userId) {
        return new ProfileResponseDto(findById(userId));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER)
        );
    }

}
