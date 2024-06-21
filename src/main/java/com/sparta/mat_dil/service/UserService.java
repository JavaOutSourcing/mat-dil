package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.PasswordRequestDto;
import com.sparta.mat_dil.dto.ProfileRequestDto;
import com.sparta.mat_dil.dto.ProfileResponseDto;
import com.sparta.mat_dil.dto.UserRequestDto;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.entity.UserStatus;
import com.sparta.mat_dil.enums.ErrorType;
import com.sparta.mat_dil.exception.CustomException;
import com.sparta.mat_dil.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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

    @Transactional
    public void withdrawUser(PasswordRequestDto requestDTO, User user) {

        //회원 상태 확인
        checkUserType(user.getUserStatus());

//        //비밀번호 일치 확인
//        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
//            throw new UserException("비밀번호가 일치하지 않습니다.");
//        }

        //비밀번호 확인
        if(!user.getPassword().equals(requestDTO.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //회원 상태 변경
        user.withdrawUser();


    }

    //동일 이메일 검증
    private void validateUserEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);

        if(findUser.isPresent()){
            throw new IllegalArgumentException("중복된 유저 email 입니다.");
        }
    }

    //동일 아이디 검증
    private void validateUserId(String id) {
        Optional<User> findUser = userRepository.findByAccountId(id);

        if(findUser.isPresent()){
            throw new IllegalArgumentException("중복된 유저 id 입니다.");
        }
    }

    private void checkUserType(UserStatus userStatus){
        if(userStatus.equals(UserStatus.DEACTIVATE)){
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다.");
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

    @Transactional
    public ProfileResponseDto update(Long userId, ProfileRequestDto requestDto) {
        User user = findById(userId);
//        String newEncodePassword = null;
        String newPassword = null;

        // 비밀번호 수정 시
        if (requestDto.getPassword() != null) {
//            // 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우
//            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
//                throw new CustomException(ErrorType.PASSWORD_RECENTLY_USED);
//            }
//            //현재 비밀번호와 동일한 비밀번호로는 변경할 수 없음
//            if (passwordEncoder.matches(requestDto.getPassword(), requestDto.getNewPassword())) {
//                throw new CustomException(ErrorType.PASSWORD_RECENTLY_USED);
//            }
//            // 최근 3번안에 사용한 비밀번호는 사용할 수 없도록 제한
//            boolean isInPreviousPasswords = user.getPwUsdLst3Tms().stream()
//                    .anyMatch(pw -> passwordEncoder.match(requestDto.getNewPassword(), pw));
//            if (isInPreviousPasswords) {
//                throw new CustomException(ErrorType.PASSWORD_RECENTLY_USED);
//            }
//            newEncodePassword = passwordEncoder.encode(requestDto.getNewPassword());

            // 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우
            if (!requestDto.getPassword().equals(user.getPassword())) {
                throw new CustomException(ErrorType.INVALID_PASSWORD);
            }
            // 현재 비밀번호와 동일한 비밀번호로는 변경할 수 없음
            if (requestDto.getPassword().equals(requestDto.getNewPassword())) {
                throw new CustomException(ErrorType.INVALID_PASSWORD);
            }
            // 최근 3번안에 사용한 비밀번호는 사용할 수 없도록 제한
            boolean isInPreviousPasswords = user.getPasswordHistory().stream()
                    .anyMatch(pw -> pw.equals(requestDto.getNewPassword()));
            if (isInPreviousPasswords) {
                throw new CustomException(ErrorType.INVALID_PASSWORD);
            }

            newPassword = requestDto.getNewPassword();
        }

        user.update(
//                Optional.ofNullable(newEncodePassword),
                Optional.ofNullable(newPassword),
                Optional.ofNullable(requestDto.getName()),
                Optional.ofNullable(requestDto.getIntro())
        );

        return new ProfileResponseDto(user);
    }

    public void logout(User user) {
        user.logout();
    }
}
