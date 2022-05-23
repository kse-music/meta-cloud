package com.hiekn.metacloud.gateway.conf;

import com.hiekn.metacloud.gateway.util.ExceptionWriter;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * GlobalErrorExceptionHandler
 *
 * #DefaultErrorWebExceptionHandler
 *
 * ReactiveResilience4JCircuitBreakerFactory若不配置fallback则走全局异常处理器
 *
 * @author DingHao
 * @since 2022/5/23 23:08
 */
@Order(-2)
@Component
public class GlobalErrorExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            response.setStatusCode(responseStatusException.getStatus());
            return ExceptionWriter.failed(responseStatusException.getReason(),response);
        }
        response.setStatusCode(HttpStatus.OK);
        return ExceptionWriter.failed(ex,response);
    }

}
