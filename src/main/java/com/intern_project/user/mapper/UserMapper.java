package com.intern_project.user.mapper;

import com.intern_project.user.domain.User;
import com.intern_project.user.domain.UserGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    //새로운 계정 회원가입
    void insertUserGroup(UserGroup userGroup);
    //계정 로그인
    UserGroup findUserGroupByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    //사용자 추가, 찾기
    void insertUser(User user);
    //1번 유저 리턴
    User findFirstUserByGroupId(@Param("groupId") int groupId);
    //이메일로 계정찾기
    UserGroup findUserGroupByEmail(@Param("email") String email);
    //groupid로 계정찾기
    UserGroup findUserGroupById(@Param("id") int id);



}
