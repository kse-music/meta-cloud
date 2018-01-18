package com.hiekn.metaboot.rest;

import com.hiekn.metaboot.bean.UserBean;
import com.hiekn.metaboot.bean.result.RestData;
import com.hiekn.metaboot.bean.result.RestResp;
import com.hiekn.metaboot.bean.vo.Page;
import com.hiekn.metaboot.service.UserService;
import com.hiekn.metaboot.util.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import java.util.List;

@RestController
@Path("/user")
@Api("用户模块")
public class UserRestApi {

    @Autowired
    private UserService userService;

    @GET
    @Path("/hi")
    public String hi(){
        return "hi";
    }


    @GET
    @Path("/list/page")
    @ApiOperation("分页")
    public RestResp<RestData<UserBean>> listByPage(@ApiParam("BASE64(userId_token)")@QueryParam("authentication") String authentication,
                                                   @BeanParam Page page) {
        return new RestResp<>(userService.listByPage(page));
    }

    @GET
    @Path("/get")
    @ApiOperation("获取")
    public RestResp<UserBean> get(@ApiParam("BASE64(userId_token)")@QueryParam("authentication") String authentication,
                                  @ApiParam(required = true)@QueryParam("id") Integer id) {
        return new RestResp<>(userService.get(id));
    }

    @GET
    @Path("/list")
    @ApiOperation("列表")
    public RestResp<List<UserBean>> list(@ApiParam("BASE64(userId_token)")@QueryParam("authentication") String authentication) {
        return new RestResp<>(userService.list());
    }

    @POST
    @Path("/add")
    @ApiOperation("新增")
    public RestResp<UserBean> add(@ApiParam("BASE64(userId_token)")@QueryParam("authentication") String authentication,
                                  @ApiParam(required = true)@FormParam("bean") String bean) {
        UserBean userBean = JsonUtils.fromJson(bean, UserBean.class);
        userService.save(userBean);
        return new RestResp<>(userBean);
    }

    @POST
    @Path("/login")
    @ApiOperation("登录")
    public RestResp<UserBean> login(@ApiParam(value="用户名/邮箱",required=true)@FormParam("username") String username,
                                    @ApiParam(value="密码",required=true)@FormParam("password") String password){
        return new RestResp<>(userService.login(username,password));
    }

    @GET
    @Path("/logout")
    @ApiOperation("登出")
    public RestResp<Object> logout(@ApiParam(required=true)@QueryParam("authentication") String authentication){
        userService.logout(authentication);
        return new RestResp<>();
    }

}
