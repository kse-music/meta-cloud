package com.hiekn.metacloud.gateway.filter;

import com.hiekn.metacloud.gateway.util.ExceptionWriter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *
 * MyGlobalFilter
 *
 * 全局过滤器，不需要配置路由，系统初始化作用到所有路由上相当于handler内部实现的过滤器与GatewayFilter类似
 *
 * 需要指定实现Ordered接口(不能使用注解@Order)才能与路由中定义的filter排序
 *
 * 执行的时候会FilteringWebHandler#handle会把全局过滤addAll路由中定义的过滤器 再排序
 *
 * @author DingHao
 * @since 2019/5/8 23:59
 */
@Component
public class MyGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (ObjectUtils.isEmpty(token)) {
            return ExceptionWriter.failed("无token",exchange.getResponse());
//            return  exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

}