package com.astropay.demo.controller.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AccountResponseTest {

  private static final Long TEST_ACCOUNT_ID = 100L;
  private static final Long TEST_AMOUNT = 10000L;
  private static final OffsetDateTime TEST_CREATED_ON = OffsetDateTime.now();

  private AccountResponse accountResponse;

  @BeforeEach
  void setUp() {
    accountResponse = new AccountResponse();
  }

  @Test
  void noArgsConstructor_ShouldCreateInstance() {
    // Act & Assert
    assertNotNull(accountResponse);
  }

  @Test
  void allArgsConstructor_ShouldCreateInstanceWithAllFields() {
    // Act
    AccountResponse response = new AccountResponse(TEST_ACCOUNT_ID, TEST_AMOUNT, TEST_CREATED_ON);

    // Assert
    assertNotNull(response);
    assertEquals(TEST_ACCOUNT_ID, response.getAccountId());
    assertEquals(TEST_AMOUNT, response.getAmount());
    assertEquals(TEST_CREATED_ON, response.getCreatedOn());
  }

  @Test
  void builder_ShouldCreateInstanceWithAllFields() {
    // Act
    AccountResponse response = AccountResponse.builder()
        .accountId(TEST_ACCOUNT_ID)
        .amount(TEST_AMOUNT)
        .createdOn(TEST_CREATED_ON)
        .build();

    // Assert
    assertNotNull(response);
    assertEquals(TEST_ACCOUNT_ID, response.getAccountId());
    assertEquals(TEST_AMOUNT, response.getAmount());
    assertEquals(TEST_CREATED_ON, response.getCreatedOn());
  }

  @Test
  void setAccountId_ShouldSetAccountIdCorrectly() {
    // Act
    accountResponse.setAccountId(TEST_ACCOUNT_ID);

    // Assert
    assertEquals(TEST_ACCOUNT_ID, accountResponse.getAccountId());
  }

  @Test
  void setAmount_ShouldSetAmountCorrectly() {
    // Act
    accountResponse.setAmount(TEST_AMOUNT);

    // Assert
    assertEquals(TEST_AMOUNT, accountResponse.getAmount());
  }

  @Test
  void setCreatedOn_ShouldSetCreatedOnCorrectly() {
    // Act
    accountResponse.setCreatedOn(TEST_CREATED_ON);

    // Assert
    assertEquals(TEST_CREATED_ON, accountResponse.getCreatedOn());
  }

  @Test
  void getAccountId_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(accountResponse.getAccountId());
  }

  @Test
  void getAmount_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(accountResponse.getAmount());
  }

  @Test
  void getCreatedOn_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(accountResponse.getCreatedOn());
  }

  @Test
  void settersAndGetters_ShouldWorkTogether() {
    // Act
    accountResponse.setAccountId(TEST_ACCOUNT_ID);
    accountResponse.setAmount(TEST_AMOUNT);
    accountResponse.setCreatedOn(TEST_CREATED_ON);

    // Assert
    assertEquals(TEST_ACCOUNT_ID, accountResponse.getAccountId());
    assertEquals(TEST_AMOUNT, accountResponse.getAmount());
    assertEquals(TEST_CREATED_ON, accountResponse.getCreatedOn());
  }
}