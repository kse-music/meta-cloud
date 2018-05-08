package com.hiekn.metaboot.conf;

import com.hiekn.boot.autoconfigure.base.exception.ServiceException;
import com.hiekn.boot.autoconfigure.jwt.JwtToken;
import com.hiekn.metaboot.exception.ErrorCodes;
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
    private JwtToken jwtToken;

    @Around("@within(org.springframework.stereotype.Controller)")
    public Object checkLogin(ProceedingJoinPoint pjp) throws Throwable{
        String name = pjp.getSignature().getName();
        if(!excludeMethod.contains(name)){
            try {
                String token = jwtToken.getToken();
                jwtToken.checkToken(token);
            } catch (Exception e) {
                throw ServiceException.newInstance(ErrorCodes.AUTHENTICATION_ERROR);
            }
        }
        return pjp.proceed();
    }

}
