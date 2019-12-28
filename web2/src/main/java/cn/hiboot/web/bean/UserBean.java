package cn.hiboot.web.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;

/**
 * describe about this class
 *
 * @author: DingHao
 * @date: 2019/6/29 17:16
 */
@Setter
@Getter
public class UserBean {

    private String name;

    @Max(200)
    private Integer age;

}
