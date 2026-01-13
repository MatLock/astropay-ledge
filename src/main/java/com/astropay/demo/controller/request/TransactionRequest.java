package com.astropay.demo.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionRequest {

  @NotNull(message = "from must not be null")
  private Long fromAccount;
  @NotNull(message = "to must not be null")
  private Long toAccount;
  @NotNull(message = "amount must not be null")
  private Long amount;


}
