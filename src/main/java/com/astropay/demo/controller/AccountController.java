package com.astropay.demo.controller;

import com.astropay.demo.controller.request.AccountRequest;
import com.astropay.demo.controller.response.AccountResponse;
import com.astropay.demo.controller.response.ErrorRestResponse;
import com.astropay.demo.controller.response.GlobalRestResponse;
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
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Account Management", description = "Operations related to account management")
@RequestMapping("/accounts")
@Validated
public interface AccountController {

  @Operation(summary = "Create a new account", description = "Creates a new account for an existing user with an initial amount")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Account created successfully",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = GlobalRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": true, \"data\": {\"accountId\": 1, \"amount\": 10000, \"createdOn\": \"2026-01-12T10:30:00Z\"}}"))),
      @ApiResponse(responseCode = "400", description = "Invalid input - validation failed or null values",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorRestResponse.class),
              examples = {
                  @ExampleObject(name = "Validation errors", value = "{\"success\": false, \"data\": {\"userId\": \"userId must not be null\", \"amount\": \"account has to be created with an amount\"}, \"reason\": \"Invalid Fields Found\"}"),
                  @ExampleObject(name = "Null values", value = "{\"success\": false, \"data\": null, \"reason\": \"userId or amount is null\"}")
              })),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": false, \"data\": null, \"reason\": \"User not found\"}")))
  })
  @PostMapping("/create")
  ResponseEntity<GlobalRestResponse<AccountResponse>> create(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Account information including userId and initial amount",
          required = true,
          content = @Content(schema = @Schema(implementation = AccountRequest.class)))
      @RequestBody @Valid AccountRequest request);

  @Operation(summary = "Get account by ID", description = "Retrieves an account by its unique identifier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Account retrieved successfully",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = GlobalRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": true, \"data\": {\"accountId\": 1, \"amount\": 10000, \"createdOn\": \"2026-01-12T10:30:00Z\"}}"))),
      @ApiResponse(responseCode = "400", description = "Account ID is null",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": false, \"data\": null, \"reason\": \"Account is null\"}"))),
      @ApiResponse(responseCode = "404", description = "Account not found",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": false, \"data\": null, \"reason\": \"Account not found\"}")))
  })
  @GetMapping("/{id}")
  ResponseEntity<GlobalRestResponse<AccountResponse>> get(
      @Parameter(name = "id", description = "ID of the account to retrieve", required = true, in = ParameterIn.PATH, example = "1")
      @PathVariable("id") Long id);
}