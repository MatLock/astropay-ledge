package com.astropay.demo.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountRequest {

  @NotNull(message = "userId must not be null")
  private Long userId;
  @NotNull(message= "account has to be created with an amount")
  private Long amount;


}
