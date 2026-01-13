package com.astropay.demo.service;

import com.astropay.demo.aspect.AuditLog;
import com.astropay.demo.exception.BadRequestException;
import com.astropay.demo.exception.NotFoundException;
import com.astropay.demo.model.Account;
import com.astropay.demo.model.Transaction;
import com.astropay.demo.repository.AccountRepository;
import com.astropay.demo.repository.TransactionRepository;
import com.astropay.demo.utils.Constant;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, ApplicationEventPublisher applicationEventPublisher) {
    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Transactional
  @AuditLog
  public void executeTransaction(Long fromAccount, Long toAccount, Long amount) {
    Account from = validateAccount(fromAccount);
    Account to = validateAccount(toAccount);
    validateNotSameAccount(fromAccount, toAccount);
    validateDebit(from, amount);
    from.debit(amount);
    to.credit(amount);
    Transaction transaction = Transaction.builder()
        .fromAccount(from)
        .toAccount(to)
        .amount(amount)
        .createdOn(OffsetDateTime.now())
        .build();
    transactionRepository.save(transaction);
    accountRepository.save(from);
    // an exception throw here should rollback previous db operation
    accountRepository.save(to);
    applicationEventPublisher.publishEvent(transaction);
  }

  private void validateNotSameAccount(Long fromAccount, Long toAccount) {
    if (fromAccount.equals(toAccount)) {
      throw new BadRequestException("Cannot create transaction with same account");
    }
  }

  private Account validateAccount(Long fromAccount) {
    Optional<Account> account = accountRepository.findById(fromAccount);
    return  account.orElseThrow(() -> new NotFoundException(Constant.ACCOUNT_NOT_FOUND));
  }

  private void validateDebit(Account from, Long amount) {
    if(from.hasEnoughMoney(amount)){
      return;
    }
    throw new IllegalArgumentException(Constant.SENDER_ACCOUNT_NOT_ENOUGH_MONEY);
  }
}
