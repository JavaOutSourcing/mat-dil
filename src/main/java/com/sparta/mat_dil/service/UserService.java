package com.sparta.mat_dil.service;

import com.sparta.mat_dil.dto.PasswordRequestDto;
import com.sparta.mat_dil.dto.UserRequestDto;
import com.sparta.mat_dil.entity.User;
import com.sparta.mat_dil.entity.UserStatus;
import com.sparta.mat_dil.entity.UserType;
import com.sparta.mat_dil.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;


    //회원가입
    @Transactional
    public void createUser(UserRequestDto requestDto) {
        //동일 아이디 검증
        validateUserId(requestDto.getAccountId());

        //동일 이메일 검증
        validateUserEmail(requestDto.getEmail());

        //비밀번호 암호화
//        String password = passwordEncoder.encode(requestDto.getPassword());

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

    private void validateUserEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);

        if(findUser.isPresent()){
            throw new IllegalArgumentException("중복된 유저 email 입니다.");
        }
    }

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
}
