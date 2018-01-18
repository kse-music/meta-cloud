package com.hiekn.metacloud.demo.bean.vo;


import com.hiekn.metacloud.demo.bean.UserBean;

public class UserLoginBean extends UserBean {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
