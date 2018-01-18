package com.hiekn.metacloud.demo.dao;

import com.hiekn.metacloud.demo.bean.UserBean;
import com.hiekn.metacloud.demo.bean.vo.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    List<UserBean> getUserList(Page page);
    int count();
    UserBean selectById(Integer id);
    void insert(UserBean userBean);
    UserBean selectByUsername(String username);
}
