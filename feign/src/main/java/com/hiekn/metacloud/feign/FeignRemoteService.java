package com.hiekn.metacloud.feign;

import cn.hiboot.mcn.core.exception.ErrorMsg;
import cn.hiboot.mcn.core.model.result.RestResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "web",fallback = FeignRemoteService.FeignRemoteServiceError.class)
public interface FeignRemoteService {

    @GetMapping("/user/list")
    RestResp hiFeign();

    @Component
    class FeignRemoteServiceError implements FeignRemoteService {

        @Override
        public RestResp hiFeign() {
            return new RestResp<>(ErrorMsg.REMOTE_SERVICE_ERROR,ErrorMsg.getErrorMsg(ErrorMsg.REMOTE_SERVICE_ERROR));
        }

    }
}
