package com.gateway.filters.global;

import com.gateway.feign.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

  @Autowired
  ClientService clientService;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    // handle non-blocking
    return clientService.getJwt().flatMap(token -> {
      if (ObjectUtils.isEmpty(token) || !StringUtils.hasText(token.getAccess_token())) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
      } else {
        return chain.filter(exchange);
      }
    });
  }


  @Override
  public int getOrder() {
    return 0;
  }
}
