//package com.example.gateway.filter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//@Order(-1)
//@Component
//public class AFilter implements GlobalFilter {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        //得到请求体
//        ServerHttpRequest request = exchange.getRequest();
//        //得到响应参数
//        MultiValueMap<String, String> queryParams = request.getQueryParams();
//        // 2.获取authorization参数
//        String auth = queryParams.getFirst("tocken");
//        // 3.校验
//        if (auth == null) {
//            // 放行
//            return chain.filter(exchange);
//        }
//        // 4.拦截
//        // 4.1.禁止访问
//        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//        // 4.2.结束处理
//        return exchange.getResponse().setComplete();
//    }
//}
