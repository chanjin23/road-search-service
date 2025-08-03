package com.didim.project1.user.controller;

import com.didim.project1.common.jwt.JwtToken;
import com.didim.project1.common.util.CookieUtil;
import com.didim.project1.user.dto.UserSignInRequestDto;
import com.didim.project1.user.dto.UserTokenResponseDto;
import com.didim.project1.user.service.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserAuthController {

    @Value("${jwt.refresh-expired}")
    private Long refreshTokenExpired;


    private final UserAuthService userAuthService;

    // 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<UserTokenResponseDto> login(@RequestBody UserSignInRequestDto userSignInRequestDto,
                                                      HttpServletResponse response) {

        JwtToken jwtToken = userAuthService.login(userSignInRequestDto, response);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserTokenResponseDto(jwtToken.getAccessToken()));
    }
}
