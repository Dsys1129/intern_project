package com.intern_project.user.domain;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
public class UserInfo {
    private Long userId;
    private Long groupId;


    public UserInfo(Long groupId, Long userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
