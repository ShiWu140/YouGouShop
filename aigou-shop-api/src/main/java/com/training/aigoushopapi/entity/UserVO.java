package com.training.aigoushopapi.entity;
public class UserVO {
    //用户id
    private String userId;
    //jwt令牌
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
