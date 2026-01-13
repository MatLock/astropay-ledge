package com.astropay.demo.controller.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorRestResponseTest {

  private static final String TEST_REASON = "User not found";
  private static final String TEST_DATA = "error details";

  private ErrorRestResponse errorRestResponse;

  @BeforeEach
  void setUp() {
    errorRestResponse = new ErrorRestResponse();
  }

  @Test
  void noArgsConstructor_ShouldCreateInstance() {
    // Act & Assert
    assertNotNull(errorRestResponse);
  }

  @Test
  void allArgsConstructor_ShouldCreateInstanceWithAllFields() {
    // Act
    ErrorRestResponse response = new ErrorRestResponse(TEST_REASON);

    // Assert
    assertNotNull(response);
    assertEquals(TEST_REASON, response.getReason());
  }

  @Test
  void customConstructor_ShouldCreateInstanceWithDataAndReason() {
    // Act
    ErrorRestResponse response = new ErrorRestResponse(TEST_DATA, TEST_REASON);

    // Assert
    assertNotNull(response);
    assertEquals(TEST_DATA, response.getData());
    assertEquals(TEST_REASON, response.getReason());
    assertFalse(response.isSuccess());
  }

  @Test
  void customConstructor_ShouldSetSuccessToFalse() {
    // Act
    ErrorRestResponse response = new ErrorRestResponse(TEST_DATA, TEST_REASON);

    // Assert
    assertFalse(response.isSuccess());
  }

  @Test
  void setReason_ShouldSetReasonCorrectly() {
    // Act
    errorRestResponse.setReason(TEST_REASON);

    // Assert
    assertEquals(TEST_REASON, errorRestResponse.getReason());
  }

  @Test
  void getReason_ShouldReturnNull_WhenNotSet() {
    // Assert
    assertNull(errorRestResponse.getReason());
  }

  @Test
  void settersAndGetters_ShouldWorkTogether() {
    // Act
    errorRestResponse.setReason(TEST_REASON);
    errorRestResponse.setSuccess(false);
    errorRestResponse.setData(TEST_DATA);

    // Assert
    assertEquals(TEST_REASON, errorRestResponse.getReason());
    assertFalse(errorRestResponse.isSuccess());
    assertEquals(TEST_DATA, errorRestResponse.getData());
  }

  @Test
  void errorRestResponse_ShouldExtendGlobalRestResponse() {
    // Act
    ErrorRestResponse response = new ErrorRestResponse(TEST_DATA, TEST_REASON);

    // Assert
    assertTrue(response instanceof GlobalRestResponse);
  }

  @Test
  void customConstructor_ShouldSetDataFromParentClass() {
    // Arrange
    Object data = "custom error data";

    // Act
    ErrorRestResponse response = new ErrorRestResponse(data, TEST_REASON);

    // Assert
    assertEquals(data, response.getData());
    assertEquals(TEST_REASON, response.getReason());
  }

  @Test
  void errorRestResponse_ShouldInheritParentMethods() {
    // Act
    errorRestResponse.setSuccess(false);
    errorRestResponse.setData(TEST_DATA);
    errorRestResponse.setReason(TEST_REASON);

    // Assert
    assertFalse(errorRestResponse.isSuccess());
    assertEquals(TEST_DATA, errorRestResponse.getData());
    assertEquals(TEST_REASON, errorRestResponse.getReason());
  }
}