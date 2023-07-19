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



    return clientService.getJwt("test")
            .map(e-> e==null?"null":e.toString())
            .doOnNext(e-> log.info(" The token is {}",e))
//            .map(e-> request.mutate()
//            .headers(httpHeaders -> httpHeaders.add("token",e)).build())
            .flatMap(e-> {
              ServerHttpResponse response = exchange.getResponse();
              response.getHeaders().set("token",e);
              return chain.filter(exchange.mutate()
                      .response(response).build());
            })
            ;
  }


  @Override
  public int getOrder() {
    return 0;
  }
}
