package com.astropay.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

  private static final Long TEST_ID = 100L;
  private static final Long TEST_AMOUNT = 10000L;
  private static final Long TEST_DEBIT_AMOUNT = 2000L;
  private static final Long TEST_CREDIT_AMOUNT = 5000L;
  private static final OffsetDateTime TEST_CREATED_ON = OffsetDateTime.now();
  private static final Long VERSION = 0L;

  private Account account;
  private User testUser;

  @BeforeEach
  void setUp() {
    account = new Account();
    testUser = User.builder().id(1L).name("Test User").build();
  }

  @Test
  void noArgsConstructor_ShouldCreateInstance() {
    // Act & Assert
    assertNotNull(account);
  }

  @Test
  void allArgsConstructor_ShouldCreateInstanceWithAllFields() {
    // Act
    Account testAccount = new Account(TEST_ID, TEST_AMOUNT,VERSION, testUser, TEST_CREATED_ON);

    // Assert
    assertNotNull(testAccount);
    assertEquals(TEST_ID, testAccount.getId());
    assertEquals(TEST_AMOUNT, testAccount.getAmount());
    assertEquals(testUser, testAccount.getUser());
    assertEquals(TEST_CREATED_ON, testAccount.getCreatedOn());
  }

  @Test
  void builder_ShouldCreateInstanceWithAllFields() {
    // Act
    Account testAccount = Account.builder()
        .id(TEST_ID)
        .amount(TEST_AMOUNT)
        .user(testUser)
        .createdOn(TEST_CREATED_ON)
        .build();

    // Assert
    assertNotNull(testAccount);
    assertEquals(TEST_ID, testAccount.getId());
    assertEquals(TEST_AMOUNT, testAccount.getAmount());
    assertEquals(testUser, testAccount.getUser());
    assertEquals(TEST_CREATED_ON, testAccount.getCreatedOn());
  }

  @Test
  void setId_ShouldSetIdCorrectly() {
    // Act
    account.setId(TEST_ID);

    // Assert
    assertEquals(TEST_ID, account.getId());
  }

  @Test
  void setAmount_ShouldSetAmountCorrectly() {
    // Act
    account.setAmount(TEST_AMOUNT);

    // Assert
    assertEquals(TEST_AMOUNT, account.getAmount());
  }

  @Test
  void setUser_ShouldSetUserCorrectly() {
    // Act
    account.setUser(testUser);

    // Assert
    assertEquals(testUser, account.getUser());
  }

  @Test
  void setCreatedOn_ShouldSetCreatedOnCorrectly() {
    // Act
    account.setCreatedOn(TEST_CREATED_ON);

    // Assert
    assertEquals(TEST_CREATED_ON, account.getCreatedOn());
  }

  @Test
  void hasEnoughMoney_ShouldReturnTrue_WhenSufficientBalance() {
    // Arrange
    account.setAmount(TEST_AMOUNT);

    // Act
    boolean result = account.hasEnoughMoney(TEST_DEBIT_AMOUNT);

    // Assert
    assertTrue(result);
  }

  @Test
  void hasEnoughMoney_ShouldReturnTrue_WhenExactBalance() {
    // Arrange
    account.setAmount(TEST_AMOUNT);

    // Act
    boolean result = account.hasEnoughMoney(TEST_AMOUNT);

    // Assert
    assertTrue(result);
  }

  @Test
  void hasEnoughMoney_ShouldReturnFalse_WhenInsufficientBalance() {
    // Arrange
    Long insufficientAmount = 500L;
    Long requestedAmount = 1000L;
    account.setAmount(insufficientAmount);

    // Act
    boolean result = account.hasEnoughMoney(requestedAmount);

    // Assert
    assertFalse(result);
  }

  @Test
  void debit_ShouldDecreaseAmount() {
    // Arrange
    Long expectedAmount = TEST_AMOUNT - TEST_DEBIT_AMOUNT;
    account.setAmount(TEST_AMOUNT);

    // Act
    account.debit(TEST_DEBIT_AMOUNT);

    // Assert
    assertEquals(expectedAmount, account.getAmount());
  }

  @Test
  void credit_ShouldIncreaseAmount() {
    // Arrange
    Long expectedAmount = TEST_AMOUNT + TEST_CREDIT_AMOUNT;
    account.setAmount(TEST_AMOUNT);

    // Act
    account.credit(TEST_CREDIT_AMOUNT);

    // Assert
    assertEquals(expectedAmount, account.getAmount());
  }

  @Test
  void debitAndCredit_ShouldWorkTogether() {
    // Arrange
    account.setAmount(TEST_AMOUNT);

    // Act
    account.debit(TEST_DEBIT_AMOUNT);
    account.credit(TEST_CREDIT_AMOUNT);

    // Assert
    Long expectedAmount = TEST_AMOUNT - TEST_DEBIT_AMOUNT + TEST_CREDIT_AMOUNT;
    assertEquals(expectedAmount, account.getAmount());
  }

  @Test
  void settersAndGetters_ShouldWorkTogether() {
    // Act
    account.setId(TEST_ID);
    account.setAmount(TEST_AMOUNT);
    account.setUser(testUser);
    account.setCreatedOn(TEST_CREATED_ON);

    // Assert
    assertEquals(TEST_ID, account.getId());
    assertEquals(TEST_AMOUNT, account.getAmount());
    assertEquals(testUser, account.getUser());
    assertEquals(TEST_CREATED_ON, account.getCreatedOn());
  }
}