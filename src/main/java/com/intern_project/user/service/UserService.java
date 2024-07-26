package com.intern_project.user.service;

import com.intern_project.user.domain.User;
import com.intern_project.user.domain.UserGroup;
import com.intern_project.user.mapper.UserMapper;
import com.intern_project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;

    // 사용자 그룹 계정 가입
    public void register(UserGroup userGroup) {
        if (isEmailDuplicate(userGroup.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        userMapper.insertUserGroup(userGroup);
    }

    // 이메일 중복 검사
    public boolean isEmailDuplicate(String email) {
        return userMapper.findUserGroupByEmail(email) != null;
    }

    //유저 존재 확인
    public User UserExist(String email, String password) {
        UserGroup userGroup = userMapper.findUserGroupByEmailAndPassword(email, password);
        if (userGroup == null) {
            return null; // UserGroup이 null이면 유저를 찾을 수 없음
        }
        User user = userMapper.findFirstUserByGroupId(userGroup.getId());
        return user; // 유저가 없으면 null을 반환
    }

    //검증
     public UserGroup authenticate(String email, String password) {
        UserGroup userGroup = userMapper.findUserGroupByEmailAndPassword(email, password);
        if (userGroup == null) {
            throw new RuntimeException("Invalid login credentials");
        }
        return userGroup;
    }

    //사용자 생성
    public void createUser(User user) {
        userMapper.insertUser(user);
    }

    // 그룹 ID로 UserGroup 찾기
    public UserGroup findUserGroupById(int id) {
        return userMapper.findUserGroupById(id);
    }

    // 이메일과 비밀번호로 UserGroup 찾기
    public UserGroup findUserGroupByEmailAndPassword(String email, String password) {
        return userMapper.findUserGroupByEmailAndPassword(email, password);
    }


//    // 토큰에서 UserGroup 가져오기
//    public UserGroup getUserGroupFromToken(String token) {
//        String email = jwtUtil.getEmailFromToken(token);
//        if (email != null) {
//            return userMapper.findUserGroupByEmail(email);
//        }
//        return null;
//    }

    // email로 그룹 아이디 찾기
    public UserGroup getUserGroupByEmail(String email) {
        return userMapper.findUserGroupByEmail(email);
    }
}
