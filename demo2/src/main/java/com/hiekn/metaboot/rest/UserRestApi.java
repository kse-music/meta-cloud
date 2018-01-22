package com.hiekn.metaboot.rest;

import com.hiekn.metaboot.bean.UserBean;
import com.hiekn.metaboot.bean.result.RestData;
import com.hiekn.metaboot.bean.result.RestResp;
import com.hiekn.metaboot.bean.vo.Page;
import com.hiekn.metaboot.conf.RemoteConfig;
import com.hiekn.metaboot.service.UserService;
import com.hiekn.metaboot.util.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Api("用户模块")
public class UserRestApi {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    private RemoteConfig remoteConfig;

    @GET
    @Path("/hi")
    @ApiOperation("test")
    public RestResp<Object> hi(@QueryParam("authentication") String authentication){
        return new RestResp<>(remoteConfig.getFoo()+" = "+port);
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
