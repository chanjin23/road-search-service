package com.didim.project1.common.config;

public class SecurityPath {

    // permitAll
    public static final String[] PUBLIC_ENDPOINTS = {
            "/test/all", //테스트
            "/api/login",
            "/api/signup",
            "/api/protected",
            "/api/token/refresh",
            "/login/**",
            "/home/**",
            "/map/**",
            "/register/**",
            "/useful-function.js",
            "/api.js",
            "/","/login","/map","/register"
    };

}
