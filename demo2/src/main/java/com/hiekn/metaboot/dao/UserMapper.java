package com.hiekn.metaboot.dao;

import cn.hiboot.mcn.core.mapper.BaseMapper;
import com.hiekn.metaboot.bean.UserBean;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserBean, String> {
}