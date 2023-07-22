package com.gateway.feign;

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

//    public Mono<BasicTokenResponseDto> getJwt(String authHeader) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add(HttpHeaders.AUTHORIZATION, authHeader);
//
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("test", "test");
//
//        Mono<BasicTokenResponseDto> responseMono = webClient.method(HttpMethod.POST)
//                .uri("some usrl")
//                .headers(httpHeaders -> httpHeaders.addAll(headers))
//                .body(BodyInserters.fromValue(requestBody))
//                .retrieve()
//                .bodyToMono(BasicTokenResponseDto.class);
//
//        Mono<BasicTokenResponseDto> customObjMono = responseMono.map(customObject -> {
//            BasicTokenResponseDto customObj = new BasicTokenResponseDto();
//            customObj.setAccess_token(customObject.getAccess_token());
//            return customObj;
//        });
//
//        return customObjMono;
//    }

//    public Mono<BasicTokenResponseDto> getJwt(String authHeader) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add(HttpHeaders.AUTHORIZATION, authHeader);
//
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("test", "test");
//
//        Mono<BasicTokenResponseDto> responseMono = webClient.method(HttpMethod.POST)
//                .uri("some usrl")
//                .headers(httpHeaders -> httpHeaders.addAll(headers))
//                .body(BodyInserters.fromValue(requestBody))
//                .retrieve()
//                .bodyToMono(BasicTokenResponseDto.class);
//
//        Mono<BasicTokenResponseDto> customObjMono = responseMono.map(customObject -> {
//            BasicTokenResponseDto customObj = new BasicTokenResponseDto();
//            customObj.setAccess_token(customObject.getAccess_token());
//            return customObj;
//        });
//
//        return customObjMono;
//    }

    public Mono<BasicTokenResponseDto> getJwt()
            throws RestClientException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("api_key", "123456789");

        Mono<BasicTokenResponseDto> responseMono = webClient.method(HttpMethod.POST)
                .uri("http://endpoint/greeting")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(BasicTokenResponseDto.class);

        Mono<BasicTokenResponseDto> response = responseMono.map(obj -> {
            BasicTokenResponseDto tokenResponse = new BasicTokenResponseDto();
            tokenResponse.setAccess_token(obj.getAccess_token());
            return tokenResponse;
        });

        return response;
    }
}
