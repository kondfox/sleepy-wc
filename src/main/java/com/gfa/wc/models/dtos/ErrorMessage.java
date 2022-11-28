package com.gfa.wc.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessage {

  private String status = "error";
  private String message;

  public ErrorMessage(String message) {
    this.message = message;
  }

}
