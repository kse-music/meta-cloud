package com.hiekn.metacloud.demo.dao;

import cn.hiboot.mcn.core.mapper.BaseMapper;
import com.hiekn.metacloud.demo.bean.UserBean;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserBean,Integer> {

}
