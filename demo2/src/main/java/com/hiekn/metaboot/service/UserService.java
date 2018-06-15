package com.hiekn.metaboot.service;

import com.hiekn.boot.autoconfigure.base.service.BaseService;
import com.hiekn.metaboot.bean.UserBean;

public interface UserService extends BaseService<UserBean, String> {
    UserBean getByMobile(String mobile);
    UserBean login(String mobile, String password);
    void logout();
}