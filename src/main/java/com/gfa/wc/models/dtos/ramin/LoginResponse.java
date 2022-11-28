package com.gfa.wc.models.dtos.ramin;

import lombok.Data;

@Data
public class LoginResponse {

  private String status;
  private Token data;

}
