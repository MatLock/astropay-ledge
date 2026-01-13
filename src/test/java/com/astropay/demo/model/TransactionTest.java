package com.astropay.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

  private static final Long TEST_ID = 1L;
  private static final Long TEST_AMOUNT = 5000L;
  private static final OffsetDateTime TEST_CREATED_ON = OffsetDateTime.now();

  private Transaction transaction;
  private Account fromAccount;
  private Account toAccount;

  @BeforeEach
  void setUp() {
    transaction = new Transaction();
    fromAccount = Account.builder().id(100L).amount(10000L).build();
    toAccount = Account.builder().id(200L).amount(5000L).build();
  }

  @Test
  void noArgsConstructor_ShouldCreateInstance() {
    // Act & Assert
    assertNotNull(transaction);
  }

  @Test
  void allArgsConstructor_ShouldCreateInstanceWithAllFields() {
    // Act
    Transaction testTransaction = new Transaction(TEST_ID, fromAccount, toAccount, TEST_AMOUNT, TEST_CREATED_ON);

    // Assert
    assertNotNull(testTransaction);
    assertEquals(TEST_ID, testTransaction.getId());
    assertEquals(fromAccount, testTransaction.getFromAccount());
    assertEquals(toAccount, testTransaction.getToAccount());
    assertEquals(TEST_AMOUNT, testTransaction.getAmount());
    assertEquals(TEST_CREATED_ON, testTransaction.getCreatedOn());
  }

  @Test
  void builder_ShouldCreateInstanceWithAllFields() {
    // Act
    Transaction testTransaction = Transaction.builder()
        .id(TEST_ID)
        .fromAccount(fromAccount)
        .toAccount(toAccount)
        .amount(TEST_AMOUNT)
        .createdOn(TEST_CREATED_ON)
        .build();

    // Assert
    assertNotNull(testTransaction);
    assertEquals(TEST_ID, testTransaction.getId());
    assertEquals(fromAccount, testTransaction.getFromAccount());
    assertEquals(toAccount, testTransaction.getToAccount());
    assertEquals(TEST_AMOUNT, testTransaction.getAmount());
    assertEquals(TEST_CREATED_ON, testTransaction.getCreatedOn());
  }

  @Test
  void setId_ShouldSetIdCorrectly() {
    // Act
    transaction.setId(TEST_ID);

    // Assert
    assertEquals(TEST_ID, transaction.getId());
  }

  @Test
  void setFromAccount_ShouldSetFromAccountCorrectly() {
    // Act
    transaction.setFromAccount(fromAccount);

    // Assert
    assertEquals(fromAccount, transaction.getFromAccount());
  }

  @Test
  void setToAccount_ShouldSetToAccountCorrectly() {
    // Act
    transaction.setToAccount(toAccount);

    // Assert
    assertEquals(toAccount, transaction.getToAccount());
  }

  @Test
  void setAmount_ShouldSetAmountCorrectly() {
    // Act
    transaction.setAmount(TEST_AMOUNT);

    // Assert
    assertEquals(TEST_AMOUNT, transaction.getAmount());
  }

  @Test
  void setCreatedOn_ShouldSetCreatedOnCorrectly() {
    // Act
    transaction.setCreatedOn(TEST_CREATED_ON);

    // Assert
    assertEquals(TEST_CREATED_ON, transaction.getCreatedOn());
  }

  @Test
  void settersAndGetters_ShouldWorkTogether() {
    // Act
    transaction.setId(TEST_ID);
    transaction.setFromAccount(fromAccount);
    transaction.setToAccount(toAccount);
    transaction.setAmount(TEST_AMOUNT);
    transaction.setCreatedOn(TEST_CREATED_ON);

    // Assert
    assertEquals(TEST_ID, transaction.getId());
    assertEquals(fromAccount, transaction.getFromAccount());
    assertEquals(toAccount, transaction.getToAccount());
    assertEquals(TEST_AMOUNT, transaction.getAmount());
    assertEquals(TEST_CREATED_ON, transaction.getCreatedOn());
  }
}