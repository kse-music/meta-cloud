package com.hiekn.metacloud.demo.rest;

import com.hiekn.metacloud.demo.bean.UserBean;
import com.hiekn.metacloud.demo.bean.result.RestData;
import com.hiekn.metacloud.demo.bean.result.RestResp;
import com.hiekn.metacloud.demo.bean.vo.Page;
import com.hiekn.metacloud.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserRestApi {

    @Autowired
    private UserService userService;

    @Value("${foo}")
    private String foo;

    @GetMapping("/hi")
    @ApiOperation("hi")
    public RestResp<Object> hi(String authentication){
        return new RestResp<>(authentication+" = "+foo);
    }

    @GetMapping("/list/page")
    @ApiOperation("分页")
    public RestResp<RestData<UserBean>> listByPage(String authentication, Page page) {
        return new RestResp<>(userService.listByPage(page));
    }

    @GetMapping("/get")
    @ApiOperation("获取")
    public RestResp<UserBean> get(String authentication,
                                  @RequestParam Integer id) {
        return new RestResp<>(userService.get(id));
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public RestResp<List<UserBean>> list(String authentication) {
        return new RestResp<>(userService.list());
    }

    @PostMapping(value="/add")
    @ApiOperation("新增")
    public RestResp<UserBean> add(String authentication,
                                   @RequestBody UserBean userBean) {
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
