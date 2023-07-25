package com.gateway.filters.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
//@JsonInclude(NON_NULL)
public class BasicTokenResponseDto implements Serializable {

  private String access_token;

}
