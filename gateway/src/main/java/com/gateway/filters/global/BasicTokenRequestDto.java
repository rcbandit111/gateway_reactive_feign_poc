package com.gateway.filters.global;

import lombok.Data;

import java.io.Serializable;

@Data
//@JsonInclude(NON_NULL)
public class BasicTokenRequestDto implements Serializable {

    private String api_key;

}
