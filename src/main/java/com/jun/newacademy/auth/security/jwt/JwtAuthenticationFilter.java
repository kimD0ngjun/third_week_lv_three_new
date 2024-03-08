package com.jun.newacademy.auth.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.newacademy.auth.security.usersecurity.UserDetailsImpl;
import com.jun.newacademy.model.dto.userdto.UserLoginRequestDto;
import com.jun.newacademy.model.entity.user.UserAuthority;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
// 필터를 쓰는 이유는 인증, 인가 처리와 비즈니스 로직 처리를 분리하기 위함이다
// Authentication : 인증 // Authorization : 인증
// 그래서 컨트롤러단의 로그인 처리가 별도로 필요하지 않게 되고 여기서 처리하게 됨
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // 아까 그 시큐리티 필터체인 중에 UsernamePasswordAuthenticationFilter
    // 걔가 제공받은 사용자의 username과 password를 기반으로 UsernamePasswordAutehnticationToken을 만듦
    // 저 UPAT가 인증 객체 Authentication 종류 중 하나임
    // 저 UPAT를 Authentication Manager에게 넘겨서 인증을 시도하는 건데...

    // 직접 상속해서 필터 기능을 확장하는 이유는, 현재 구현하는 방식이 존맛탱이어서 존맛탱 생성까지 기능을 덧붙이기 위함

    private final JwtUtil jwtUtil; // 로그인 성공 시, 존맛탱 발급을 위한 의존성 주입

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login"); // 로그인 처리 경로 설정(매우매우 중요)
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 로그인 시도를 담당함

        log.info("로그인 단계 진입");
        try {
            UserLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestDto.class); // 입력 스트림, 변환 타입 매개값으로
            // JSON 형태로 입력받은 요청을 Object로 바꿈

            // 인증 처리 (인증 객체 토큰 생성) - UsernamePasswordAuthenticationFilter 상속 받아서 메서드를 사용할 수 있게 된다
            // 인증 처리에서는 권한이 필요 없기 때문에 authorities 를 null 로 넣어준다
            return getAuthenticationManager().authenticate( // 인증 처리 하는 메소드
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    ) // Authentication 인증 객체의 종류 중 하나인 UPAT 생성해서 Authentication Manager에게 넘겨줘서 인증 성공 실패 여부 판단 시작
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");

        // 인증 객체 Authentication에 담긴 세 개의 값들 중 첫 번째인 UserDetails에 있는 내용 기반으로 부여된 username(이메일)을 얻는다
        // AuthenticationManager 가 담아준다
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        // 인증 객체 Authentication에 담긴 세 개의 값들 중 첫 번째인 UserDetails에 있는 내용 기반으로 유저 엔티티 인스턴스에 저장, 부여된 권한을 얻는다
        // AuthenticationManager 가 담아준다
        UserAuthority authority = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getAuthority();

        String token = jwtUtil.createToken(username, authority); // 얻은 username과 authority를 바탕으로 존맛탱 생성
        jwtUtil.addJwtToCookie(token, response); // 쿠키에 담아주기
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패 및 401 에러 반환");

        // 로그인 실패로 상태코드 반환
        response.setStatus(401);
    }
}
