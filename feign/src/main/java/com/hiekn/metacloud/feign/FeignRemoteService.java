package com.hiekn.metacloud.feign;

import cn.hiboot.mcn.core.model.result.RestResp;
import com.hiekn.metacloud.feign.bean.UserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "reactive-web")
public interface FeignRemoteService {

    @GetMapping("/user/list")
    RestResp<List<UserBean>> list();

}
