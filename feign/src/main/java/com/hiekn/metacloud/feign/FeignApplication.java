package com.hiekn.metacloud.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class FeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignApplication.class, args);
	}
}
