package com.jun.newacademy.controller;

import com.jun.newacademy.entity.User;
import com.jun.newacademy.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TempController {

    @GetMapping
    public ResponseEntity<String> signUp(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // Authentication의 Principal 부분에 들어가는 userDetails
            User user = userDetails.getUser();
            System.out.println("user.getEmail() = " + user.getEmail());

            return ResponseEntity.ok("인가 테스트 성공.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("너 코드 잘못 짰어");
        }
    }
}
