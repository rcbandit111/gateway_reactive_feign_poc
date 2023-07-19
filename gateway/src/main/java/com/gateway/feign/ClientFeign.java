package com.gateway.feign;

import com.gateway.filters.global.BasicTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

//@FeignClient(name = "http://endpoint")
@ReactiveFeignClient(name = "endpoint")
public interface  ClientFeign {

    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
    Mono<BasicTokenResponseDto> getJwt(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader);
}
