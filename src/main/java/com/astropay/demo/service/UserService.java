package com.astropay.demo.service;

import com.astropay.demo.aspect.AuditLog;
import com.astropay.demo.exception.AlreadyExistsException;
import com.astropay.demo.exception.NotFoundException;
import com.astropay.demo.model.User;
import com.astropay.demo.repository.UserRepository;
import com.astropay.demo.utils.Constant;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @AuditLog
  public User findUserById(Long id){
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      return user.get();
    }
    throw new NotFoundException(Constant.USER_NOT_FOUND);
  }

  @Transactional
  @AuditLog
  public User createUser(String name, String email) {
    validateUniqueness(email);
    User newUser = User.builder()
        .name(name)
        .email(email)
        .createdOn(OffsetDateTime.now())
        .build();
    return userRepository.save(newUser);
  }

  private void validateUniqueness(String email) {
    if(userRepository.findByEmail(email).isPresent()) {
      throw new AlreadyExistsException(Constant.USER_ALREADY_TAKEN);
    }
  }

}
