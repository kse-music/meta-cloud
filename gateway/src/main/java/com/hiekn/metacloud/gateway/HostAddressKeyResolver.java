package com.hiekn.metacloud.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 常见的限流算法:
 *      计数器算法：从开始请求+1，时间到清零
 *          弊端：如果我在单位时间1s内的前10ms，已经通过了100个请求，那后面的990ms，
 *          只能眼巴巴的把请求拒绝，我们把这种现象称为“突刺现象
 *      漏桶算法:以固定的时间来处理请求
 *          弊端：无法应对短时间的突发流量
 *      令牌桶算法：拿到令牌方可执行
 *
 * 根据什么来进行限流，ip，接口，或者用户来进行限流
 * @author: DingHao
 * @date: 2019/5/8 23:59
 */
@Component
public class HostAddressKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

}