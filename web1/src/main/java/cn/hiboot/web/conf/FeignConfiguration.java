package cn.hiboot.web.conf;

import feign.FeignException;
import feign.Logger;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
public class FeignConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Configuration(proxyBeanMethods = false)
    public static class FeignErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            return FeignException.errorStatus(methodKey, response);
        }

    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(
                id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                        .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                        .build());
    }

    //    @Bean
    //    Request.Options requestOptions(ConfigurableEnvironment env){
    //        int ribbonReadTimeout = env.getProperty("ribbon.ReadTimeout", int.class, 6000);
    //        int ribbonConnectionTimeout = env.getProperty("ribbon.ConnectTimeout", int.class, 3000);
    //        return new Request.Options(ribbonConnectionTimeout, ribbonReadTimeout);
    //    }
    //    @Bean
    //    @LoadBalanced
    //        RestTemplate restTemplate() {
    //        HttpComponentsClientHttpRequestFactory httpRequestFactory =  new HttpComponentsClientHttpRequestFactory();
    //        httpRequestFactory.setReadTimeout(60000);
    //        httpRequestFactory.setConnectTimeout(60000);
    //        return new RestTemplate(httpRequestFactory);
    //    }

}
