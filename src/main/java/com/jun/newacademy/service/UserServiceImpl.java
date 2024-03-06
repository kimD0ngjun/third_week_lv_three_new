package com.jun.newacademy.service;

import com.jun.newacademy.dto.UserSignUpRequestDto;
import com.jun.newacademy.entity.User;
import com.jun.newacademy.entity.UserAuthority;
import com.jun.newacademy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<String> signUp(UserSignUpRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String department = requestDto.getDepartment();

        // 권한 설정
        UserAuthority role;
        if (department.equals("커리큘럼") || department.equals("개발")) {
            role = UserAuthority.MANAGER;
        } else if (department.equals("마케팅")) {
            role = UserAuthority.STAFF;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 부서입니다.");
        }

        User user = new User(email, password, department, role);
        userRepository.save(user);

        return ResponseEntity.ok("가입에 성공하였습니다");
    }
}
