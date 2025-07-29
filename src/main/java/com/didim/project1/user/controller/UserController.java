package com.didim.project1.user.controller;

import com.didim.project1.user.dto.UserSignUpRequestDto;
import com.didim.project1.user.dto.UserSignUpResponseDto;
import com.didim.project1.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
