package com.hiekn.metacloud.demo.rest;

import cn.hiboot.mcn.core.model.result.RestResp;
import com.hiekn.metacloud.demo.bean.UserBean;
import com.hiekn.metacloud.demo.service.RemoteService;
import com.hiekn.metacloud.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RefreshScope
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserRestApi {

    @Autowired
    private UserService userService;

    @Autowired
    private RemoteService remoteService;

    @Value("${foo}")
    private String foo;

    @GetMapping("/hi")
    @ApiOperation("hi")
    public RestResp hi(){
        RestResp<Map<String, Object>> hi = remoteService.hi();
        hi.getData().put("mvc","i am mvc");
        RestResp<UserBean> add = remoteService.add("{password:123456,mobile:18362109799,nickname:nick}");
        hi.getData().put("mvc",add.getData().getId());
        return hi;
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
