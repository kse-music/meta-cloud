package com.hiekn.metacloud.ribbon;

import cn.hiboot.mcn.core.model.result.RestResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteController {

    @Autowired
    private RemoteService remoteService;

    @RequestMapping("/hi")
    public RestResp hi(){
        return remoteService.hiService();
    }


}
