package com.hiekn.metaboot.service.impl;

import com.hiekn.boot.autoconfigure.base.exception.ServiceException;
import com.hiekn.boot.autoconfigure.base.service.BaseServiceImpl;
import com.hiekn.boot.autoconfigure.jwt.JwtToken;
import com.hiekn.metaboot.bean.UserBean;
import com.hiekn.metaboot.dao.UserMapper;
import com.hiekn.metaboot.exception.ErrorCodes;
import com.hiekn.metaboot.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserBean,String> implements UserService {

    private static final Log logger = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtToken jwtToken;

    @Override
    public UserBean getByMobile(String mobile) {
        logger.debug("请使用logger.debug替代System.out.println()！！！");
        UserBean userBean = new UserBean();
        userBean.setMobile(mobile);
        List<UserBean> list = selectByCondition(userBean);
        return list != null && !list.isEmpty() ? list.get(0):null;
    }

    @Override
    public UserBean login(String mobile, String password) {
        if(StringUtils.isBlank(mobile) || StringUtils.isBlank(password)){
            throw ServiceException.newInstance(ErrorCodes.PARAM_PARSE_ERROR);
        }
        UserBean user = getByMobile(mobile);
        if(Objects.isNull(user)){
            throw ServiceException.newInstance(ErrorCodes.NOT_FOUND_ERROR);
        }
        if(!Objects.equals(DigestUtils.md5Hex(password), user.getPassword())){
            throw ServiceException.newInstance(ErrorCodes.USER_PWD_ERROR);
        }
        jwtToken.createToken(user.getId());
        user.setPassword(null);
        return user;
    }


    @Override
    public void logout() {
        redisTemplate.delete (jwtToken.getUserIdAsString());
    }

}