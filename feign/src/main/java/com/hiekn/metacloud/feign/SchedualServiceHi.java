package com.hiekn.metacloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "demo2",fallback = SchedualServiceHi.SchedualServiceHiHystric.class)
public interface SchedualServiceHi {

    @RequestMapping(value = "/api/user/hi",method = RequestMethod.GET)
    String sayHiFromClientOne();

    @Component
    class SchedualServiceHiHystric implements SchedualServiceHi {

        @Override
        public String sayHiFromClientOne() {
            return "sorry ";
        }

    }
}
