package com.hiekn.metacloud.feign;

import cn.hiboot.mcn.core.model.result.RestResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteController {

    @Autowired
    private FeignRemoteService scheduleServiceHi;

    @GetMapping("/hi")
    public RestResp sayHi(){
        return scheduleServiceHi.hiFeign();
    }

}