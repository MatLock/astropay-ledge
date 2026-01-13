package com.astropay.demo.controller.impl;

import com.astropay.demo.controller.TransactionController;
import com.astropay.demo.controller.request.TransactionRequest;
import com.astropay.demo.controller.response.GlobalRestResponse;
import com.astropay.demo.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionControllerImpl implements TransactionController {

  private TransactionService service;

  public TransactionControllerImpl(TransactionService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<GlobalRestResponse<Void>> create(TransactionRequest request) {
    service.executeTransaction(request.getFromAccount(), request.getToAccount(), request.getAmount());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
