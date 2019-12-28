package com.hiekn.metacloud.gateway;

import cn.hiboot.mcn.core.exception.ErrorMsg;
import cn.hiboot.mcn.core.model.result.RestResp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableHystrix
@SpringBootApplication
@RestController
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = "http://localhost:8888";
        return builder.routes()
//                .route(p -> p
//                        .path("/jersey/user/hi")
//                        .filters(f -> f.addRequestHeader("Hello", "World").filter(new MyGatewayFilter()).stripPrefix(1))
//                        .uri(httpUri))
                .route(p -> p
                        .host("*.hystrix.com")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
    }

    @RequestMapping("/fallback")
    public RestResp fallback() {
        return new RestResp<>(ErrorMsg.REMOTE_SERVICE_ERROR,ErrorMsg.getErrorMsg(ErrorMsg.REMOTE_SERVICE_ERROR));
    }

}
