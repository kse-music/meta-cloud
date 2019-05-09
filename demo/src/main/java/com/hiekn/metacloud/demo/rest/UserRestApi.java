package com.hiekn.metacloud.demo.rest;

import cn.hiboot.mcn.core.model.result.RestResp;
import com.google.common.collect.Maps;
import com.hiekn.metacloud.demo.bean.UserBean;
import com.hiekn.metacloud.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@RefreshScope
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserRestApi {

    @Autowired
    private UserService userService;

    @Value("${foo}")
    private String foo;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/hi")
    @ApiOperation("hi")
    public RestResp<Object> hi(){
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String,String> data = Maps.newHashMap();
        while (headerNames.hasMoreElements()){
            String s = headerNames.nextElement();
            data.put(s,request.getHeader(s));
        }
        data.put("remoteConfig",foo);
        return new RestResp<>(data);
    }

    @GetMapping("/get")
    @ApiOperation("获取")
    public RestResp<UserBean> get(@RequestParam Integer id) {
        return new RestResp<>(userService.getByPrimaryKey(id));
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    public RestResp<UserBean> add( @RequestBody UserBean userBean) {
        userService.save(userBean);
        return new RestResp<>(userBean);
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public RestResp<UserBean> login(@ApiParam(value = "用户名/邮箱",required = true) String username,
                                    @RequestParam String password){
        return new RestResp<>(userService.login(username,password));
    }

    @PostMapping("/logout")
    @ApiOperation("登出")
    public RestResp<Object> logout(@RequestParam String authentication){
        userService.logout(authentication);
        return new RestResp<>();
    }

}
