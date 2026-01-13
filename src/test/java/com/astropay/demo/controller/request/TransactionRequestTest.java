package com.astropay.demo.controller.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRequestTest {

  private static final Long TEST_FROM_ACCOUNT = 100L;
  private static final Long TEST_TO_ACCOUNT = 200L;
  private static final Long TEST_AMOUNT = 5000L;

  private TransactionRequest transactionRequest;

  @BeforeEach
  void setUp() {
    transactionRequest = new TransactionRequest();
  }

  @Test
  void noArgsConstructor_ShouldCreateInstance() {
    // Act & Assert
    assertNotNull(transactionRequest);
  }

  @Test
  void setFromAccount_ShouldSetFromAccountCorrectly() {
    // Act
    transactionRequest.setFromAccount(TEST_FROM_ACCOUNT);

    // Assert
    assertEquals(TEST_FROM_ACCOUNT, transactionRequest.getFromAccount());
  }

  @Test
  void setToAccount_ShouldSetToAccountCorrectly() {
    // Act
    transactionRequest.setToAccount(TEST_TO_ACCOUNT);

    // Assert
    assertEquals(TEST_TO_ACCOUNT, transactionRequest.getToAccount());
  }

  @Test
  void setAmount_ShouldSetAmountCorrectly() {
    // Act
    transactionRequest.setAmount(TEST_AMOUNT);

    // Assert
    assertEquals(TEST_AMOUNT, transactionRequest.getAmount());
  }

  @Test
  void getFromAccount_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(transactionRequest.getFromAccount());
  }

  @Test
  void getToAccount_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(transactionRequest.getToAccount());
  }

  @Test
  void getAmount_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(transactionRequest.getAmount());
  }

  @Test
  void settersAndGetters_ShouldWorkTogether() {
    // Act
    transactionRequest.setFromAccount(TEST_FROM_ACCOUNT);
    transactionRequest.setToAccount(TEST_TO_ACCOUNT);
    transactionRequest.setAmount(TEST_AMOUNT);

    // Assert
    assertEquals(TEST_FROM_ACCOUNT, transactionRequest.getFromAccount());
    assertEquals(TEST_TO_ACCOUNT, transactionRequest.getToAccount());
    assertEquals(TEST_AMOUNT, transactionRequest.getAmount());
  }
}