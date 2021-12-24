package com.hiekn.metacloud.gateway;

import cn.hiboot.mcn.core.exception.ErrorMsg;
import cn.hiboot.mcn.core.exception.ExceptionKeys;
import cn.hiboot.mcn.core.model.result.RestResp;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2021/7/5 14:45
 */
@Configuration(proxyBeanMethods = false)
public class GateWayConfiguration {

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(
                id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                        .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                        .build());
    }

    @RestController
    private static class FallbackController{

        @RequestMapping("/fallback")
        public RestResp<?> fallback() {
            return new RestResp<>(ExceptionKeys.REMOTE_SERVICE_ERROR,ErrorMsg.getErrorMsg(ExceptionKeys.REMOTE_SERVICE_ERROR));
        }

    }


}
