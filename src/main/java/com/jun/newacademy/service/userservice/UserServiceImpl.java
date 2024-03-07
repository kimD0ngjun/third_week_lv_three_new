package com.jun.newacademy.service.userservice;

import com.jun.newacademy.model.dto.userdto.UserSignUpRequestDto;
import com.jun.newacademy.model.entity.user.User;
import com.jun.newacademy.model.entity.user.UserAuthority;
import com.jun.newacademy.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseEntity<String> signUp(@Valid UserSignUpRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword()); // 암호화 설정
        String department = requestDto.getDepartment();

        UserAuthority authority;
        if (department.equals("커리큘럼") || department.equals("개발")) {
            authority = UserAuthority.MANAGER;
        } else if (department.equals("마케팅")) {
            authority = UserAuthority.STAFF;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 부서입니다.");
        }

        User user = new User(email, password, department, authority);
        userRepository.save(user);

        return ResponseEntity.ok("가입에 성공하였습니다");
    }
}
