package com.astropay.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

  private static final String EXPECTED_TITLE = "AstroPay Demo API";
  private static final String EXPECTED_DESCRIPTION = "REST API for AstroPay Demo Application - User, Account and Transaction Management";
  private static final String EXPECTED_VERSION = "1.0.0";
  private static final String EXPECTED_CONTACT_NAME = "AstroPay";
  private static final String EXPECTED_CONTACT_URL = "https://www.astropay.com";
  private static final String EXPECTED_CONTACT_EMAIL = "support@astropay.com";

  private SwaggerConfig swaggerConfig;

  @BeforeEach
  void setUp() {
    swaggerConfig = new SwaggerConfig();
  }

  @Test
  void customOpenAPI_ShouldReturnOpenAPIInstance() {
    // Act
    OpenAPI openAPI = swaggerConfig.customOpenAPI();

    // Assert
    assertNotNull(openAPI);
  }

  @Test
  void customOpenAPI_ShouldHaveInfo() {
    // Act
    OpenAPI openAPI = swaggerConfig.customOpenAPI();

    // Assert
    assertNotNull(openAPI.getInfo());
  }

  @Test
  void customOpenAPI_ShouldHaveCorrectTitle() {
    // Act
    OpenAPI openAPI = swaggerConfig.customOpenAPI();
    Info info = openAPI.getInfo();

    // Assert
    assertEquals(EXPECTED_TITLE, info.getTitle());
  }

  @Test
  void customOpenAPI_ShouldHaveCorrectDescription() {
    // Act
    OpenAPI openAPI = swaggerConfig.customOpenAPI();
    Info info = openAPI.getInfo();

    // Assert
    assertEquals(EXPECTED_DESCRIPTION, info.getDescription());
  }

  @Test
  void customOpenAPI_ShouldHaveCorrectVersion() {
    // Act
    OpenAPI openAPI = swaggerConfig.customOpenAPI();
    Info info = openAPI.getInfo();

    // Assert
    assertEquals(EXPECTED_VERSION, info.getVersion());
  }

  @Test
  void customOpenAPI_ShouldHaveContact() {
    // Act
    OpenAPI openAPI = swaggerConfig.customOpenAPI();
    Info info = openAPI.getInfo();

    // Assert
    assertNotNull(info.getContact());
  }

  @Test
  void customOpenAPI_ShouldHaveCorrectContactName() {
    // Act
    OpenAPI openAPI = swaggerConfig.customOpenAPI();
    Contact contact = openAPI.getInfo().getContact();

    // Assert
    assertEquals(EXPECTED_CONTACT_NAME, contact.getName());
  }

  @Test
  void customOpenAPI_ShouldHaveCorrectContactUrl() {
    // Act
    OpenAPI openAPI = swaggerConfig.customOpenAPI();
    Contact contact = openAPI.getInfo().getContact();

    // Assert
    assertEquals(EXPECTED_CONTACT_URL, contact.getUrl());
  }

  @Test
  void customOpenAPI_ShouldHaveCorrectContactEmail() {
    // Act
    OpenAPI openAPI = swaggerConfig.customOpenAPI();
    Contact contact = openAPI.getInfo().getContact();

    // Assert
    assertEquals(EXPECTED_CONTACT_EMAIL, contact.getEmail());
  }

  @Test
  void swaggerConfig_ShouldBeInstantiable() {
    // Act & Assert
    assertNotNull(swaggerConfig);
  }
}