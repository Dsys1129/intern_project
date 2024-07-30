package com.intern_project.user.service;

import com.intern_project.global.jwt.JwtUtil;
import com.intern_project.user.domain.*;
import com.intern_project.user.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 사용자 그룹 계정 가입
    public void register(RegisterRequestDTO requestDTO) {
        if (isEmailDuplicate(requestDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }
        UserGroup userGroup = new UserGroup();
        userGroup.setEmail(requestDTO.getEmail());
        String encryptedPassword = passwordEncoder.encode(requestDTO.getPassword());
        userGroup.setPassword(encryptedPassword);
        userMapper.insertUserGroup(userGroup);
        User user = new User();
        user.setGender(requestDTO.getGender());
        user.setName(requestDTO.getName());
        user.setBirthDate(requestDTO.getBirthDate());
        user.setGroupId(userMapper.findUserGroupByEmail(userGroup.getEmail()).getId());
        userMapper.insertUser(user);
    }
    // 이메일 중복 검사
    public boolean isEmailDuplicate(String email) {
        return userMapper.findUserGroupByEmail(email) != null;
    }

    //로그인
    public String login(String email, String password) {
        UserGroup userGroup = authenticate(email, password);
        if (userGroup == null) {
            throw new IllegalStateException("Invalid username or password");
        }
        User user = userMapper.findFirstUserByGroupId(userGroup.getId());
        return jwtUtil.createToken(userGroup.getId(), user.getId());
    }
    //검증
     public UserGroup authenticate(String email, String password) {
        UserGroup userGroup = userMapper.findUserGroupByEmail(email);
        if (userGroup != null && passwordEncoder.matches(password, userGroup.getPassword())) {
            return userGroup;
        } else {
            return null;
        }
    }

    //사용자 생성
    public void createUser(User user) {
        userMapper.insertUser(user);
    }

    // 그룹 ID로 UserGroup 찾기
    public UserGroup findUserGroupById(Long id) {
        return userMapper.findUserGroupById(id);
    }

    // 이메일과 비밀번호로 UserGroup 찾기
    public UserGroup findUserGroupByEmailAndPassword(String email, String password) {
        return userMapper.findUserGroupByEmailAndPassword(email, password);
    }

    // email로 그룹 아이디 찾기
    public UserGroup getUserGroupByEmail(String email) {
        return userMapper.findUserGroupByEmail(email);
    }

    public List<User> getUserListByGroupId(Long groupId) {
        return userMapper.findUserListByGroupId(groupId);
    }


}
