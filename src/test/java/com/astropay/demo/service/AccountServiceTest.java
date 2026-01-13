package com.astropay.demo.service;

import com.astropay.demo.exception.BadRequestException;
import com.astropay.demo.exception.NotFoundException;
import com.astropay.demo.model.Account;
import com.astropay.demo.model.User;
import com.astropay.demo.repository.AccountRepository;
import com.astropay.demo.repository.UserRepository;
import com.astropay.demo.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  private static final Long TEST_USER_ID = 1L;
  private static final Long TEST_ACCOUNT_ID = 100L;
  private static final Long TEST_AMOUNT = 10000L;
  private static final Long TEST_NEGATIVE_AMOUNT = -1000L;
  private static final String TEST_USER_NAME = "Test User";

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private AccountService accountService;

  private User testUser;
  private Account testAccount;

  @BeforeEach
  void setUp() {
    testUser = User.builder()
        .id(TEST_USER_ID)
        .name(TEST_USER_NAME)
        .build();

    testAccount = Account.builder()
        .id(TEST_ACCOUNT_ID)
        .amount(TEST_AMOUNT)
        .user(testUser)
        .createdOn(OffsetDateTime.now())
        .build();
  }

  @Test
  void createAccount_ShouldReturnAccount_WhenValidInput() {
    // Arrange
    when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));
    when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

    // Act
    Account result = accountService.createAccount(TEST_USER_ID, TEST_AMOUNT);

    // Assert
    assertNotNull(result);
    assertEquals(testAccount.getId(), result.getId());
    assertEquals(testAccount.getAmount(), result.getAmount());
    verify(userRepository, times(1)).findById(TEST_USER_ID);
    verify(accountRepository, times(1)).save(any(Account.class));
  }

  @Test
  void createAccount_ShouldThrowBadRequestException_WhenUserIdIsNull() {
    // Act & Assert
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> accountService.createAccount(null, TEST_AMOUNT));

    assertEquals(Constant.USER_ID_OR_AMOUNT_IS_NULL, exception.getMessage());
    verify(userRepository, never()).findById(any());
    verify(accountRepository, never()).save(any());
  }

  @Test
  void createAccount_ShouldThrowBadRequestException_WhenAmountIsNull() {
    // Act & Assert
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> accountService.createAccount(TEST_USER_ID, null));

    assertEquals(Constant.USER_ID_OR_AMOUNT_IS_NULL, exception.getMessage());
    verify(userRepository, never()).findById(any());
    verify(accountRepository, never()).save(any());
  }

  @Test
  void createAccount_ShouldThrowBadRequestException_WhenAmountIsNegative() {
    // Act & Assert
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> accountService.createAccount(TEST_USER_ID, TEST_NEGATIVE_AMOUNT));

    assertEquals(Constant.AMOUNT_NON_NEGATIVE, exception.getMessage());
    verify(userRepository, never()).findById(any());
    verify(accountRepository, never()).save(any());
  }

  @Test
  void createAccount_ShouldThrowNotFoundException_WhenUserNotFound() {
    // Arrange
    when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.empty());

    // Act & Assert
    NotFoundException exception = assertThrows(NotFoundException.class,
        () -> accountService.createAccount(TEST_USER_ID, TEST_AMOUNT));

    assertEquals(Constant.USER_NOT_FOUND, exception.getMessage());
    verify(userRepository, times(1)).findById(TEST_USER_ID);
    verify(accountRepository, never()).save(any());
  }

  @Test
  void findAccount_ShouldReturnAccount_WhenAccountExists() {
    // Arrange
    when(accountRepository.findById(TEST_ACCOUNT_ID)).thenReturn(Optional.of(testAccount));

    // Act
    Account result = accountService.findAccount(TEST_ACCOUNT_ID);

    // Assert
    assertNotNull(result);
    assertEquals(testAccount.getId(), result.getId());
    assertEquals(testAccount.getAmount(), result.getAmount());
    verify(accountRepository, times(1)).findById(TEST_ACCOUNT_ID);
  }

  @Test
  void findAccount_ShouldThrowBadRequestException_WhenAccountIdIsNull() {
    // Act & Assert
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> accountService.findAccount(null));

    assertEquals(Constant.ACCOUNT_IS_NULL, exception.getMessage());
    verify(accountRepository, never()).findById(any());
  }

  @Test
  void findAccount_ShouldThrowNotFoundException_WhenAccountNotFound() {
    // Arrange
    when(accountRepository.findById(TEST_ACCOUNT_ID)).thenReturn(Optional.empty());

    // Act & Assert
    NotFoundException exception = assertThrows(NotFoundException.class,
        () -> accountService.findAccount(TEST_ACCOUNT_ID));

    assertEquals(Constant.ACCOUNT_NOT_FOUND, exception.getMessage());
    verify(accountRepository, times(1)).findById(TEST_ACCOUNT_ID);
  }

  @Test
  void createAccount_ShouldCallRepositorySave_WithCorrectAccount() {
    // Arrange
    when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));
    when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

    // Act
    accountService.createAccount(TEST_USER_ID, TEST_AMOUNT);

    // Assert
    verify(accountRepository).save(argThat(account ->
        account.getUser().equals(testUser) &&
            account.getAmount().equals(TEST_AMOUNT) &&
            account.getCreatedOn() != null
    ));
  }
}