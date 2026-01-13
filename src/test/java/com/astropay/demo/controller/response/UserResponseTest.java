package com.astropay.demo.controller.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

  private static final Long TEST_ID = 1L;
  private static final String TEST_NAME = "John Doe";
  private static final String TEST_EMAIL = "john.doe@example.com";
  private static final OffsetDateTime TEST_CREATED_ON = OffsetDateTime.now();

  private UserResponse userResponse;

  @BeforeEach
  void setUp() {
    userResponse = new UserResponse();
  }

  @Test
  void noArgsConstructor_ShouldCreateInstance() {
    // Act & Assert
    assertNotNull(userResponse);
  }

  @Test
  void allArgsConstructor_ShouldCreateInstanceWithAllFields() {
    // Act
    UserResponse response = new UserResponse(TEST_ID, TEST_NAME, TEST_EMAIL, TEST_CREATED_ON);

    // Assert
    assertNotNull(response);
    assertEquals(TEST_ID, response.getId());
    assertEquals(TEST_NAME, response.getName());
    assertEquals(TEST_EMAIL, response.getEmail());
    assertEquals(TEST_CREATED_ON, response.getCreatedOn());
  }

  @Test
  void builder_ShouldCreateInstanceWithAllFields() {
    // Act
    UserResponse response = UserResponse.builder()
        .id(TEST_ID)
        .name(TEST_NAME)
        .email(TEST_EMAIL)
        .createdOn(TEST_CREATED_ON)
        .build();

    // Assert
    assertNotNull(response);
    assertEquals(TEST_ID, response.getId());
    assertEquals(TEST_NAME, response.getName());
    assertEquals(TEST_EMAIL, response.getEmail());
    assertEquals(TEST_CREATED_ON, response.getCreatedOn());
  }

  @Test
  void setId_ShouldSetIdCorrectly() {
    // Act
    userResponse.setId(TEST_ID);

    // Assert
    assertEquals(TEST_ID, userResponse.getId());
  }

  @Test
  void setName_ShouldSetNameCorrectly() {
    // Act
    userResponse.setName(TEST_NAME);

    // Assert
    assertEquals(TEST_NAME, userResponse.getName());
  }

  @Test
  void setEmail_ShouldSetEmailCorrectly() {
    // Act
    userResponse.setEmail(TEST_EMAIL);

    // Assert
    assertEquals(TEST_EMAIL, userResponse.getEmail());
  }

  @Test
  void setCreatedOn_ShouldSetCreatedOnCorrectly() {
    // Act
    userResponse.setCreatedOn(TEST_CREATED_ON);

    // Assert
    assertEquals(TEST_CREATED_ON, userResponse.getCreatedOn());
  }

  @Test
  void getId_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(userResponse.getId());
  }

  @Test
  void getName_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(userResponse.getName());
  }

  @Test
  void getEmail_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(userResponse.getEmail());
  }

  @Test
  void getCreatedOn_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(userResponse.getCreatedOn());
  }

  @Test
  void settersAndGetters_ShouldWorkTogether() {
    // Act
    userResponse.setId(TEST_ID);
    userResponse.setName(TEST_NAME);
    userResponse.setEmail(TEST_EMAIL);
    userResponse.setCreatedOn(TEST_CREATED_ON);

    // Assert
    assertEquals(TEST_ID, userResponse.getId());
    assertEquals(TEST_NAME, userResponse.getName());
    assertEquals(TEST_EMAIL, userResponse.getEmail());
    assertEquals(TEST_CREATED_ON, userResponse.getCreatedOn());
  }
}