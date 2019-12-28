package cn.hiboot.web.rest;

import cn.hiboot.mcn.core.model.result.RestResp;
import cn.hiboot.web.bean.UserBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user")
@RestController
@Validated
public class UserRestApi {

    @GetMapping("list")
    public RestResp<List<UserBean>> list() {
        return new RestResp<>();
    }

    @PostMapping("json")
    public RestResp<UserBean> postJson(@Validated @RequestBody UserBean userBean) {
        return new RestResp<>(userBean);
    }

}
