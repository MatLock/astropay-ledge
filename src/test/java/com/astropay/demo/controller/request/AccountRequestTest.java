package com.astropay.demo.controller.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountRequestTest {

  private static final Long TEST_USER_ID = 1L;
  private static final Long TEST_AMOUNT = 10000L;

  private AccountRequest accountRequest;

  @BeforeEach
  void setUp() {
    accountRequest = new AccountRequest();
  }

  @Test
  void noArgsConstructor_ShouldCreateInstance() {
    // Act & Assert
    assertNotNull(accountRequest);
  }

  @Test
  void setUserId_ShouldSetUserIdCorrectly() {
    // Act
    accountRequest.setUserId(TEST_USER_ID);

    // Assert
    assertEquals(TEST_USER_ID, accountRequest.getUserId());
  }

  @Test
  void setAmount_ShouldSetAmountCorrectly() {
    // Act
    accountRequest.setAmount(TEST_AMOUNT);

    // Assert
    assertEquals(TEST_AMOUNT, accountRequest.getAmount());
  }

  @Test
  void getUserId_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(accountRequest.getUserId());
  }

  @Test
  void getAmount_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(accountRequest.getAmount());
  }

  @Test
  void settersAndGetters_ShouldWorkTogether() {
    // Act
    accountRequest.setUserId(TEST_USER_ID);
    accountRequest.setAmount(TEST_AMOUNT);

    // Assert
    assertEquals(TEST_USER_ID, accountRequest.getUserId());
    assertEquals(TEST_AMOUNT, accountRequest.getAmount());
  }
}