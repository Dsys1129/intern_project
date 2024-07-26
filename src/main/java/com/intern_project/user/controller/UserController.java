package com.intern_project.user.controller;

import com.intern_project.user.domain.ChangeUserRequest;
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

import java.util.List;

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
            int groupId = userGroup.getId();
            int userId = user.getId();
            String token = jwtUtil.createToken(groupId, userId);
            return ResponseEntity.ok(token);
        } else {
            // 유저 없음 메세지와 null user 생성해서 보내기
            return null;
        }
    }

    //사용자 생성
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            if (claims != null) {
                int groupId = claims.get("groupId", Integer.class);
                int userId = claims.get("userId", Integer.class);
                UserGroup userGroup = userService.findUserGroupById(groupId);
                if (userGroup != null) {
                    user.setGroupId(userGroup.getId());
                    userService.createUser(user);
                    return ResponseEntity.ok(jwtUtil.createToken(userGroup.getId(), user.getId()));
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or UserGroup not found");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/userList")
    public ResponseEntity<List<User>> getUserList() {
        try{
            Claims claims = (Claims) request.getAttribute("claims");
            if(claims != null) {
                int groupId = claims.get("groupId", Integer.class);
                List<User> users = userService.getUserListByGroupId(groupId);
                return ResponseEntity.ok(users);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // 새로운 메서드 (유저 변경)
    @PostMapping("/changeUser")
    public ResponseEntity<String> changeUser(@RequestBody ChangeUserRequest changeUserRequest) {
        String newToken = jwtUtil.createToken(changeUserRequest.getGroupId(), changeUserRequest.getUserId());
        return ResponseEntity.ok(newToken);
    }


//    @GetMapping("/userList")
//    public ResponseEntity<List<User>> getUserList(Claims claims) {
//        try{
//            if(claims != null) {
//                int groupId = claims.get("groupId", Integer.class);
//                List<User> users = userService.getUserListByGroupId(groupId);
//                return ResponseEntity.ok(users);
//            }else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//            }
//        }catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }

}


