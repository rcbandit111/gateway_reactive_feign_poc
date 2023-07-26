package com.gateway.feign;

import com.gateway.filters.global.BasicTokenRequestDto;
import com.gateway.filters.global.BasicTokenResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClientService {

    private ClientFeign clientFeign;

    @Autowired
    public ClientService(ClientFeign clientFeign) {
        this.clientFeign = clientFeign;
    }

//    public Mono<BasicTokenResponseDto> getJwt(String authHeader) {
//
//        return clientFeign.getJwt(authHeader);
//    }

    @Autowired
    public WebClient webClient;

    public Mono<BasicTokenResponseDto> getJwt(String key)
            throws RestClientException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ApiKey apiKey = new ApiKey();
        apiKey.setApi_key(key);

        return webClient.method(HttpMethod.POST)
                .uri("http://endpoint/greeting")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(Mono.just(apiKey), ApiKey.class)
                .retrieve()
                .onStatus(httpStatus -> {
                    return httpStatus.isError();
                },clientResponse -> {
                  return clientResponse.createException();
                })
                .bodyToMono(BasicTokenResponseDto.class)
                ;
    }

    public Mono<BasicTokenResponseDto> getJwt()
            throws RestClientException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ApiKey apiKey = new ApiKey();
        apiKey.setApi_key("172613672");

        return webClient.method(HttpMethod.POST)
                .uri("http://endpoint/greeting")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(Mono.just(apiKey), ApiKey.class)
                .retrieve()
                .bodyToMono(BasicTokenResponseDto.class);
    }
}
