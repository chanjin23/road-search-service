package com.didim.project1.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebViewConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/home/home.html"); //메인페이지
        registry.addViewController("/login").setViewName("forward:/login/login.html"); //로그인페이지
        registry.addViewController("/map").setViewName("forward:/map/map.html"); //지도 페이지
        registry.addViewController("/register").setViewName("forward:/register/register.html"); //지도 페이지
    }
}
