package com.hiekn.metacloud.gateway.conf;

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
import org.springframework.web.server.ServerWebExchange;

import java.time.Duration;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR;

/**
 * GateWayConfiguration
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
    static class FallbackController{

        @RequestMapping("/fallback")
        public RestResp<?> fallback(ServerWebExchange exchange) {
            Throwable o = exchange.getAttribute(CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR);
            if(o != null){
                return RestResp.error(o.getMessage());
            }
            return RestResp.error(ExceptionKeys.REMOTE_SERVICE_ERROR);
        }

    }

}
