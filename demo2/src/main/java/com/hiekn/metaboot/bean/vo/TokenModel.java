package com.hiekn.metaboot.bean.vo;

/**
 * 约定前台传入的验证为 userId_token并用BASE64编码
 */
public class TokenModel {

    private long userId;

    private String token;

    public TokenModel(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
