package com.jun.newacademy.auth.config;

import com.jun.newacademy.auth.security.usersecurity.UserDetailsServiceImpl;
import com.jun.newacademy.auth.security.jwt.JwtAuthenticationFilter;
import com.jun.newacademy.auth.security.jwt.JwtAuthorizationFilter;
import com.jun.newacademy.auth.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
    private final UserDetailsServiceImpl userDetailsService;

    // Authentication 매니저 만들거임 이거로
    // 왜냐하면 곧바로 Authentication Manager를 바로 가져올 수 없어서
    private final AuthenticationConfiguration authenticationConfiguration;

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

        // 존맛탱 채택을 위한 세션 무상태성화(시큐리티는 세션이 디폴트 세팅)
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

        // 시큐리티는 디폴트 로그인과 관련된 세팅이 제공된다.
        // 대표적인 것이 form 로그인, 그리고 로그인 url 자동 세팅(/login)
        // 대신 form 로그인 역시 비밀번호 암호화 커스텀 설정과 충돌할 소지가 다분함
        // 여담으로 서버 부팅할 때, form 로그인 id : user + pw : 랜덤 일련번호 를 제공한다.
//        http.formLogin(Customizer.withDefaults());
        // 로그인 처리가 되지 않은 상태(시큐리티의 세션id가 저장되지 않은 상태로)로 요청을 하면, 로그인 페이지를 반환하는 게 디폴트 세팅

        // 필터를 만들기만 하면 안 되고, 이제 시큐리티 필터에 끼워넣어야 함
        // 로그인 통한 토큰 발급 전에 우선 회원이 토큰을 담아 요청 보냈는지 확인이 우선이어서
        // 인가 필터를 인증 필터 앞에 넣어줌
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class); // 인가 처리 필터

        // UPAF 전에 입력받은 사용자 아이디랑 비번 바탕으로 인증하고 토큰 발급하고 담아주는 처리해야 함
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 인증(+ 로그인) 처리 필터

        // 시큐리티 필터체인 중에 UsernamePasswordAuthenticationFilter가 존재함(스프링의 뭐.. 엄청 이름 긴 필터 상속 구현)
        // 아까의 디폴트 폼 로그인에서 아이디와 비번을 넣으면 인증 처리하는 역할을 맡는 필터
        // 1. 사용자가 아이디와 비번 제공
        // 2. UPAF에서 UsernamePasswordAuthenticationToken 발급
        // 3. 그 토큰을 AuthenticationManager에게 넘기고 인증 시도
        // 4. 실패하면 실패한 거고(SCH 비움)... 성공하면 SecurityContextHolder에 인증 처리를 저장

        // * SecurityContextHolder 내부에 SecurityContext가 있음
        // * SecurityContext 내부에 Authentication이 담김
        // * Authentication 내부에는 username(Principal), password(Credentials), authority(Authorities)이 담김


        // *** 접근 불가에 대한 설정
        // secured 어노테이션으로 권한별로 접근 차등을 뒀을 때, 튕겨져 나올 경의 접근 불가 페이지
//        http.exceptionHandling((exceptionHandling) -> {
//            exceptionHandling.accessDeniedPage("/forbidden.html"); // static 페이지(해당 URL 및 이름) 반환
//        });


        return http.build();
    }
}

// 스프링에서의 모든 호출은 DispatcherServlet을 통과하게 됨
// 이후 각 요청을 담당하는 컨트롤러로 분배됨
// 이떄 각 요청에 대해서 공통적으로 처리해야 할 필요가 있을 때(예를 들면 인증.. 인가..) DispatcherServlet 이전 단계의 작업이 필요함
// 그것을 맡을 수 있는 게 필터(최상단에서 마주하는 거 생각하기)
// 근데 시큐리티의 필터는 FilterChainProxy를 사용해서 구현하는데, 간단하게 수많은 필터 사이에 끼어있는 애들이 시큐리티 필터 체인을 생성한다고 생각하기
