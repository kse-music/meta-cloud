package com.hiekn.metacloud.feign;

import cn.hiboot.mcn.core.model.result.RestResp;
import com.hiekn.metacloud.feign.bean.UserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * describe about this class
 *
 * @author: DingHao
 * @date: 2020/1/8 15:37
 */
//@RequestMapping("user")//如果这样，得保证消费者中不存在url和feign client一样的路径
@Validated
@FeignClient(value = "reactive-web",path = "/user/")
public interface UserApi {

    @GetMapping("list")
    RestResp<List<UserBean>> list();

    @PostMapping("json")
    RestResp<UserBean> postJson(@Validated @RequestBody UserBean userBean);

}
