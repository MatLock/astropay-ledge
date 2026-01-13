package com.astropay.demo.controller.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalRestResponseTest {

  private static final Boolean TEST_SUCCESS = true;
  private static final String TEST_DATA = "test data";

  private GlobalRestResponse<String> globalRestResponse;

  @BeforeEach
  void setUp() {
    globalRestResponse = new GlobalRestResponse<>();
  }

  @Test
  void noArgsConstructor_ShouldCreateInstance() {
    // Act & Assert
    assertNotNull(globalRestResponse);
  }

  @Test
  void allArgsConstructor_ShouldCreateInstanceWithAllFields() {
    // Act
    GlobalRestResponse<String> response = new GlobalRestResponse<>(TEST_SUCCESS, TEST_DATA);

    // Assert
    assertNotNull(response);
    assertEquals(TEST_SUCCESS, response.isSuccess());
    assertEquals(TEST_DATA, response.getData());
  }

  @Test
  void builder_ShouldCreateInstanceWithAllFields() {
    // Act
    GlobalRestResponse<String> response = GlobalRestResponse.<String>builder()
        .success(TEST_SUCCESS)
        .data(TEST_DATA)
        .build();

    // Assert
    assertNotNull(response);
    assertEquals(TEST_SUCCESS, response.isSuccess());
    assertEquals(TEST_DATA, response.getData());
  }

  @Test
  void setSuccess_ShouldSetSuccessCorrectly() {
    // Act
    globalRestResponse.setSuccess(TEST_SUCCESS);

    // Assert
    assertEquals(TEST_SUCCESS, globalRestResponse.isSuccess());
  }

  @Test
  void setData_ShouldSetDataCorrectly() {
    // Act
    globalRestResponse.setData(TEST_DATA);

    // Assert
    assertEquals(TEST_DATA, globalRestResponse.getData());
  }

  @Test
  void isSuccess_ShouldReturnFalse_WhenNotSet() {
    // Assert
    assertFalse(globalRestResponse.isSuccess());
  }

  @Test
  void getData_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(globalRestResponse.getData());
  }

  @Test
  void settersAndGetters_ShouldWorkTogether() {
    // Act
    globalRestResponse.setSuccess(TEST_SUCCESS);
    globalRestResponse.setData(TEST_DATA);

    // Assert
    assertEquals(TEST_SUCCESS, globalRestResponse.isSuccess());
    assertEquals(TEST_DATA, globalRestResponse.getData());
  }

  @Test
  void shouldWorkWithDifferentDataTypes_Integer() {
    // Arrange
    GlobalRestResponse<Integer> response = new GlobalRestResponse<>();
    Integer testInteger = 42;

    // Act
    response.setSuccess(true);
    response.setData(testInteger);

    // Assert
    assertTrue(response.isSuccess());
    assertEquals(testInteger, response.getData());
  }

  @Test
  void shouldWorkWithDifferentDataTypes_Object() {
    // Arrange
    GlobalRestResponse<UserResponse> response = new GlobalRestResponse<>();
    UserResponse userResponse = UserResponse.builder()
        .id(1L)
        .name("Test User")
        .email("test@example.com")
        .build();

    // Act
    response.setSuccess(true);
    response.setData(userResponse);

    // Assert
    assertTrue(response.isSuccess());
    assertEquals(userResponse, response.getData());
  }
}