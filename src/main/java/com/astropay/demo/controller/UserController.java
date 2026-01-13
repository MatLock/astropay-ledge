package com.astropay.demo.controller;

import com.astropay.demo.controller.request.UserRequest;
import com.astropay.demo.controller.response.ErrorRestResponse;
import com.astropay.demo.controller.response.GlobalRestResponse;
import com.astropay.demo.controller.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User Management", description = "Operations related to user management")
@RequestMapping("/users")
@Validated
public interface UserController {

  @Operation(summary = "Create a new user", description = "Creates a new user with the provided name and email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User created successfully",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = GlobalRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": true, \"data\": {\"id\": 1, \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"createdOn\": \"2026-01-12T10:30:00Z\"}}"))),
      @ApiResponse(responseCode = "400", description = "Invalid input - validation failed",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": false, \"data\": {\"name\": \"name must not be empty or null\", \"email\": \"email not valid\"}, \"reason\": \"Invalid Fields Found\"}")))
  })
  @PostMapping("/create")
  ResponseEntity<GlobalRestResponse<UserResponse>> create(
      @Parameter(description = "User information for creating a new user", required = true)
      @RequestBody @Valid UserRequest user);

  @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique identifier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User retrieved successfully",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = GlobalRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": true, \"data\": {\"id\": 1, \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"createdOn\": \"2026-01-12T10:30:00Z\"}}"))),
      @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": false, \"data\": null, \"reason\": \"Id Cannot be null\"}"))),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": false, \"data\": null, \"reason\": \"User not found\"}")))
  })
  @GetMapping("/{id}")
  ResponseEntity<GlobalRestResponse<UserResponse>> get(
      @Parameter(description = "ID of the user to retrieve", required = true,in = ParameterIn.PATH, example = "1")
      @PathVariable Long id);


}
