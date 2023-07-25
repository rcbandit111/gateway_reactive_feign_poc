package com.service.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
//@JsonInclude(NON_NULL)
public class BasicTokenRequestDto  implements Serializable {

    private String api_key;

}
