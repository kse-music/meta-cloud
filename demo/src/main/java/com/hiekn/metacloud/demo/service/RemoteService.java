package com.hiekn.metacloud.demo.service;

import cn.hiboot.mcn.core.exception.ErrorMsg;
import cn.hiboot.mcn.core.model.result.RestResp;
import com.hiekn.metacloud.demo.bean.UserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "demo2",fallback = RemoteService.RemoteServiceHystrix.class)
public interface RemoteService {

    @GetMapping(value = "/user/hi")
    RestResp hi();

    @PostMapping(value = "/user/add",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    RestResp<UserBean> add(@RequestParam String bean);

    @Component
    class RemoteServiceHystrix implements RemoteService {

        @Override
        public RestResp hi() {
            return error();
        }

        @Override
        public RestResp<UserBean> add(String bean) {
            return error();
        }

        private RestResp error(){
            return new RestResp<>(ErrorMsg.REMOTE_SERVICE_ERROR,ErrorMsg.getErrorMsg(ErrorMsg.REMOTE_SERVICE_ERROR));
        }


    }
}
