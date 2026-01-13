package com.astropay.demo.controller.impl;

import com.astropay.demo.controller.request.TransactionRequest;
import com.astropay.demo.controller.response.GlobalRestResponse;
import com.astropay.demo.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerImplTest {

  private static final Long TEST_FROM_ACCOUNT_ID = 100L;
  private static final Long TEST_TO_ACCOUNT_ID = 200L;
  private static final Long TEST_TRANSACTION_AMOUNT = 5000L;

  @Mock
  private TransactionService transactionService;

  @InjectMocks
  private TransactionControllerImpl transactionController;

  private TransactionRequest testTransactionRequest;

  @BeforeEach
  void setUp() {
    testTransactionRequest = new TransactionRequest();
    testTransactionRequest.setFromAccount(TEST_FROM_ACCOUNT_ID);
    testTransactionRequest.setToAccount(TEST_TO_ACCOUNT_ID);
    testTransactionRequest.setAmount(TEST_TRANSACTION_AMOUNT);
  }

  @Test
  void create_ShouldReturnCreatedStatus_WhenValidRequest() {
    // Arrange
    doNothing().when(transactionService).executeTransaction(
        TEST_FROM_ACCOUNT_ID,
        TEST_TO_ACCOUNT_ID,
        TEST_TRANSACTION_AMOUNT
    );

    // Act
    ResponseEntity<GlobalRestResponse<Void>> response = transactionController.create(testTransactionRequest);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    verify(transactionService, times(1)).executeTransaction(
        TEST_FROM_ACCOUNT_ID,
        TEST_TO_ACCOUNT_ID,
        TEST_TRANSACTION_AMOUNT
    );
  }

  @Test
  void create_ShouldCallTransactionService_WithCorrectParameters() {
    // Arrange
    doNothing().when(transactionService).executeTransaction(any(), any(), any());

    // Act
    transactionController.create(testTransactionRequest);

    // Assert
    verify(transactionService).executeTransaction(
        TEST_FROM_ACCOUNT_ID,
        TEST_TO_ACCOUNT_ID,
        TEST_TRANSACTION_AMOUNT
    );
  }

  @Test
  void create_ShouldInvokeServiceOnce_WhenCalled() {
    // Arrange
    doNothing().when(transactionService).executeTransaction(any(), any(), any());

    // Act
    transactionController.create(testTransactionRequest);

    // Assert
    verify(transactionService, times(1)).executeTransaction(any(), any(), any());
  }

  @Test
  void create_ShouldExtractCorrectValuesFromRequest() {
    // Arrange
    doNothing().when(transactionService).executeTransaction(any(), any(), any());

    // Act
    transactionController.create(testTransactionRequest);

    // Assert
    verify(transactionService).executeTransaction(
        eq(TEST_FROM_ACCOUNT_ID),
        eq(TEST_TO_ACCOUNT_ID),
        eq(TEST_TRANSACTION_AMOUNT)
    );
  }

  @Test
  void create_ShouldReturnEmptyBody_WhenTransactionSucceeds() {
    // Arrange
    doNothing().when(transactionService).executeTransaction(
        TEST_FROM_ACCOUNT_ID,
        TEST_TO_ACCOUNT_ID,
        TEST_TRANSACTION_AMOUNT
    );

    // Act
    ResponseEntity<GlobalRestResponse<Void>> response = transactionController.create(testTransactionRequest);

    // Assert
    assertNotNull(response);
    assertNull(response.getBody());
  }
}