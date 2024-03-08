package com.jun.newacademy.auth.security.jwt;

import com.jun.newacademy.auth.security.usersecurity.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    // OncePerRequestFilter -> HttpServletRequest / HttpServletResponse 받아 올 수 있음
    // 강의 다시 한번 더 확인

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService; // 사용자가 있는지 확인

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // HttpServletRequest, HttpServletResponse를 받을 수 있는 이유는 클래스가 OncePerRequestFilter 상속받아서

        String token = jwtUtil.getTokenFromRequest(request); // 쿠키에서 존맛탱 가지고 오기(없으면 뭐.. null일 테니)

        // 토큰을 갖고 있니?
        if (StringUtils.hasText(token)) {
            token = jwtUtil.substringToken(token); // 존맛탱 앞글자 싹둑
            log.info(token);

            // 앞의 "Bearer " 떼어낸 토큰의 유효성 검증(안 유효한 거니?)
            if (!jwtUtil.validateToken(token)) {
                log.error("Token Error");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(token); // 토큰에서 사용자 정보 뽑아오기

            try {
                setAuthentication(info.getSubject()); // 토큰에서 뽑은 사용자 정보로 인증 시도
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        // 최종적으로 SecurityContextHolder -> SecurityContext -> Authentication 인증 객체 -> Principal, Credentials, Authorities 담겨진 내용
        filterChain.doFilter(request, response); // 그 내용 들고 다음 필터로 넘어가세용
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext(); // 얘가 SecurityContextHolde를 통해 빈 SecurityContext 생성
        Authentication authentication = createAuthentication(username); // 그리고 Authentication 인증 객체 만듦
        context.setAuthentication(authentication); // 그 인증 객체를 SecurityContext에 담는 거임

        SecurityContextHolder.setContext(context); // 최종 세팅
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) { // 아까 그 Authentication 인증 객체 만듦
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // UPAT 생성
        // Authentication 인증 객체에 넣는 Principal, Credentials, Authorities
        // Principal : 보통 사용자 식별값(UserDetails의 인스턴스를 집어넣음)
        // Credentials : 주로 비밀번호, 대부분 사용자 인증 후에 비움
        // Authorities : 사용자 부여 권한을 GrantedAuthority로 추상화해서 넣음(권한에 따른 요청 허가 처리 용이를 위해서)
    }
}
