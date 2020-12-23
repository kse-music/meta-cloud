package cn.hiboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/12/29 0:01
 */
@EnableFeignClients(basePackages = "com.hiekn.metacloud.feign")
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
