package com.hiekn.metacloud.feign.bean;

import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;



/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/6/29 17:16
 */
@Setter
@Getter
public class UserBean {

    private String name;
    @Max(200)
    private Integer age;

    private Integer port;

}
