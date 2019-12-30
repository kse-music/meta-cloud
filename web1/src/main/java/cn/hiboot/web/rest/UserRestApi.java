package cn.hiboot.web.rest;

import cn.hiboot.mcn.core.model.result.RestResp;
import com.hiekn.metacloud.feign.FeignRemoteService;
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
    private FeignRemoteService feignRemoteService;

    @GetMapping("list")
    public RestResp<List<UserBean>> list() {
        return feignRemoteService.list();
    }

    @PostMapping("json")
    public RestResp<UserBean> postJson(@Validated @RequestBody UserBean userBean) {
        return new RestResp<>(userBean);
    }

}
