package com.hiekn.metacloud.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RemoteController {

    @Autowired
    private RemoteService remoteService;

    @RequestMapping("/api/swagger.json")
    public String hi(){
        return remoteService.hiService();
    }


}
