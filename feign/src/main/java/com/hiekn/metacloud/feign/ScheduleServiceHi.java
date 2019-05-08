package com.hiekn.metacloud.feign;

import cn.hiboot.mcn.core.model.result.RestResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "demo2",fallback = ScheduleServiceHi.ScheduleServiceHiHystrix.class)
public interface ScheduleServiceHi {

    @RequestMapping(value = "/user/test",method = RequestMethod.GET)
    RestResp sayHiFromClientOne();

    @Component
    class ScheduleServiceHiHystrix implements ScheduleServiceHi {

        @Override
        public RestResp sayHiFromClientOne() {
            RestResp restResp = new RestResp("hi,sorry,error!");
            restResp.setActionStatus(RestResp.ActionStatusMethod.FAIL.toString());
            return restResp;
        }

    }
}
