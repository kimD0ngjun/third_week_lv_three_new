package com.jun.newacademy.auth.security.usersecurity;

import com.jun.newacademy.model.entity.user.User;
import com.jun.newacademy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    // 아까 Authentication 인증 객체에 담을 Principal에 해당하는 UserDetails 생성
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email) // 입력받은 이메일로 DB에서 유저 엔티티 조회
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + email));

        return new UserDetailsImpl(user);
    }
}