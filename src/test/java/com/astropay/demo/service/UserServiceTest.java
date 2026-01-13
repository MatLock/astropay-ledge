package com.astropay.demo.service;

import com.astropay.demo.exception.AlreadyExistsException;
import com.astropay.demo.exception.NotFoundException;
import com.astropay.demo.model.User;
import com.astropay.demo.repository.UserRepository;
import com.astropay.demo.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private static final Long TEST_USER_ID = 1L;
  private static final String TEST_NAME = "John Doe";
  private static final String TEST_EMAIL = "john.doe@example.com";
  private static final String TEST_EXISTING_EMAIL = "existing@example.com";

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User testUser;

  @BeforeEach
  void setUp() {
    testUser = User.builder()
        .id(TEST_USER_ID)
        .name(TEST_NAME)
        .email(TEST_EMAIL)
        .createdOn(OffsetDateTime.now())
        .build();
  }

  @Test
  void findUserById_ShouldReturnUser_WhenUserExists() {
    // Arrange
    when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));

    // Act
    User result = userService.findUserById(TEST_USER_ID);

    // Assert
    assertNotNull(result);
    assertEquals(testUser.getId(), result.getId());
    assertEquals(testUser.getName(), result.getName());
    assertEquals(testUser.getEmail(), result.getEmail());
    verify(userRepository, times(1)).findById(TEST_USER_ID);
  }

  @Test
  void findUserById_ShouldThrowNotFoundException_WhenUserNotFound() {
    // Arrange
    when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.empty());

    // Act & Assert
    NotFoundException exception = assertThrows(NotFoundException.class,
        () -> userService.findUserById(TEST_USER_ID));

    assertEquals(Constant.USER_NOT_FOUND, exception.getMessage());
    verify(userRepository, times(1)).findById(TEST_USER_ID);
  }

  @Test
  void createUser_ShouldReturnUser_WhenValidInput() {
    // Arrange
    when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(testUser);

    // Act
    User result = userService.createUser(TEST_NAME, TEST_EMAIL);

    // Assert
    assertNotNull(result);
    assertEquals(testUser.getId(), result.getId());
    assertEquals(testUser.getName(), result.getName());
    assertEquals(testUser.getEmail(), result.getEmail());
    verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void createUser_ShouldThrowAlreadyExistsException_WhenEmailExists() {
    // Arrange
    User existingUser = User.builder()
        .id(2L)
        .email(TEST_EXISTING_EMAIL)
        .build();
    when(userRepository.findByEmail(TEST_EXISTING_EMAIL)).thenReturn(Optional.of(existingUser));

    // Act & Assert
    AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
        () -> userService.createUser(TEST_NAME, TEST_EXISTING_EMAIL));

    assertEquals(Constant.USER_ALREADY_TAKEN, exception.getMessage());
    verify(userRepository, times(1)).findByEmail(TEST_EXISTING_EMAIL);
    verify(userRepository, never()).save(any());
  }

  @Test
  void createUser_ShouldCallRepositorySave_WithCorrectUser() {
    // Arrange
    when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(testUser);

    // Act
    userService.createUser(TEST_NAME, TEST_EMAIL);

    // Assert
    verify(userRepository).save(argThat(user ->
        user.getName().equals(TEST_NAME) &&
            user.getEmail().equals(TEST_EMAIL) &&
            user.getCreatedOn() != null
    ));
  }

  @Test
  void createUser_ShouldCheckEmailUniqueness_BeforeSaving() {
    // Arrange
    when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(testUser);

    // Act
    userService.createUser(TEST_NAME, TEST_EMAIL);

    // Assert
    verify(userRepository, times(1)).findByEmail(TEST_EMAIL);
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void findUserById_ShouldReturnUserFromRepository() {
    // Arrange
    when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));

    // Act
    User result = userService.findUserById(TEST_USER_ID);

    // Assert
    assertSame(testUser, result);
  }
}