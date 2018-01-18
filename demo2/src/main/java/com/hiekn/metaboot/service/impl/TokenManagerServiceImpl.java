package com.hiekn.metaboot.service.impl;

import com.hiekn.metaboot.bean.vo.TokenModel;
import com.hiekn.metaboot.conf.Constants;
import com.hiekn.metaboot.service.TokenManagerService;
import com.hiekn.metaboot.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class TokenManagerServiceImpl implements TokenManagerService{

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public TokenModel createToken (long userId) {
        // 使用 uuid 作为源 token
        String token = CommonUtils.getRandomUUID();
        TokenModel model = new TokenModel (userId, token);
        // 存储到 redis 并设置过期时间
        redisTemplate.boundValueOps (userId).set (token, Constants.tokenExpiresTimeDay, TimeUnit.DAYS);
        return model;
    }

    @Override
    public TokenModel getToken (String authentication) {
        if (authentication == null || authentication.length () == 0) {
            return null;
        }
        String auth = new String(Base64.getDecoder().decode(authentication));
        String [] param = auth.split ("_");
        if (param.length != 2) {
            return null;
        }
        // 使用 userId 和源 token 简单拼接成的 token，可以增加加密措施
        long userId = Long.valueOf (param [0]);
        String token = param [1];
        return new TokenModel (userId, token);
    }

    @Override
    public boolean checkToken (String authentication) {
        TokenModel model = getToken(authentication);
        if (model == null) {
            return false;
        }
        Object token = redisTemplate.boundValueOps (model.getUserId ()).get();
        if (token == null || !token.equals (model.getToken ())) {
            return false;
        }
        // 如果验证成功，说明此用户进行了一次有效操作，延长 token 的过期时间
        redisTemplate.boundValueOps (model.getUserId ()).expire (Constants.tokenExpiresTimeDay, TimeUnit.DAYS);
        return true;
    }

    @Override
    public void deleteToken (long userId) {
        redisTemplate.delete (userId);
    }

}
