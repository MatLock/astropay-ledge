package com.astropay.demo.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class
EmailValidatorTest {

  private static final String VALID_EMAIL_1 = "test@example.com";
  private static final String VALID_EMAIL_2 = "user.name@domain.co";
  private static final String VALID_EMAIL_3 = "test123@test-domain.com";
  private static final String VALID_EMAIL_WITH_NUMBERS = "user123@example123.com";
  private static final String VALID_EMAIL_WITH_PLUS = "user+tag@example.com";
  private static final String INVALID_EMAIL_NO_AT = "testexample.com";
  private static final String INVALID_EMAIL_NO_DOMAIN = "test@";
  private static final String INVALID_EMAIL_NO_TLD = "test@example";
  private static final String INVALID_EMAIL_SPACES = "test @example.com";
  private static final String INVALID_EMAIL_DOUBLE_AT = "test@@example.com";
  private static final String EMPTY_EMAIL = "";

  @Mock
  private ConstraintValidatorContext context;

  private EmailValidator emailValidator;

  @BeforeEach
  void setUp() {
    emailValidator = new EmailValidator();
  }

  @Test
  void isValid_ShouldReturnTrue_WhenValidEmail() {
    // Act
    boolean result = emailValidator.isValid(VALID_EMAIL_1, context);

    // Assert
    assertTrue(result);
  }

  @Test
  void isValid_ShouldReturnTrue_WhenValidEmailWithDot() {
    // Act
    boolean result = emailValidator.isValid(VALID_EMAIL_2, context);

    // Assert
    assertTrue(result);
  }

  @Test
  void isValid_ShouldReturnTrue_WhenValidEmailWithHyphen() {
    // Act
    boolean result = emailValidator.isValid(VALID_EMAIL_3, context);

    // Assert
    assertTrue(result);
  }

  @Test
  void isValid_ShouldReturnTrue_WhenValidEmailWithNumbers() {
    // Act
    boolean result = emailValidator.isValid(VALID_EMAIL_WITH_NUMBERS, context);

    // Assert
    assertTrue(result);
  }

  @Test
  void isValid_ShouldReturnTrue_WhenValidEmailWithPlus() {
    // Act
    boolean result = emailValidator.isValid(VALID_EMAIL_WITH_PLUS, context);

    // Assert
    assertTrue(result);
  }

  @Test
  void isValid_ShouldReturnFalse_WhenEmailIsNull() {
    // Act
    boolean result = emailValidator.isValid(null, context);

    // Assert
    assertFalse(result);
  }

  @Test
  void isValid_ShouldReturnFalse_WhenEmailIsEmpty() {
    // Act
    boolean result = emailValidator.isValid(EMPTY_EMAIL, context);

    // Assert
    assertFalse(result);
  }

  @Test
  void isValid_ShouldReturnFalse_WhenEmailMissingAtSign() {
    // Act
    boolean result = emailValidator.isValid(INVALID_EMAIL_NO_AT, context);

    // Assert
    assertFalse(result);
  }

  @Test
  void isValid_ShouldReturnFalse_WhenEmailMissingDomain() {
    // Act
    boolean result = emailValidator.isValid(INVALID_EMAIL_NO_DOMAIN, context);

    // Assert
    assertFalse(result);
  }

  @Test
  void isValid_ShouldReturnFalse_WhenEmailMissingTld() {
    // Act
    boolean result = emailValidator.isValid(INVALID_EMAIL_NO_TLD, context);

    // Assert
    assertFalse(result);
  }

  @Test
  void isValid_ShouldReturnFalse_WhenEmailContainsSpaces() {
    // Act
    boolean result = emailValidator.isValid(INVALID_EMAIL_SPACES, context);

    // Assert
    assertFalse(result);
  }

  @Test
  void isValid_ShouldReturnFalse_WhenEmailHasDoubleAtSign() {
    // Act
    boolean result = emailValidator.isValid(INVALID_EMAIL_DOUBLE_AT, context);

    // Assert
    assertFalse(result);
  }

  @Test
  void initialize_ShouldNotThrowException() {
    // Arrange
    ValidEmail validEmail = createValidEmailAnnotation();

    // Act & Assert
    assertDoesNotThrow(() -> emailValidator.initialize(validEmail));
  }

  private ValidEmail createValidEmailAnnotation() {
    return new ValidEmail() {
      @Override
      public String message() {
        return "Invalid email format";
      }

      @Override
      public Class<?>[] groups() {
        return new Class[0];
      }

      @Override
      public Class[] payload() {
        return new Class[0];
      }

      @Override
      public Class annotationType() {
        return ValidEmail.class;
      }
    };
  }
}