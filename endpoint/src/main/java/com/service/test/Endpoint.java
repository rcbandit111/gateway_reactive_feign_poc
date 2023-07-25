package com.service.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Endpoint {

//    @RequestMapping("/greeting")
//    public @ResponseBody String greeting()
//    {
//        return "test response";
//    }

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
    public ResponseEntity<BasicTokenRequestDto> apiKeytest(String token)
    {
        BasicTokenRequestDto response = new BasicTokenRequestDto();
        response.setApi_key("1234525235234234234234234234234234234234234=="+token);


        System.out.println("token has been generated");
        return ResponseEntity.ok(response);
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }



//    TODO  unfortunately ,  the null  error  you encouter ,it is a  very  easy issue , be careful ,
//     make sure that   the api between  both api consumer side and provide  is coinside with eachother
//     previously , you define BasicTokenRequestDto on api side but the receive is  responseDto   ,
//    @PostMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE )
//    public ResponseEntity<BasicTokenResponseDto> apiKey(@RequestBody ApiKey api_key)
//    {
//        BasicTokenResponseDto response = new BasicTokenResponseDto();
//        response.setAccess_token("1234525235234234234234234234234234234234234=="+api_key.getApi_key());
//
//        System.out.println("token has been generated");
//        System.out.println("1234525235234234234234234234234234234234234=="+api_key.getApi_key());
////        return ResponseEntity.ok(null);
//        return null;
//    }

    @PostMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE )
    public BasicTokenResponseDto apiKey(@RequestBody ApiKey api_key)
    {
        BasicTokenResponseDto response = new BasicTokenResponseDto();
        response.setAccess_token("1234525235234234234234234234234234234234234=="+api_key.getApi_key());

        System.out.println("token has been generated");
        System.out.println("1234525235234234234234234234234234234234234=="+api_key.getApi_key());
//        return ResponseEntity.ok(null);
        return response;
    }

}
