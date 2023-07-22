package com.gateway;

import com.gateway.filters.global.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class})
@EnableWebFlux
//@EnableFeignClients
@ComponentScan({"com.gateway"})
@EnableEurekaClient
@EnableReactiveFeignClients
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public GlobalFilter authenticationFilter() {
    return new AuthenticationFilter();
  }

  @Autowired
  private ReactorLoadBalancerExchangeFilterFunction lbFunction;

  @Bean
  public WebClient loadBalancedWebClientBuilder() {
    return WebClient.builder().filter(lbFunction).build();
  }

}
