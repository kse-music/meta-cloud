package com.hiekn.metaboot.service.impl;

import com.hiekn.metaboot.bean.UserBean;
import com.hiekn.metaboot.bean.result.ErrorCodes;
import com.hiekn.metaboot.bean.result.RestData;
import com.hiekn.metaboot.bean.vo.Page;
import com.hiekn.metaboot.bean.vo.TokenModel;
import com.hiekn.metaboot.bean.vo.UserLoginBean;
import com.hiekn.metaboot.dao.UserMapper;
import com.hiekn.metaboot.exception.ServiceException;
import com.hiekn.metaboot.service.TokenManagerService;
import com.hiekn.metaboot.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private static final Log logger = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenManagerService tokenManagerService;

    @Override
    public RestData<UserBean> listByPage(Page page) {
        List<UserBean> userList = userMapper.getUserList(page);
        logger.info("请使用logger替代System.out.println！！！");
        return new RestData<>(userList,userMapper.count());
    }

    @Override
    public UserBean get(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public UserBean getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<UserBean> list() {
        return userMapper.getUserList(null);
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
        UserBean user = userMapper.selectByUsername(username);
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
