package com.didim.project1.user.controller;

import com.didim.project1.common.jwt.JwtToken;
import com.didim.project1.common.jwt.JwtTokenProvider;
import com.didim.project1.common.util.CookieUtil;
import com.didim.project1.user.dto.UserSignInRequestDto;
import com.didim.project1.user.dto.UserTokenResponseDto;
import com.didim.project1.user.service.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserAuthController {
    private final UserAuthService userAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    // 로그인 처리
    @PostMapping("/login")
    public ResponseEntity<UserTokenResponseDto> login(@RequestBody UserSignInRequestDto userSignInRequestDto,
                                                      HttpServletResponse response) {

        JwtToken jwtToken = userAuthService.login(userSignInRequestDto, response);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserTokenResponseDto(jwtToken.getAccessToken()));
    }

    @GetMapping("/protected")
    public ResponseEntity<Void> getProtectedResource(@CookieValue(
            value = "accessToken", required = false, defaultValue = "") String accessToken) {
        log.info("토큰검증");
        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (jwtTokenProvider.validateToken(accessToken)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // 토큰 재발급
    @GetMapping("/token/refresh")
    public ResponseEntity<UserTokenResponseDto> refreshAccessToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {
        log.info("토큰재발급 시작!");
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("리프레시토큰 없음");
        }

        if (jwtTokenProvider.validateToken(refreshToken)) {
            JwtToken jwtToken = userAuthService.refresh(refreshToken, response);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new UserTokenResponseDto(jwtToken.getAccessToken()));
        } else {
            throw new IllegalArgumentException("리프레시토큰 유효성검증 실패");
        }
    }

    // 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        userAuthService.logout(response);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
