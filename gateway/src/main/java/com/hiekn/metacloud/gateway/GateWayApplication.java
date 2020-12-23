package com.hiekn.metacloud.gateway;

import cn.hiboot.mcn.core.exception.ErrorMsg;
import cn.hiboot.mcn.core.model.result.RestResp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

    @RequestMapping("/fallback")
    public RestResp fallback() {
        return new RestResp<>(ErrorMsg.REMOTE_SERVICE_ERROR,ErrorMsg.getErrorMsg(ErrorMsg.REMOTE_SERVICE_ERROR));
    }

}
