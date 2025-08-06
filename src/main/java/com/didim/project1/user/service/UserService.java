package com.didim.project1.user.service;

import com.didim.project1.user.dto.UserSignUpRequestDto;
import com.didim.project1.user.dto.UserSignUpResponseDto;
import com.didim.project1.user.dto.UserUpdateRequestDto;
import com.didim.project1.user.entity.User;
import com.didim.project1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 이메일로 회원 검색
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일을 찾을 수 없습니다."));
    }

    // 회원 가입
    @Transactional
    public UserSignUpResponseDto registerUser(UserSignUpRequestDto userSignUpRequestDto) {

        Optional<User> existingUser = userRepository
                .findByEmail(userSignUpRequestDto.getEmail());

        if (existingUser.isPresent()) {
            User user = existingUser.get(); // NPE 방지
            if (user.getEmail().equals(userSignUpRequestDto.getEmail())) {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            }
        }

        String encodedPassword = bCryptPasswordEncoder.encode(userSignUpRequestDto.getPassword());

        User newUser = User.builder()
                .email(userSignUpRequestDto.getEmail())
                .password(encodedPassword)
                .name(userSignUpRequestDto.getName())
                .isDeleted(false)
                .build();

        userRepository.save(newUser);

        log.debug("회원 가입 - 이메일: {}", newUser.getEmail());
        return newUser.toSignUpResponseDto();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void updateUser(UserUpdateRequestDto userUpdateRequestDto,long userId) {
        User user = findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 존재하지않습니다."));

        String currentPassword = userUpdateRequestDto.getCurrentPassword();
        String newPassword = userUpdateRequestDto.getNewPassword();

        if (!bCryptPasswordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다.");
        }

        if (currentPassword.equals(newPassword)) {
            throw new IllegalArgumentException("현재비밀번호와 동일합니다. 다시설정해주세요");
        }

        userRepository.update(user.getId(),bCryptPasswordEncoder.encode(newPassword));
    }
}
