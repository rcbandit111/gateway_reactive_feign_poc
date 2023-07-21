package com.gateway.filters.global;

import com.gateway.feign.ClientFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

  @Autowired
  ClientFeign clientService;

  @Resource(name = "gateway-thread")
  Executor executor;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {



//    AtomicReference<BasicTokenResponseDto> plainJavaObject = new AtomicReference<>();
//    Mono<BasicTokenResponseDto> tokenMono = clientService.getJwt("test");
//    tokenMono.subscribe(transformedToken -> {
//      plainJavaObject.set(transformedToken);
//    });
//    BasicTokenResponseDto token = plainJavaObject.get();
//
//
//    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + token);

//      if  you wanna get the object ,  it is necessary that execute in a blocking-model threadpool
//       the  error  you encounter several times is  that  you do blocking  in a reavtive-thread model such as 'reactor-http-epoll-3'
      executor.execute(()-> {
          BasicTokenResponseDto test = clientService.getJwt("test").block();
          log.info("test : {}",test);

      });



      return clientService.getJwt("test")
            .map(e-> e==null?"null":e.toString())
            .doOnNext(e-> log.info(" The token is {}",e))
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
