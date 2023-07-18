package com.gateway.filters.global;

import com.gateway.feign.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

public class AuthenticationFilter implements GlobalFilter, Ordered {

  @Autowired
  ClientService clientService;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    ServerHttpRequest request = exchange.getRequest();


    AtomicReference<BasicTokenResponseDto> plainJavaObject = new AtomicReference<>();
    Mono<BasicTokenResponseDto> tokenMono = clientService.getJwt("test");
    tokenMono.subscribe(transformedToken -> {
      plainJavaObject.set(transformedToken);
    });
    BasicTokenResponseDto token = plainJavaObject.get();


    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + token);



    ServerHttpRequest.Builder requestBuilder = request.mutate();

    return chain.filter(exchange.mutate().request(requestBuilder.build()).build());
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
