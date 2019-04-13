package com.hiekn.metaboot.validator;

import cn.hiboot.mcn.autoconfigure.web.util.SpringBeanUtils;
import com.hiekn.metaboot.bean.UserBean;
import com.hiekn.metaboot.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValidatorForMobile implements ConstraintValidator<UniqueMobile, String> {

    public void initialize(UniqueMobile constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        UserService userService = SpringBeanUtils.getBean(UserService.class);
        UserBean userBean = new UserBean();
        userBean.setMobile(value);
        List<UserBean> list = userService.selectByCondition(userBean);
        return list == null || list.isEmpty();
    }
}

