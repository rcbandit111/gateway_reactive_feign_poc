package com.gateway.filters.global;

import com.gateway.feign.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

  @Autowired
  ClientService clientService;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    ServerHttpRequest request = exchange.getRequest();


//    AtomicReference<BasicTokenResponseDto> plainJavaObject = new AtomicReference<>();
//    Mono<BasicTokenResponseDto> tokenMono = clientService.getJwt("test");
//    tokenMono.subscribe(transformedToken -> {
//      plainJavaObject.set(transformedToken);
//    });
//    BasicTokenResponseDto token = plainJavaObject.get();
//
//
//    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + token);

    /* modified start - SuperDev {
    AtomicReference<BasicTokenResponseDto> plainJavaObject = new AtomicReference<>();
    Mono<BasicTokenResponseDto> tokenMono = clientService.getJwt();
    tokenMono.subscribe(transformedToken -> {
      plainJavaObject.set(transformedToken);
    });
    BasicTokenResponseDto token = plainJavaObject.get();

    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + token);

//TODO this is a web exchange filter  ,  null is  forbidden
//    return null;

    return  chain.filter(exchange.mutate().build());
    ====================================================================== */
    return clientService.getJwt()
            .flatMap(transformedToken -> {
                AtomicReference<BasicTokenResponseDto> plainJavaObject = new AtomicReference<>();
                plainJavaObject.set(transformedToken);
                BasicTokenResponseDto token = plainJavaObject.get();
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + token);
                // Continue the chain by calling the next filter
                return chain.filter(exchange);
            })
            .onErrorResume(throwable -> {
              // Handle errors if necessary
              log.error("Error occurred during authentication: {}", throwable.getMessage());
              // Continue the chain by calling the next filter
              return chain.filter(exchange);
            });
      // modify end - SuperDev @}
  }


  @Override
  public int getOrder() {
    return 0;
  }
}
