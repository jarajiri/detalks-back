package com.twat.detalks.config;

import com.twat.detalks.oauth2.jwt.JWTFilter;
import com.twat.detalks.oauth2.service.CustomOAuth2UserService;
import com.twat.detalks.oauth2.CustomSuccessHandler;
import com.twat.detalks.oauth2.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // cors
        http
            .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList(
                    "http://localhost:3000",
                    "http://ec2-52-78-163-112.ap-northeast-2.compute.amazonaws.com",
                    "http://13.124.92.241",
                    "http://www.detalks.store"
                    ));
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setMaxAge(3600L);
                configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));
                return configuration;
            }));
        // csrf disable
        http
            .csrf(AbstractHttpConfigurer::disable);
        // Form 로그인 방식 disable
        http
            .formLogin(AbstractHttpConfigurer::disable);
        // HTTP Basic 인증 방식 disable
        http
            .httpBasic(AbstractHttpConfigurer::disable);
        // JWTFilter 추가
        http
            .addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);
        // 세션 설정 : STATELESS
        http
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 경로별 인가 작업
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/swagger-ui/**","/v3/api-docs/**","/api-docs/**").permitAll() // 스웨거
                .requestMatchers("/api/member/signup").permitAll() // 회원 가입
                .requestMatchers("/api/member/idx/**").permitAll() // 회원 프로필 조회(비인증)
                .requestMatchers("/api/member/email/**").permitAll() // 이메일 중복체크
                .requestMatchers("/api/member/name/**").permitAll() // 이름 중복체크
                .requestMatchers("/api/member/signin").permitAll() //  일반 로그인
                .requestMatchers("/api/member/auth/header").permitAll() // 소셜 로그인
                .requestMatchers("/api/email/**").permitAll() // 이메일 인증
                // .requestMatchers("/api/v1/email/**").permitAll()
                .requestMatchers("/api/member/pwd").permitAll() // 비밀번호 재설정
                .requestMatchers(HttpMethod.GET,"/api/questions/**").permitAll() // 질문 조회 관련
                // .requestMatchers("/api/votes/**").permitAll() // 투표
                .requestMatchers(("/api/mypage/**")).permitAll() // 마이 페이지 관련
                .requestMatchers("/upload/**").permitAll()
                .anyRequest().authenticated()
            );
        // oauth2
        http
            .oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                    .userService(customOAuth2UserService))
                .successHandler(customSuccessHandler)
            );
        http
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("access denied !!!!");
                })
            );

        return http.build();
    }

}