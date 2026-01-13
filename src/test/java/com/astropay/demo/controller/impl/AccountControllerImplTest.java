package com.astropay.demo.controller.impl;

import com.astropay.demo.controller.request.AccountRequest;
import com.astropay.demo.controller.response.AccountResponse;
import com.astropay.demo.controller.response.GlobalRestResponse;
import com.astropay.demo.model.Account;
import com.astropay.demo.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerImplTest {

  private static final Long TEST_USER_ID = 1L;
  private static final Long TEST_ACCOUNT_ID = 100L;
  private static final Long TEST_AMOUNT = 10000L;
  private static final OffsetDateTime TEST_CREATED_ON = OffsetDateTime.now();

  @Mock
  private AccountService accountService;

  @InjectMocks
  private AccountControllerImpl accountController;

  private Account testAccount;
  private AccountRequest testAccountRequest;

  @BeforeEach
  void setUp() {
    testAccount = Account.builder()
        .id(TEST_ACCOUNT_ID)
        .amount(TEST_AMOUNT)
        .createdOn(TEST_CREATED_ON)
        .build();

    testAccountRequest = new AccountRequest();
    testAccountRequest.setUserId(TEST_USER_ID);
    testAccountRequest.setAmount(TEST_AMOUNT);
  }

  @Test
  void create_ShouldReturnCreatedAccount_WhenValidRequest() {
    // Arrange
    when(accountService.createAccount(TEST_USER_ID, TEST_AMOUNT)).thenReturn(testAccount);

    // Act
    ResponseEntity<GlobalRestResponse<AccountResponse>> response = accountController.create(testAccountRequest);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().isSuccess());

    AccountResponse accountResponse = response.getBody().getData();
    assertNotNull(accountResponse);
    assertEquals(TEST_ACCOUNT_ID, accountResponse.getAccountId());
    assertEquals(TEST_AMOUNT, accountResponse.getAmount());
    assertEquals(TEST_CREATED_ON, accountResponse.getCreatedOn());

    verify(accountService, times(1)).createAccount(TEST_USER_ID, TEST_AMOUNT);
  }

  @Test
  void create_ShouldCallAccountService_WithCorrectParameters() {
    // Arrange
    when(accountService.createAccount(any(), any())).thenReturn(testAccount);

    // Act
    accountController.create(testAccountRequest);

    // Assert
    verify(accountService).createAccount(TEST_USER_ID, TEST_AMOUNT);
  }

  @Test
  void get_ShouldReturnAccount_WhenAccountExists() {
    // Arrange
    when(accountService.findAccount(TEST_ACCOUNT_ID)).thenReturn(testAccount);

    // Act
    ResponseEntity<GlobalRestResponse<AccountResponse>> response = accountController.get(TEST_ACCOUNT_ID);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().isSuccess());

    AccountResponse accountResponse = response.getBody().getData();
    assertNotNull(accountResponse);
    assertEquals(TEST_ACCOUNT_ID, accountResponse.getAccountId());
    assertEquals(TEST_AMOUNT, accountResponse.getAmount());
    assertEquals(TEST_CREATED_ON, accountResponse.getCreatedOn());

    verify(accountService, times(1)).findAccount(TEST_ACCOUNT_ID);
  }

  @Test
  void get_ShouldCallAccountService_WithCorrectId() {
    // Arrange
    when(accountService.findAccount(any())).thenReturn(testAccount);

    // Act
    accountController.get(TEST_ACCOUNT_ID);

    // Assert
    verify(accountService).findAccount(TEST_ACCOUNT_ID);
  }

  @Test
  void create_ShouldMapAccountToAccountResponse_Correctly() {
    // Arrange
    when(accountService.createAccount(TEST_USER_ID, TEST_AMOUNT)).thenReturn(testAccount);

    // Act
    ResponseEntity<GlobalRestResponse<AccountResponse>> response = accountController.create(testAccountRequest);

    // Assert
    AccountResponse accountResponse = response.getBody().getData();
    assertEquals(testAccount.getId(), accountResponse.getAccountId());
    assertEquals(testAccount.getAmount(), accountResponse.getAmount());
    assertEquals(testAccount.getCreatedOn(), accountResponse.getCreatedOn());
  }

  @Test
  void get_ShouldMapAccountToAccountResponse_Correctly() {
    // Arrange
    when(accountService.findAccount(TEST_ACCOUNT_ID)).thenReturn(testAccount);

    // Act
    ResponseEntity<GlobalRestResponse<AccountResponse>> response = accountController.get(TEST_ACCOUNT_ID);

    // Assert
    AccountResponse accountResponse = response.getBody().getData();
    assertEquals(testAccount.getId(), accountResponse.getAccountId());
    assertEquals(testAccount.getAmount(), accountResponse.getAmount());
    assertEquals(testAccount.getCreatedOn(), accountResponse.getCreatedOn());
  }
}