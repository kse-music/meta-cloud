package com.hiekn.metacloud.feign;

import feign.FeignException;
import feign.Logger;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Configuration
    public static class FeignErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            return FeignException.errorStatus(methodKey, response);
        }

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
