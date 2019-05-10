package com.hiekn.metacloud.demo.service;

import cn.hiboot.mcn.core.exception.ErrorMsg;
import cn.hiboot.mcn.core.model.result.RestResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "demo2",fallback = RemoteService.RemoteServiceHystrix.class)
public interface RemoteService {

    @RequestMapping(value = "/user/hi",method = RequestMethod.GET)
    RestResp hi();

    @Component
    class RemoteServiceHystrix implements RemoteService {

        @Override
        public RestResp hi() {
            return new RestResp<>(ErrorMsg.REMOTE_SERVICE_ERROR,ErrorMsg.getErrorMsg(ErrorMsg.REMOTE_SERVICE_ERROR));
        }

    }
}
