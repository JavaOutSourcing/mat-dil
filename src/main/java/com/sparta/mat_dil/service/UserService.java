package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.ProfileRequestDto;
import com.sparta.mat_dil.dto.ProfileResponseDto;
import com.sparta.mat_dil.entity.PasswordHistory;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.enums.ErrorType;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.repository.PasswordHistoryRepository;
import com.sparta.mat_dil.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
//    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ProfileResponseDto update(Long userId, ProfileRequestDto requestDto) {
        User user = findById(userId);
//        String newEncodePassword = null;
        String newPassword = null;

        // 비밀번호 수정 시
        if (requestDto.getPassword() != null && requestDto.getNewPassword() != null) {
//            // 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우
//            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
//                throw new CustomException(ErrorType.INVALID_PASSWORD);
//            }
//            //현재 비밀번호와 동일한 비밀번호로는 변경할 수 없음
//            if (passwordEncoder.matches(requestDto.getPassword(), requestDto.getNewPassword())) {
//                throw new CustomException(ErrorType.PASSWORD_SAME);
//            }
//            // 최근 3번 안에 사용한 비밀번호는 사용할 수 없도록 제한
//            List<PasswordHistory> recentPasswords = passwordHistoryRepository.findTop3ByUserOrderByChangeDateDesc(user);
//            boolean isInPreviousPasswords = recentPasswords.stream()
//                    .anyMatch(pw -> passwordEncoder.matches(requestDto.getNewPassword(), pw));
//            if (isInPreviousPasswords) {
//                throw new CustomException(ErrorType.PASSWORD_RECENTLY_USED);
//            }
//
//            newEncodePassword = passwordEncoder.encode(requestDto.getNewPassword());
//
//            PasswordHistory passwordHistory = new PasswordHistory(user, newEncodePassword);
//            passwordHistoryRepository.save(passwordHistory);

            // 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우
            if (!requestDto.getPassword().equals(user.getPassword())) {
                throw new CustomException(ErrorType.INVALID_PASSWORD);
            }
            // 현재 비밀번호와 동일한 비밀번호로는 변경할 수 없음
            if (requestDto.getPassword().equals(requestDto.getNewPassword())) {
                throw new CustomException(ErrorType.PASSWORD_RECENTLY_USED);
            }
            // 최근 3번안에 사용한 비밀번호는 사용할 수 없도록 제한
            List<PasswordHistory> recentPasswords = passwordHistoryRepository.findTop3ByUserOrderByChangeDateDesc(user);
            boolean isInPreviousPasswords = recentPasswords.stream()
                    .anyMatch(pw -> pw.getPassword().equals(requestDto.getNewPassword()));
            if (isInPreviousPasswords) {
                throw new CustomException(ErrorType.PASSWORD_RECENTLY_USED);
            }

            newPassword = requestDto.getNewPassword();

            PasswordHistory passwordHistory = new PasswordHistory(user, newPassword);
            passwordHistoryRepository.save(passwordHistory);
        }

        user.update(
//                Optional.ofNullable(newEncodePassword),
                Optional.ofNullable(newPassword),
                Optional.ofNullable(requestDto.getName()),
                Optional.ofNullable(requestDto.getIntro())
        );

        return new ProfileResponseDto(user);
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
