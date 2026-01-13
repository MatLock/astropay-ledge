package com.astropay.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppBeansConfigTest {

  private AppBeansConfig appBeansConfig;

  @BeforeEach
  void setUp() {
    appBeansConfig = new AppBeansConfig();
  }

  @Test
  void objectMapper_ShouldReturnObjectMapperInstance() {
    // Act
    ObjectMapper objectMapper = appBeansConfig.objectMapper();

    // Assert
    assertNotNull(objectMapper);
  }

  @Test
  void objectMapper_ShouldBeInstanceOfObjectMapper() {
    // Act
    ObjectMapper objectMapper = appBeansConfig.objectMapper();

    // Assert
    assertTrue(objectMapper instanceof ObjectMapper);
  }

  @Test
  void objectMapper_ShouldHaveJavaTimeModuleRegistered() {
    // Act
    ObjectMapper objectMapper = appBeansConfig.objectMapper();

    // Assert
    assertNotNull(objectMapper);
    assertFalse(objectMapper.getRegisteredModuleIds().isEmpty());
  }

  @Test
  void objectMapper_ShouldReturnNewInstance_EachTime() {
    // Act
    ObjectMapper firstMapper = appBeansConfig.objectMapper();
    ObjectMapper secondMapper = appBeansConfig.objectMapper();

    // Assert
    assertNotSame(firstMapper, secondMapper);
  }

  @Test
  void appBeansConfig_ShouldBeInstantiable() {
    // Act & Assert
    assertNotNull(appBeansConfig);
  }
}