package com.gateway.filters.global;

import com.gateway.feign.ClientService;
import com.google.common.collect.Collections2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

  @Autowired
  ClientService clientService;

    private static final String SECRET_KEY = "your-secret-key";
    private static final long EXPIRATION_TIME_MS = 86400000; // 24 hours in milliseconds

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
      List<String> tokens = request.getHeaders().get("token");
        if (tokens!=null && tokens.size()!=0) {
            String apiKey = tokens.get(0);
            CompletableFuture<ResponseEntity<BasicTokenResponseDto>> completableFuture = CompletableFuture.supplyAsync
                    (()-> {
                        return clientService.getJwtWithRestTemplate(apiKey);
                    });

            ResponseEntity<BasicTokenResponseDto> resp = null;
            try {
                resp = completableFuture.get();
            } catch (Exception ex) {
                log.error("error get token", ex);
            }
            //now you can use the token
            System.out.println("access token = " + resp.getBody().getAccess_token());
        }
        return chain.filter(exchange);

  }


  @Override
  public int getOrder() {
    return 0;
  }
}
