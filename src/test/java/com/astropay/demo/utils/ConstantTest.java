package com.astropay.demo.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class ConstantTest {

  private static final String EXPECTED_ACCOUNT_IS_NULL = "Account is null";
  private static final String EXPECTED_ACCOUNT_NOT_FOUND = "Account not found";
  private static final String EXPECTED_USER_ID_OR_AMOUNT_IS_NULL = "userId or amount is null";
  private static final String EXPECTED_USER_NOT_FOUND = "User not found";
  private static final String EXPECTED_ID_CANNOT_BE_NULL = "Id Cannot be null";
  private static final String EXPECTED_SENDER_ACCOUNT_NOT_ENOUGH_MONEY = "Sender account has not enough money";
  private static final String EXPECTED_USER_ALREADY_TAKEN = "User is already taken";
  private static final String EXPECTED_AMOUNT_NON_NEGATIVE = "An account can't be created with a negative amount";

  @Test
  void constantClass_ShouldHavePrivateConstructor() throws NoSuchMethodException {
    // Arrange
    Constructor<Constant> constructor = Constant.class.getDeclaredConstructor();

    // Act & Assert
    assertFalse(constructor.canAccess(null));
  }

  @Test
  void constantClass_PrivateConstructor_ShouldBeAccessible() throws Exception {
    // Arrange
    Constructor<Constant> constructor = Constant.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    // Act & Assert
    assertDoesNotThrow(() -> constructor.newInstance());
  }

  @Test
  void accountIsNull_ShouldHaveCorrectValue() {
    // Assert
    assertEquals(EXPECTED_ACCOUNT_IS_NULL, Constant.ACCOUNT_IS_NULL);
  }

  @Test
  void accountNotFound_ShouldHaveCorrectValue() {
    // Assert
    assertEquals(EXPECTED_ACCOUNT_NOT_FOUND, Constant.ACCOUNT_NOT_FOUND);
  }

  @Test
  void userIdOrAmountIsNull_ShouldHaveCorrectValue() {
    // Assert
    assertEquals(EXPECTED_USER_ID_OR_AMOUNT_IS_NULL, Constant.USER_ID_OR_AMOUNT_IS_NULL);
  }

  @Test
  void userNotFound_ShouldHaveCorrectValue() {
    // Assert
    assertEquals(EXPECTED_USER_NOT_FOUND, Constant.USER_NOT_FOUND);
  }

  @Test
  void idCannotBeNull_ShouldHaveCorrectValue() {
    // Assert
    assertEquals(EXPECTED_ID_CANNOT_BE_NULL, Constant.ID_CANNOT_BE_NULL);
  }

  @Test
  void senderAccountNotEnoughMoney_ShouldHaveCorrectValue() {
    // Assert
    assertEquals(EXPECTED_SENDER_ACCOUNT_NOT_ENOUGH_MONEY, Constant.SENDER_ACCOUNT_NOT_ENOUGH_MONEY);
  }

  @Test
  void userAlreadyTaken_ShouldHaveCorrectValue() {
    // Assert
    assertEquals(EXPECTED_USER_ALREADY_TAKEN, Constant.USER_ALREADY_TAKEN);
  }

  @Test
  void amountNonNegative_ShouldHaveCorrectValue() {
    // Assert
    assertEquals(EXPECTED_AMOUNT_NON_NEGATIVE, Constant.AMOUNT_NON_NEGATIVE);
  }
}