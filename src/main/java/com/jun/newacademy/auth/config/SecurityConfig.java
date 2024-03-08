package com.jun.newacademy.auth.config;

import com.jun.newacademy.auth.security.usersecurity.UserDetailsServiceImpl;
import com.jun.newacademy.auth.security.jwt.JwtAuthenticationFilter;
import com.jun.newacademy.auth.security.jwt.JwtAuthorizationFilter;
import com.jun.newacademy.auth.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 커스텀 필터와 시큐리티는 기왕이면 공존시키지 말 것(충돌 우려 열심히 겪어봤으니...)
@Configuration // 빈 등록 수동 등록
@EnableWebSecurity // 시큐리티 사용하겠다는 의미 꼭 잊지 말 것...
@EnableMethodSecurity(securedEnabled = true) // securedEnabled는 권한 당 접근 여부 부(secured 어노테이션을 위해서)
@RequiredArgsConstructor
public class SecurityConfig { // 인증 인가를 위한 기본 세팅

    private final JwtUtil jwtUtil;
    private final
    UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration; // Authentication 매니저 만들거임 이거로

    // 인증매니저 생성 메서드
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception { // 인증필터 생성
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration)); // 인증매니저 설정
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() { // 인가필터 생성
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean // 시큐리티에 있어 가장 기본적인 설정 담당(특히 URL 경로별 접근 여부)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable()); // 크로스 사이트 요청 위조

        // 존맛탱 채택을 위한 세션 무상태성화
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        // 풀스택일 때 의미있을...
//                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/api/users/signup").permitAll() // 회원가입만 바로 통과
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리 요구
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class); // 인가 처리 필터
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 인증(+ 로그인) 처리 필터

        return http.build();
    }
}
