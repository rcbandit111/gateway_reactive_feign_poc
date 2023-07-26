package com.service.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@Slf4j
public class Endpoint {


//TODO  below is  another api , the pervious api is different with  your feginclient  api reference
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicTokenRequestDto> apiKeytest()
    {
        String accessToken = generateToken();
        BasicTokenRequestDto response = new BasicTokenRequestDto();
        response.setApi_key(accessToken);
        System.out.println("token has been generated");
        return ResponseEntity.ok(response);
    }



//    TODO  unfortunately ,  the null  error  you encouter ,it is a  very  easy issue , be careful ,
//     make sure that   the api between  both api consumer side and provide  is coinside with eachother
//     previously , you define BasicTokenRequestDto on api side but the receive is  responseDto   ,


    @PostMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<BasicTokenResponseDto> apiKey(@RequestBody ApiKey api_key)
    {
        BasicTokenResponseDto response = new BasicTokenResponseDto();
        String apiKey = api_key.getApi_key();

        if(isJwtValid(apiKey)){

            response.setAccess_token(api_key.getApi_key());

            log.info(apiKey);
            return ResponseEntity.ok(response);

        }
        response.setAccess_token("error api key");
        return ResponseEntity.ok(response);
    }


    private static final String SECRET_KEY = "secretKeyAnddatetimeaccountorelseqqqqqew1qqqq";
    private static final long EXPIRATION_TIME_MS = 86400000; // 24 hours in milliseconds

    public static String generateToken() {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME_MS);
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY) // Set the signature algorithm and secret key
                .compact()
        ;

    }

    public static boolean isJwtValid(String jwtToken) {
        try {
            Jws<Claims> claimsJws =
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)

                    // Set the secret key
                    .build()
                    .parseClaimsJws(jwtToken); // Parse the JWT token
            // The token is valid if it hasn't expired

            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            // Exception indicates that the token is invalid or expired
            log.error(e.getMessage());
            return false;
        }
    }

    public static String extractSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


}
