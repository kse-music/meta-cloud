package com.hiekn.metacloud.demo.service;


import com.hiekn.metacloud.demo.bean.UserBean;
import com.hiekn.metacloud.demo.bean.result.RestData;
import com.hiekn.metacloud.demo.bean.vo.Page;
import com.hiekn.metacloud.demo.bean.vo.UserLoginBean;

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
