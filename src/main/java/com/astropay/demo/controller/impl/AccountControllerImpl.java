package com.astropay.demo.controller.impl;

import com.astropay.demo.controller.AccountController;
import com.astropay.demo.controller.request.AccountRequest;
import com.astropay.demo.controller.response.AccountResponse;
import com.astropay.demo.controller.response.GlobalRestResponse;
import com.astropay.demo.model.Account;
import com.astropay.demo.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Log4j2
public class AccountControllerImpl implements AccountController {

  private AccountService accountService;

  public AccountControllerImpl(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  public ResponseEntity<GlobalRestResponse<AccountResponse>> create(AccountRequest request) {
    AccountResponse accountResponse = toAccountResponse(accountService.createAccount(request.getUserId(), request.getAmount()));
    return ResponseEntity.status(HttpStatus.CREATED).body(new GlobalRestResponse<>(Boolean.TRUE, accountResponse));
  }

  @Override
  public ResponseEntity<GlobalRestResponse<AccountResponse>> get(Long id) {
    AccountResponse accountResponse = toAccountResponse(accountService.findAccount(id));
    return ResponseEntity.ok(new GlobalRestResponse<>(Boolean.TRUE, accountResponse));
  }

  private AccountResponse toAccountResponse(Account account) {
    return AccountResponse.builder()
        .accountId(account.getId())
        .amount(account.getAmount())
        .createdOn(account.getCreatedOn())
        .build();
  }
}
