package com.service.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Endpoint {

    @RequestMapping("/greeting")
    public @ResponseBody String greeting()
    {
        return "test response";
    }

//    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<BasicTokenRequestDto> apiKey(@RequestBody BasicTokenResponseDto token)
//    {
//        BasicTokenRequestDto response = new BasicTokenRequestDto();
//        response.setAccess_token("1234525235234234234234234234234234234234234==");
//
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//TODO  below is  another api , the pervious api is different with  your feginclient  api reference
    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicTokenRequestDto> apiKey(String token)
    {
        BasicTokenRequestDto response = new BasicTokenRequestDto();
        response.setAccess_token("1234525235234234234234234234234234234234234=="+token);

        System.out.println("token has been generated");
        return ResponseEntity.ok(response);
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
