package com.gfa.wc.models.dtos.ramin;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginRequest {

  @Value("${app.raminapi.email}")
  private String email;
  @Value("${app.raminapi.password}")
  private String password;

}
