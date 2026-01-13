package com.astropay.demo.controller.impl;

import com.astropay.demo.controller.request.UserRequest;
import com.astropay.demo.controller.response.GlobalRestResponse;
import com.astropay.demo.controller.response.UserResponse;
import com.astropay.demo.model.User;
import com.astropay.demo.service.UserService;
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
class UserControllerImplTest {

  private static final Long TEST_USER_ID = 1L;
  private static final String TEST_USER_NAME = "John Doe";
  private static final String TEST_USER_EMAIL = "john.doe@example.com";
  private static final OffsetDateTime TEST_CREATED_ON = OffsetDateTime.now();

  @Mock
  private UserService userService;

  @InjectMocks
  private UserControllerImpl userController;

  private User testUser;
  private UserRequest testUserRequest;

  @BeforeEach
  void setUp() {
    testUser = User.builder()
        .id(TEST_USER_ID)
        .name(TEST_USER_NAME)
        .email(TEST_USER_EMAIL)
        .createdOn(TEST_CREATED_ON)
        .build();

    testUserRequest = new UserRequest();
    testUserRequest.setName(TEST_USER_NAME);
    testUserRequest.setEmail(TEST_USER_EMAIL);
  }

  @Test
  void create_ShouldReturnCreatedUser_WhenValidRequest() {
    // Arrange
    when(userService.createUser(TEST_USER_NAME, TEST_USER_EMAIL)).thenReturn(testUser);

    // Act
    ResponseEntity<GlobalRestResponse<UserResponse>> response = userController.create(testUserRequest);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().isSuccess());

    UserResponse userResponse = response.getBody().getData();
    assertNotNull(userResponse);
    assertEquals(TEST_USER_ID, userResponse.getId());
    assertEquals(TEST_USER_NAME, userResponse.getName());
    assertEquals(TEST_USER_EMAIL, userResponse.getEmail());
    assertEquals(TEST_CREATED_ON, userResponse.getCreatedOn());

    verify(userService, times(1)).createUser(TEST_USER_NAME, TEST_USER_EMAIL);
  }

  @Test
  void create_ShouldCallUserService_WithCorrectParameters() {
    // Arrange
    when(userService.createUser(any(), any())).thenReturn(testUser);

    // Act
    userController.create(testUserRequest);

    // Assert
    verify(userService).createUser(TEST_USER_NAME, TEST_USER_EMAIL);
  }

  @Test
  void get_ShouldReturnUser_WhenUserExists() {
    // Arrange
    when(userService.findUserById(TEST_USER_ID)).thenReturn(testUser);

    // Act
    ResponseEntity<GlobalRestResponse<UserResponse>> response = userController.get(TEST_USER_ID);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().isSuccess());

    UserResponse userResponse = response.getBody().getData();
    assertNotNull(userResponse);
    assertEquals(TEST_USER_ID, userResponse.getId());
    assertEquals(TEST_USER_NAME, userResponse.getName());
    assertEquals(TEST_USER_EMAIL, userResponse.getEmail());
    assertEquals(TEST_CREATED_ON, userResponse.getCreatedOn());

    verify(userService, times(1)).findUserById(TEST_USER_ID);
  }

  @Test
  void get_ShouldCallUserService_WithCorrectId() {
    // Arrange
    when(userService.findUserById(any())).thenReturn(testUser);

    // Act
    userController.get(TEST_USER_ID);

    // Assert
    verify(userService).findUserById(TEST_USER_ID);
  }

  @Test
  void create_ShouldMapUserToUserResponse_Correctly() {
    // Arrange
    when(userService.createUser(TEST_USER_NAME, TEST_USER_EMAIL)).thenReturn(testUser);

    // Act
    ResponseEntity<GlobalRestResponse<UserResponse>> response = userController.create(testUserRequest);

    // Assert
    UserResponse userResponse = response.getBody().getData();
    assertEquals(testUser.getId(), userResponse.getId());
    assertEquals(testUser.getName(), userResponse.getName());
    assertEquals(testUser.getEmail(), userResponse.getEmail());
    assertEquals(testUser.getCreatedOn(), userResponse.getCreatedOn());
  }

  @Test
  void get_ShouldMapUserToUserResponse_Correctly() {
    // Arrange
    when(userService.findUserById(TEST_USER_ID)).thenReturn(testUser);

    // Act
    ResponseEntity<GlobalRestResponse<UserResponse>> response = userController.get(TEST_USER_ID);

    // Assert
    UserResponse userResponse = response.getBody().getData();
    assertEquals(testUser.getId(), userResponse.getId());
    assertEquals(testUser.getName(), userResponse.getName());
    assertEquals(testUser.getEmail(), userResponse.getEmail());
    assertEquals(testUser.getCreatedOn(), userResponse.getCreatedOn());
  }
}