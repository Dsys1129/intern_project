package com.intern_project.user.controller;

import com.intern_project.user.domain.*;
import com.intern_project.user.service.UserService;
import com.intern_project.util.JwtUtil;
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
public class UserController implements SwaggerApi{

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HttpServletRequest request;

    //계정 정보 받아 회원가입
    @PostMapping("/register")
    public void register(@RequestBody RegisterRequestDTO requestDTO) {
        userService.register(requestDTO);
    }

    // 계정으로 로그인 예외처리추가
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = userService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist");
        }
    }

    //사용자 생성
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO, @RequestAttribute("userinfo") UserInfo userInfo) {
        try {
            Long groupId = userInfo.getGroupId();
            Long userId = userInfo.getUserId();
            UserGroup userGroup = userService.findUserGroupById(groupId);
            if (userGroup != null) {
                User user = new User();
                user.setId(userId);
                user.setGroupId(groupId);
                user.setBirthDate(createUserRequestDTO.getBirthDate());
                user.setGender(createUserRequestDTO.getGender());
                user.setName(createUserRequestDTO.getName());
                userService.createUser(user);
                return ResponseEntity.ok(jwtUtil.createToken(userGroup.getId(), userId));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or UserGroup not found");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/userList")
    public ResponseEntity<List<User>> getUserList(@RequestAttribute("userinfo") UserInfo userInfo) {
        try {
            Long groupId = userInfo.getGroupId();
            List<User> users = userService.getUserListByGroupId(groupId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @PostMapping("/changeUser")
    public ResponseEntity<String> changeUser(@RequestBody ChangeUserRequestDTO changeUserRequestDTO) {
        String newToken = jwtUtil.createToken(changeUserRequestDTO.getGroupId(), changeUserRequestDTO.getUserId());
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


