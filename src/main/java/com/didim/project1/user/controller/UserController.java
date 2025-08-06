package com.didim.project1.user.controller;

import com.didim.project1.common.jwt.CustomUserDetails;
import com.didim.project1.user.dto.UserResponseDto;
import com.didim.project1.user.dto.UserSignUpRequestDto;
import com.didim.project1.user.dto.UserSignUpResponseDto;
import com.didim.project1.user.dto.UserUpdateRequestDto;
import com.didim.project1.user.entity.User;
import com.didim.project1.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> registerUser(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto) {
        UserSignUpResponseDto userSignUpResponseDto = userService.registerUser(userSignUpRequestDto);
        log.info("회원가입 성공: email={}", userSignUpRequestDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userSignUpResponseDto);
    }

    //회원정보 가져오기
    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = userService.findById(customUserDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponseDto(user.getName(), user.getEmail()));
    }

    //비밀번호 변경
    @PutMapping("/user")
    public ResponseEntity<UserResponseDto> updateUserInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
        log.info("비밀번호변경!!");
        userService.updateUser(userUpdateRequestDto,customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
