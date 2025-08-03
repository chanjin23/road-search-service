package com.didim.project1.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.didim.project1.common.jwt.UserConstants.ACCESS_TOKEN_TYPE_VALUE;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.secret-key}")
    private String secretKey;
    private final JwtTokenProvider jwtTokenProvider;
    private static final String BEARER = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //쿠키에서 JWT 추출
        String accessToken = resolveAccessToken(request);

        // access token이 있고, BEARER로 시작한다면
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            // 유효한 토큰: 유저 정보 가져옴
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            // SecurityContextHolder 에 인증정보담기
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String resolveAccessToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        // 쿠키에서 "accessToken" 추출
        Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> ACCESS_TOKEN_TYPE_VALUE.equals(cookie.getName()))
                .findFirst();

        return jwtCookie.map(Cookie::getValue).orElse(null);
    }
}
