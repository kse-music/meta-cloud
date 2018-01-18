package com.hiekn.metaboot.service;

import com.hiekn.metaboot.bean.UserBean;
import com.hiekn.metaboot.bean.result.RestData;
import com.hiekn.metaboot.bean.vo.Page;
import com.hiekn.metaboot.bean.vo.UserLoginBean;

import java.util.List;

public interface UserService {
    RestData<UserBean> listByPage(Page page);
    UserBean get(Integer id);
    UserBean getByUsername(String username);
    List<UserBean> list();
    UserBean save(UserBean userBean);

    UserLoginBean login(String username, String password);
    void logout(String authentication);
}
