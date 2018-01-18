package com.hiekn.metacloud.ribbon;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RemoteService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService() {

        return restTemplate.getForObject("http://META-BOOT/api/user/hi", String.class);
    }

    public String hiError() {
        return "hi,sorry,error!";
    }
}
