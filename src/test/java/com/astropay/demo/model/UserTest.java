package com.astropay.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  private static final Long TEST_ID = 1L;
  private static final String TEST_NAME = "John Doe";
  private static final String TEST_EMAIL = "john.doe@example.com";
  private static final OffsetDateTime TEST_CREATED_ON = OffsetDateTime.now();

  private User user;

  @BeforeEach
  void setUp() {
    user = new User();
  }

  @Test
  void noArgsConstructor_ShouldCreateInstance() {
    // Act & Assert
    assertNotNull(user);
  }

  @Test
  void allArgsConstructor_ShouldCreateInstanceWithAllFields() {
    // Arrange
    List<Account> accounts = new ArrayList<>();

    // Act
    User testUser = new User(TEST_ID, TEST_NAME, TEST_EMAIL, TEST_CREATED_ON, accounts);

    // Assert
    assertNotNull(testUser);
    assertEquals(TEST_ID, testUser.getId());
    assertEquals(TEST_NAME, testUser.getName());
    assertEquals(TEST_EMAIL, testUser.getEmail());
    assertEquals(TEST_CREATED_ON, testUser.getCreatedOn());
    assertEquals(accounts, testUser.getAccounts());
  }

  @Test
  void builder_ShouldCreateInstanceWithAllFields() {
    // Arrange
    List<Account> accounts = new ArrayList<>();

    // Act
    User testUser = User.builder()
        .id(TEST_ID)
        .name(TEST_NAME)
        .email(TEST_EMAIL)
        .createdOn(TEST_CREATED_ON)
        .accounts(accounts)
        .build();

    // Assert
    assertNotNull(testUser);
    assertEquals(TEST_ID, testUser.getId());
    assertEquals(TEST_NAME, testUser.getName());
    assertEquals(TEST_EMAIL, testUser.getEmail());
    assertEquals(TEST_CREATED_ON, testUser.getCreatedOn());
    assertEquals(accounts, testUser.getAccounts());
  }

  @Test
  void setId_ShouldSetIdCorrectly() {
    // Act
    user.setId(TEST_ID);

    // Assert
    assertEquals(TEST_ID, user.getId());
  }

  @Test
  void setName_ShouldSetNameCorrectly() {
    // Act
    user.setName(TEST_NAME);

    // Assert
    assertEquals(TEST_NAME, user.getName());
  }

  @Test
  void setEmail_ShouldSetEmailCorrectly() {
    // Act
    user.setEmail(TEST_EMAIL);

    // Assert
    assertEquals(TEST_EMAIL, user.getEmail());
  }

  @Test
  void setCreatedOn_ShouldSetCreatedOnCorrectly() {
    // Act
    user.setCreatedOn(TEST_CREATED_ON);

    // Assert
    assertEquals(TEST_CREATED_ON, user.getCreatedOn());
  }

  @Test
  void setAccounts_ShouldSetAccountsCorrectly() {
    // Arrange
    List<Account> accounts = new ArrayList<>();
    Account account = Account.builder().id(100L).build();
    accounts.add(account);

    // Act
    user.setAccounts(accounts);

    // Assert
    assertEquals(accounts, user.getAccounts());
    assertEquals(1, user.getAccounts().size());
  }

  @Test
  void settersAndGetters_ShouldWorkTogether() {
    // Arrange
    List<Account> accounts = new ArrayList<>();

    // Act
    user.setId(TEST_ID);
    user.setName(TEST_NAME);
    user.setEmail(TEST_EMAIL);
    user.setCreatedOn(TEST_CREATED_ON);
    user.setAccounts(accounts);

    // Assert
    assertEquals(TEST_ID, user.getId());
    assertEquals(TEST_NAME, user.getName());
    assertEquals(TEST_EMAIL, user.getEmail());
    assertEquals(TEST_CREATED_ON, user.getCreatedOn());
    assertEquals(accounts, user.getAccounts());
  }
}