package cn.hiboot.web.conf;

import feign.Feign;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.openfeign.Targeter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.Duration;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2021/7/4 12:02
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "feign.circuitbreaker",name = "enabled",havingValue = "true")
public class CircuitBreakerConfiguration {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(
                id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                        .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                        .build());
    }

    @ConditionalOnProperty(prefix = "feign.circuitbreaker",name = "globalfallback.enabled",havingValue = "true")
    private static class GlobalFallbackConfig{

        @Bean
        @Scope("prototype")
        public Feign.Builder circuitBreakerFeignBuilder() {
            return FeignCircuitBreaker.builder();
        }

        @Bean
        public Targeter circuitBreakerFeignTargeter(CircuitBreakerFactory circuitBreakerFactory,
                                                    @Value("${feign.circuitbreaker.group.enabled:false}") boolean circuitBreakerGroupEnabled) {
            return new FeignCircuitBreakerTargeter(circuitBreakerFactory, circuitBreakerGroupEnabled);
        }

    }

}
