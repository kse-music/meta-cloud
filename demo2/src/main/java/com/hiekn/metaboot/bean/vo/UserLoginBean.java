package com.hiekn.metaboot.bean.vo;

import com.hiekn.metaboot.bean.UserBean;

public class UserLoginBean extends UserBean{

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
