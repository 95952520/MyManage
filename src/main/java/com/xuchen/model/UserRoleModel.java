package com.xuchen.model;

import com.xuchen.entity.SysRole;

public class UserRoleModel extends SysRole {
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
