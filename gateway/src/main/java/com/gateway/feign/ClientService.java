package com.gateway.feign;

import com.gateway.filters.global.BasicTokenResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

@Component
public class ClientService {

    private ClientFeign clientFeign;

    @Autowired
    public ClientService(ClientFeign clientFeign) {
        this.clientFeign = clientFeign;
    }

    public Mono<BasicTokenResponseDto> getJwt(String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, authHeader);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("key", "value");

        return clientFeign.getJwt(authHeader).map(customObject -> {
            BasicTokenResponseDto customObj = new BasicTokenResponseDto();
            customObj.setAccess_token(customObject.getAccess_token());
            return customObj;
        });
    }
}
