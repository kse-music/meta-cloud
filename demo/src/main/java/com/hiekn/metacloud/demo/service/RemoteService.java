package com.hiekn.metacloud.demo.service;

import cn.hiboot.mcn.core.exception.ErrorMsg;
import cn.hiboot.mcn.core.model.result.RestResp;
import com.hiekn.metacloud.demo.bean.UserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "demo2",fallback = RemoteService.RemoteServiceHystrix.class)
public interface RemoteService {

    @RequestMapping(value = "/user/hi",method = RequestMethod.GET)
    RestResp hi();

    @RequestMapping(value = "/user/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
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
