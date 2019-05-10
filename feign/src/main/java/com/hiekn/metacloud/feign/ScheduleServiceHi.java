package com.hiekn.metacloud.feign;

import cn.hiboot.mcn.core.exception.ErrorMsg;
import cn.hiboot.mcn.core.model.result.RestResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "demo2",fallback = ScheduleServiceHi.ScheduleServiceHiHystrix.class)
public interface ScheduleServiceHi {

    @RequestMapping(value = "/user/hi",method = RequestMethod.GET)
    RestResp sayHiFromClientOne();

    @Component
    class ScheduleServiceHiHystrix implements ScheduleServiceHi {

        @Override
        public RestResp sayHiFromClientOne() {
            return new RestResp<>(ErrorMsg.REMOTE_SERVICE_ERROR,ErrorMsg.getErrorMsg(ErrorMsg.REMOTE_SERVICE_ERROR));
        }

    }
}
