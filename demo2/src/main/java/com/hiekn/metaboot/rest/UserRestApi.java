package com.hiekn.metaboot.rest;


import cn.hiboot.mcn.autoconfigure.web.util.BeanValidator;
import cn.hiboot.mcn.core.model.result.RestResp;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.hiekn.metaboot.bean.UserBean;
import com.hiekn.metaboot.conf.RemoteConfig;
import com.hiekn.metaboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Enumeration;
import java.util.Map;

@Controller
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Api("用户模块")
public class UserRestApi {

    @Autowired
    private Gson gson;

    @Autowired
    private UserService userService;

    @Autowired
    private RemoteConfig remoteConfig;

    @Autowired
    private HttpServletRequest request;

    @Value("${server.port}")
    private Integer port;

    @GET
    @Path("hi")
    @ApiOperation("hi")
    public RestResp hi(){
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String,String> data = Maps.newHashMap();
        while (headerNames.hasMoreElements()){
            String s = headerNames.nextElement();
            data.put(s,request.getHeader(s));
        }
        data.put("remoteConfig",remoteConfig.getFoo());
        data.put("demoPort",port.toString());
        return new RestResp<>(data);
    }

    @GET
    @Path("get")
    @ApiOperation("获取")
    public RestResp<UserBean> get(@ApiParam(required = true)@QueryParam("id") Integer id) {
        return new RestResp<>(userService.getByPrimaryKey(id));
    }

    @GET
    @Path("delete")
    @ApiOperation("删除")
    public RestResp delete(@ApiParam(required = true)@QueryParam("id") Integer id) {
        userService.deleteByPrimaryKey(id);
        return new RestResp<>();
    }

    @POST
    @Path("add")
    @ApiOperation("新增")
    public RestResp<UserBean> add(@ApiParam(required = true)@FormParam("bean") String bean) {
        UserBean userBean = gson.fromJson(bean, UserBean.class);
        BeanValidator.validate(userBean);
        userService.saveSelective(userBean);
        return new RestResp<>(userBean);
    }


    @POST
    @Path("update")
    @ApiOperation("修改")
    public RestResp update(@ApiParam(required = true)@QueryParam("id") Integer id,
                           @ApiParam(required = true)@FormParam("bean") String bean) {
        UserBean userBean = gson.fromJson(bean, UserBean.class);
        userBean.setId(id);
        userService.updateByPrimaryKeySelective(userBean);
        return new RestResp<>();
    }

    @POST
    @Path("login")
    @ApiOperation("登录")
    public RestResp<UserBean> login(@ApiParam(value="手机号",required=true)@FormParam("mobile") String mobile,
                                    @ApiParam(value="密码",required=true)@FormParam("password") String password){
        return new RestResp<>(userService.login(mobile,password));
    }

    @GET
    @Path("logout")
    @ApiOperation("登出")
    public RestResp<Object> logout(){
        userService.logout();
        return new RestResp<>();
    }

}
