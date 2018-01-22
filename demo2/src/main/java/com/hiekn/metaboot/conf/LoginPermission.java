package com.hiekn.metaboot.conf;

import com.hiekn.metaboot.bean.result.ErrorCodes;
import com.hiekn.metaboot.exception.ServiceException;
import com.hiekn.metaboot.service.TokenManagerService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

@Aspect
@Configuration
public class LoginPermission {

    @Value("#{'${exclude_method}'.split(',')}")
    private List<String> excludeMethod;

    @Autowired
    private TokenManagerService tokenManagerService;


    @Around("@within(org.springframework.stereotype.Controller)")
    public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable{
        String name = pjp.getSignature().getName();
        if(!excludeMethod.contains(name)){
            Object auth = pjp.getArgs()[0];
            if(Objects.isNull(auth)){//未登录
                throw ServiceException.newInstance(ErrorCodes.UN_LOGIN_ERROR);
            }
            if(!tokenManagerService.checkToken(auth.toString())){//未登录
                throw ServiceException.newInstance(ErrorCodes.UN_LOGIN_ERROR);
            }
        }
        return pjp.proceed();
    }

}
