package com.hiekn.metacloud.feign;

import cn.hiboot.mcn.core.model.result.RestResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RemoteController {

    @Resource
    private FeignRemoteService scheduleServiceHi;

    @GetMapping("/hi")
    public RestResp sayHi(){
        return scheduleServiceHi.hiFeign();
    }

}