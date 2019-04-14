package com.hiekn.metacloud.demo.service;


import cn.hiboot.mcn.core.service.BaseService;
import com.hiekn.metacloud.demo.bean.UserBean;
import com.hiekn.metacloud.demo.bean.vo.UserLoginBean;

public interface UserService extends BaseService<UserBean,Integer> {
    UserBean getByUsername(String username);
    UserLoginBean login(String username, String password);
    void logout(String authentication);
}
