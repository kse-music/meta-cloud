package cn.hiboot.web.rest;

import cn.hiboot.mcn.core.model.result.RestResp;
import com.hiekn.metacloud.feign.UserApi;
import com.hiekn.metacloud.feign.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user")
@RestController
@Validated
public class UserRestApi {

    @Autowired
    private UserApi userApi;

    @GetMapping("list")
    public RestResp<List<UserBean>> list() {
        return userApi.list();
    }

    @PostMapping("json")
    public RestResp<UserBean> postJson(@Validated @RequestBody UserBean userBean) {
        return userApi.postJson(userBean);
    }

}
