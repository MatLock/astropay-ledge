package com.astropay.demo.controller.impl;

import com.astropay.demo.controller.UserController;
import com.astropay.demo.controller.request.UserRequest;
import com.astropay.demo.controller.response.GlobalRestResponse;
import com.astropay.demo.controller.response.UserResponse;
import com.astropay.demo.model.User;
import com.astropay.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Log4j2
public class UserControllerImpl implements UserController {

  private UserService userService;

  public UserControllerImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public ResponseEntity<GlobalRestResponse<UserResponse>> create(UserRequest request) {
    UserResponse user = toUser(userService.createUser(request.getName(), request.getEmail()));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new GlobalRestResponse<>(Boolean.TRUE, user));
  }

  @Override
  public ResponseEntity<GlobalRestResponse<UserResponse>> get(Long id) {
    UserResponse user = toUser(userService.findUserById(id));
    return ResponseEntity.ok(new GlobalRestResponse<>(Boolean.TRUE, user));
  }


  private UserResponse toUser(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .createdOn(user.getCreatedOn())
        .build();
  }


}
