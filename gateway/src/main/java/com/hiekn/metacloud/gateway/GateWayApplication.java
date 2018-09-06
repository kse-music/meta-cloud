package com.hiekn.metacloud.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.core.env.Environment;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class GateWayApplication {


    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

}
