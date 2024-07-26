package com.intern_project.user.controller;

import com.intern_project.user.domain.LoginRequest;
import com.intern_project.user.domain.User;
import com.intern_project.user.domain.UserGroup;
import com.intern_project.user.service.UserService;
import com.intern_project.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HttpServletRequest request;

    //계정 정보 받아 회원가입
    @PostMapping("/register")
    public void register(@RequestBody UserGroup userGroup) {
        userService.register(userGroup);
    }

    // 계정으로 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        UserGroup userGroup;
        User user;
        userGroup = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (userGroup == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }
        user = userService.UserExist(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {
            String token = jwtUtil.createToken(loginRequest.getEmail());
            return ResponseEntity.ok(token);
        } else {
            // 유저 없음 메세지 전달
            return null;
        }
    }

    //사용자 생성
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            if (claims != null) {
                String email = claims.getSubject();
                UserGroup userGroup = userService.getUserGroupByEmail(email);
                if (userGroup != null) {
                    user.setGroupId(userGroup.getId());
                    userService.createUser(user);
                    return ResponseEntity.ok(jwtUtil.createToken(userGroup.getEmail()));
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or UserGroup not found");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}


