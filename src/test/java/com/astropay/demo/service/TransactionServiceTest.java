package com.astropay.demo.service;

import com.astropay.demo.exception.BadRequestException;
import com.astropay.demo.exception.NotFoundException;
import com.astropay.demo.model.Account;
import com.astropay.demo.model.Transaction;
import com.astropay.demo.repository.AccountRepository;
import com.astropay.demo.repository.TransactionRepository;
import com.astropay.demo.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

  private static final Long TEST_FROM_ACCOUNT_ID = 100L;
  private static final Long TEST_TO_ACCOUNT_ID = 200L;
  private static final Long TEST_TRANSACTION_AMOUNT = 5000L;
  private static final Long TEST_FROM_ACCOUNT_BALANCE = 10000L;
  private static final Long TEST_TO_ACCOUNT_BALANCE = 3000L;
  private static final Long TEST_INSUFFICIENT_BALANCE = 500L;

  @Mock
  private TransactionRepository transactionRepository;

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private ApplicationEventPublisher applicationEventPublisher;

  @InjectMocks
  private TransactionService transactionService;

  private Account fromAccount;
  private Account toAccount;

  @BeforeEach
  void setUp() {
    fromAccount = Account.builder()
        .id(TEST_FROM_ACCOUNT_ID)
        .amount(TEST_FROM_ACCOUNT_BALANCE)
        .build();

    toAccount = Account.builder()
        .id(TEST_TO_ACCOUNT_ID)
        .amount(TEST_TO_ACCOUNT_BALANCE)
        .build();
  }

  @Test
  void executeTransaction_ShouldCompleteSuccessfully_WhenValidInput() {
    // Arrange
    when(accountRepository.findById(TEST_FROM_ACCOUNT_ID)).thenReturn(Optional.of(fromAccount));
    when(accountRepository.findById(TEST_TO_ACCOUNT_ID)).thenReturn(Optional.of(toAccount));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
    when(accountRepository.save(any(Account.class))).thenReturn(fromAccount);

    // Act
    transactionService.executeTransaction(TEST_FROM_ACCOUNT_ID, TEST_TO_ACCOUNT_ID, TEST_TRANSACTION_AMOUNT);

    // Assert
    verify(accountRepository, times(1)).findById(TEST_FROM_ACCOUNT_ID);
    verify(accountRepository, times(1)).findById(TEST_TO_ACCOUNT_ID);
    verify(transactionRepository, times(1)).save(any(Transaction.class));
    verify(accountRepository, times(2)).save(any(Account.class));
    verify(applicationEventPublisher, times(1)).publishEvent(any(Transaction.class));
  }

  @Test
  void executeTransaction_ShouldDebitFromAccount_AndCreditToAccount() {
    // Arrange
    when(accountRepository.findById(TEST_FROM_ACCOUNT_ID)).thenReturn(Optional.of(fromAccount));
    when(accountRepository.findById(TEST_TO_ACCOUNT_ID)).thenReturn(Optional.of(toAccount));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
    when(accountRepository.save(any(Account.class))).thenReturn(fromAccount);

    Long expectedFromBalance = TEST_FROM_ACCOUNT_BALANCE - TEST_TRANSACTION_AMOUNT;
    Long expectedToBalance = TEST_TO_ACCOUNT_BALANCE + TEST_TRANSACTION_AMOUNT;

    // Act
    transactionService.executeTransaction(TEST_FROM_ACCOUNT_ID, TEST_TO_ACCOUNT_ID, TEST_TRANSACTION_AMOUNT);

    // Assert
    assertEquals(expectedFromBalance, fromAccount.getAmount());
    assertEquals(expectedToBalance, toAccount.getAmount());
  }

  @Test
  void executeTransaction_ShouldThrowNotFoundException_WhenFromAccountNotFound() {
    // Arrange
    when(accountRepository.findById(TEST_FROM_ACCOUNT_ID)).thenReturn(Optional.empty());

    // Act & Assert
    NotFoundException exception = assertThrows(NotFoundException.class,
        () -> transactionService.executeTransaction(TEST_FROM_ACCOUNT_ID, TEST_TO_ACCOUNT_ID, TEST_TRANSACTION_AMOUNT));

    assertEquals(Constant.ACCOUNT_NOT_FOUND, exception.getMessage());
    verify(accountRepository, times(1)).findById(TEST_FROM_ACCOUNT_ID);
    verify(transactionRepository, never()).save(any());
  }

  @Test
  void executeTransaction_ShouldThrowNotFoundException_WhenToAccountNotFound() {
    // Arrange
    when(accountRepository.findById(TEST_FROM_ACCOUNT_ID)).thenReturn(Optional.of(fromAccount));
    when(accountRepository.findById(TEST_TO_ACCOUNT_ID)).thenReturn(Optional.empty());

    // Act & Assert
    NotFoundException exception = assertThrows(NotFoundException.class,
        () -> transactionService.executeTransaction(TEST_FROM_ACCOUNT_ID, TEST_TO_ACCOUNT_ID, TEST_TRANSACTION_AMOUNT));

    assertEquals(Constant.ACCOUNT_NOT_FOUND, exception.getMessage());
    verify(accountRepository, times(1)).findById(TEST_FROM_ACCOUNT_ID);
    verify(accountRepository, times(1)).findById(TEST_TO_ACCOUNT_ID);
    verify(transactionRepository, never()).save(any());
  }

  @Test
  void executeTransaction_ShouldThrowBadRequestException_WhenSameAccount() {
    // Arrange
    when(accountRepository.findById(TEST_FROM_ACCOUNT_ID)).thenReturn(Optional.of(fromAccount));

    // Act & Assert
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> transactionService.executeTransaction(TEST_FROM_ACCOUNT_ID, TEST_FROM_ACCOUNT_ID, TEST_TRANSACTION_AMOUNT));

    assertEquals("Cannot create transaction with same account", exception.getMessage());
    verify(accountRepository, times(2)).findById(TEST_FROM_ACCOUNT_ID);
    verify(transactionRepository, never()).save(any());
  }

  @Test
  void executeTransaction_ShouldThrowIllegalArgumentException_WhenInsufficientBalance() {
    // Arrange
    Account poorAccount = Account.builder()
        .id(TEST_FROM_ACCOUNT_ID)
        .amount(TEST_INSUFFICIENT_BALANCE)
        .build();
    when(accountRepository.findById(TEST_FROM_ACCOUNT_ID)).thenReturn(Optional.of(poorAccount));
    when(accountRepository.findById(TEST_TO_ACCOUNT_ID)).thenReturn(Optional.of(toAccount));

    // Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> transactionService.executeTransaction(TEST_FROM_ACCOUNT_ID, TEST_TO_ACCOUNT_ID, TEST_TRANSACTION_AMOUNT));

    assertEquals(Constant.SENDER_ACCOUNT_NOT_ENOUGH_MONEY, exception.getMessage());
    verify(accountRepository, times(1)).findById(TEST_FROM_ACCOUNT_ID);
    verify(accountRepository, times(1)).findById(TEST_TO_ACCOUNT_ID);
    verify(transactionRepository, never()).save(any());
  }

  @Test
  void executeTransaction_ShouldSaveTransaction_WithCorrectDetails() {
    // Arrange
    when(accountRepository.findById(TEST_FROM_ACCOUNT_ID)).thenReturn(Optional.of(fromAccount));
    when(accountRepository.findById(TEST_TO_ACCOUNT_ID)).thenReturn(Optional.of(toAccount));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
    when(accountRepository.save(any(Account.class))).thenReturn(fromAccount);

    // Act
    transactionService.executeTransaction(TEST_FROM_ACCOUNT_ID, TEST_TO_ACCOUNT_ID, TEST_TRANSACTION_AMOUNT);

    // Assert
    verify(transactionRepository).save(argThat(transaction ->
        transaction.getFromAccount().equals(fromAccount) &&
            transaction.getToAccount().equals(toAccount) &&
            transaction.getAmount().equals(TEST_TRANSACTION_AMOUNT) &&
            transaction.getCreatedOn() != null
    ));
  }

  @Test
  void executeTransaction_ShouldPublishEvent_AfterSuccessfulTransaction() {
    // Arrange
    when(accountRepository.findById(TEST_FROM_ACCOUNT_ID)).thenReturn(Optional.of(fromAccount));
    when(accountRepository.findById(TEST_TO_ACCOUNT_ID)).thenReturn(Optional.of(toAccount));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
    when(accountRepository.save(any(Account.class))).thenReturn(fromAccount);

    // Act
    transactionService.executeTransaction(TEST_FROM_ACCOUNT_ID, TEST_TO_ACCOUNT_ID, TEST_TRANSACTION_AMOUNT);

    // Assert
    verify(applicationEventPublisher, times(1)).publishEvent(any(Transaction.class));
  }

  @Test
  void executeTransaction_ShouldSaveBothAccounts_AfterTransaction() {
    // Arrange
    when(accountRepository.findById(TEST_FROM_ACCOUNT_ID)).thenReturn(Optional.of(fromAccount));
    when(accountRepository.findById(TEST_TO_ACCOUNT_ID)).thenReturn(Optional.of(toAccount));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
    when(accountRepository.save(any(Account.class))).thenReturn(fromAccount, toAccount);

    // Act
    transactionService.executeTransaction(TEST_FROM_ACCOUNT_ID, TEST_TO_ACCOUNT_ID, TEST_TRANSACTION_AMOUNT);

    // Assert
    verify(accountRepository).save(fromAccount);
    verify(accountRepository).save(toAccount);
  }
}