package cn.hiboot.web.rest;

import cn.hiboot.mcn.core.model.result.RestResp;
import cn.hiboot.web.bean.UserBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("user")
@RestController
@Validated
public class UserRestApi {

    @Value("${server.port}")
    private Integer port;

    @Value("${foo}")
    private String foo;

    @GetMapping("list")
    public RestResp<List<UserBean>> list() {
        List<UserBean> rs = new ArrayList<>();
        UserBean userBean = new UserBean();
        userBean.setName(foo);
        userBean.setPort(port);
        rs.add(userBean);
        return new RestResp<>(rs);
    }

    @PostMapping("json")
    public RestResp<UserBean> postJson(@Validated @RequestBody UserBean userBean) {
        return new RestResp<>(userBean);
    }

}
