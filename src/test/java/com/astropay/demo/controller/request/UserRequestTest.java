package com.astropay.demo.controller.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestTest {

  private static final String TEST_NAME = "John Doe";
  private static final String TEST_EMAIL = "john.doe@example.com";

  private UserRequest userRequest;

  @BeforeEach
  void setUp() {
    userRequest = new UserRequest();
  }

  @Test
  void noArgsConstructor_ShouldCreateInstance() {
    // Act & Assert
    assertNotNull(userRequest);
  }

  @Test
  void setName_ShouldSetNameCorrectly() {
    // Act
    userRequest.setName(TEST_NAME);

    // Assert
    assertEquals(TEST_NAME, userRequest.getName());
  }

  @Test
  void setEmail_ShouldSetEmailCorrectly() {
    // Act
    userRequest.setEmail(TEST_EMAIL);

    // Assert
    assertEquals(TEST_EMAIL, userRequest.getEmail());
  }

  @Test
  void getName_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(userRequest.getName());
  }

  @Test
  void getEmail_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(userRequest.getEmail());
  }

  @Test
  void settersAndGetters_ShouldWorkTogether() {
    // Act
    userRequest.setName(TEST_NAME);
    userRequest.setEmail(TEST_EMAIL);

    // Assert
    assertEquals(TEST_NAME, userRequest.getName());
    assertEquals(TEST_EMAIL, userRequest.getEmail());
  }
}