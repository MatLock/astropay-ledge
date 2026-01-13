package com.astropay.demo.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRestResponse extends GlobalRestResponse{

  private String reason;

  public ErrorRestResponse(Object data, String reason) {
    super(false, data);
    this.reason = reason;
  }
}
