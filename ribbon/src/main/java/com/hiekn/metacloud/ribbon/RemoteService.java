package com.hiekn.metacloud.ribbon;

import cn.hiboot.mcn.core.model.result.RestResp;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RemoteService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public RestResp hiService() {

        return restTemplate.getForObject("http://DEMO2/user/test", RestResp.class);
    }

    public RestResp hiError() {
        RestResp restResp = new RestResp("hi,sorry,error!");
        restResp.setActionStatus(RestResp.ActionStatusMethod.FAIL.toString());
        return restResp;
    }
}
