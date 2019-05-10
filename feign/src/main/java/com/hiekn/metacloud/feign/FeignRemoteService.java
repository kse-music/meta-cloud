package com.hiekn.metacloud.feign;

import cn.hiboot.mcn.core.exception.ErrorMsg;
import cn.hiboot.mcn.core.model.result.RestResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "demo2",fallback = FeignRemoteService.FeignRemoteServiceError.class)
public interface FeignRemoteService {

    @RequestMapping(value = "/user/hi",method = RequestMethod.GET)
    RestResp hiFeign();

    @Component
    class FeignRemoteServiceError implements FeignRemoteService {

        @Override
        public RestResp hiFeign() {
            return new RestResp<>(ErrorMsg.REMOTE_SERVICE_ERROR,ErrorMsg.getErrorMsg(ErrorMsg.REMOTE_SERVICE_ERROR));
        }

    }
}
