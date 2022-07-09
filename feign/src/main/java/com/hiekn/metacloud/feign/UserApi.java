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
 * @author DingHao
 * @since 2020/1/8 15:37
 */
//url的使用可以支持哪些不需要注册服务仅仅使用feign的项目
@Validated
@FeignClient(value = "${reactive.web:reactive-web}",url = "${reactive.web.url:}",path = "/user")
public interface UserApi {

    @GetMapping("list")
    RestResp<List<UserBean>> list();

    @PostMapping("json")
    RestResp<UserBean> postJson(@Validated @RequestBody UserBean userBean);

}
