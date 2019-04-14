package com.hiekn.metacloud.demo.service.impl;

import cn.hiboot.mcn.core.exception.ServiceException;
import cn.hiboot.mcn.core.service.BaseServiceImpl;
import com.hiekn.metacloud.demo.bean.UserBean;
import com.hiekn.metacloud.demo.bean.vo.TokenModel;
import com.hiekn.metacloud.demo.bean.vo.UserLoginBean;
import com.hiekn.metacloud.demo.dao.UserMapper;
import com.hiekn.metacloud.demo.exception.ErrorCodes;
import com.hiekn.metacloud.demo.service.TokenManagerService;
import com.hiekn.metacloud.demo.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserBean,Integer> implements UserService {

    private static final Log logger = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenManagerService tokenManagerService;

    @Override
    public UserBean getByUsername(String username) {
        logger.info("请使用logger替代System.out.println！！！");
        UserBean userBean = new UserBean();
        userBean.setUsername(username);
        return userMapper.selectByCondition(userBean).get(0);
    }

    @Override
    public UserBean save(UserBean userBean) {
        UserBean user = getByUsername(userBean.getUsername());
        if(Objects.nonNull(user)){
            throw ServiceException.newInstance(ErrorCodes.USER_EXIST_ERROR);
        }
        userMapper.insert(userBean);
        return userBean;
    }


    @Override
    public UserLoginBean login(String username, String password) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            throw ServiceException.newInstance(ErrorCodes.PARAM_PARSE_ERROR);
        }
        UserBean user = getByUsername(username);
        if(Objects.isNull(user)){
            throw ServiceException.newInstance(ErrorCodes.USER_NOT_FOUND_ERROR);
        }
        if(!Objects.equals(DigestUtils.md5Hex(password), user.getPassword())){
            throw ServiceException.newInstance(ErrorCodes.USER_PWD_ERROR);
        }
        UserLoginBean userLoginBean = new UserLoginBean();
        BeanUtils.copyProperties(user,userLoginBean);
        TokenModel tokenModel = tokenManagerService.createToken(user.getId());
        userLoginBean.setToken(tokenModel.getToken());
        userLoginBean.setPassword(null);
        return userLoginBean;
    }


    @Override
    public void logout(String authentication) {
        TokenModel tokenModel = tokenManagerService.getToken(authentication);
        if(Objects.nonNull(tokenModel)){
            tokenManagerService.deleteToken(tokenModel.getUserId());
        }
    }

}
