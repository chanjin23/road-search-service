package com.didim.project1.common.jwt;

import com.didim.project1.common.util.CookieUtil;
import com.didim.project1.user.entity.User;
import com.didim.project1.user.repository.UserRepository;
import com.didim.project1.user.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final Key secretKey;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.access-expired}")
    private Long accessTokenExpired;
    @Value("${jwt.refresh-expired}")
    private Long refreshTokenExpired;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = secretKey.getBytes(); // 키 바이트변환
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(Authentication authentication,
                                  HttpServletResponse response) {

        long now = (new Date()).getTime();
        Date accessTokenExpiration = new Date(now + accessTokenExpired * 1000);
        Date refreshTokenExpiration = new Date(now + refreshTokenExpired * 1000);

        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(userPrincipal.getEmail()) // 이메일을 Subject로 설정
                .setIssuedAt(new Date()) // 발행 시간
                .claim("email", userPrincipal.getEmail())
                .claim("name", userPrincipal.getName())
                .setExpiration(accessTokenExpiration) // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setSubject(userPrincipal.getEmail()) // 이메일을 Subject로 설정
                .setIssuedAt(new Date()) // 발행 시간
                .claim("email", userPrincipal.getEmail())
                .claim("name", userPrincipal.getName())
                .setExpiration(refreshTokenExpiration) // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명
                .compact();

        CookieUtil.setCookie(response, "accessToken", accessToken, refreshTokenExpired);
        CookieUtil.setCookie(response, "refreshToken", refreshToken, refreshTokenExpired);


        // JWT Token 객체 반환
        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 토큰 정보 검증
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    //토큰 만료여부
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    //날짜 추출
    private Date extractExpiration(String jwtToken) {
        return extractAllClaims(jwtToken).getExpiration();
    }

    //토큰 정보를 비밀키를 이용해 해석
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // JWT가 만료된 경우에도 Claims를 반환할 수 있도록 예외에서 Claims를 가져옴
            return e.getClaims();  // 만료된 토큰에서 Claims 정보를 가져옴
        }
    }

    public Authentication getAuthentication(String authToken) {
        // 토큰에서 Claims 추출
        Claims claims = extractAllClaims(authToken);

        // 사용자 정보 추출
        String email = claims.getSubject(); // 토큰 subject에서 email 추출

        // email로 User 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // CustomUserDetails 생성
        CustomUserDetails userDetails = new CustomUserDetails(
                user.getId(), email, user.getPassword(), user.getName());

        // Authentication 객체 반환
        return new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());
    }

}
