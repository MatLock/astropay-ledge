package com.astropay.demo.service;

import com.astropay.demo.aspect.AuditLog;
import com.astropay.demo.exception.BadRequestException;
import com.astropay.demo.exception.NotFoundException;
import com.astropay.demo.model.Account;
import com.astropay.demo.model.User;
import com.astropay.demo.repository.AccountRepository;
import com.astropay.demo.repository.UserRepository;
import com.astropay.demo.utils.Constant;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class AccountService {

  private final AccountRepository accountRepository;
  private final UserRepository userRepository;

  public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
    this.accountRepository = accountRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  @AuditLog
  public Account createAccount(Long userId, Long amount) {
    User user = validate(userId, amount);
    Account newAccount = Account.builder()
        .user(user)
        .amount(amount)
        .createdOn(OffsetDateTime.now())
        .build();
    return accountRepository.save(newAccount);
  }

  @AuditLog
  public Account findAccount(Long accountId) {
    if( accountId == null ) {
      throw new BadRequestException(Constant.ACCOUNT_IS_NULL);
    }
    Optional<Account> account = accountRepository.findById(accountId);
    return account.orElseThrow(() -> new NotFoundException(Constant.ACCOUNT_NOT_FOUND));
  }

  private User validate(Long userId, Long amount) {
    if(userId == null || amount == null) {
      throw new BadRequestException(Constant.USER_ID_OR_AMOUNT_IS_NULL);
    }
    if(amount < 0) {
      throw new BadRequestException(Constant.AMOUNT_NON_NEGATIVE);
    }

    Optional<User> user = userRepository.findById(userId);
    return user.orElseThrow(() -> new NotFoundException(Constant.USER_NOT_FOUND));
  }


}
