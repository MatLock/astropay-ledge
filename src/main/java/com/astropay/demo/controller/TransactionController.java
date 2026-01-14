package com.astropay.demo.controller;

import com.astropay.demo.controller.request.TransactionRequest;
import com.astropay.demo.controller.response.ErrorRestResponse;
import com.astropay.demo.controller.response.GlobalRestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Transaction Management", description = "Operations related to transaction processing")
@RequestMapping("/transactions")
@Validated
public interface TransactionController {

  @Operation(summary = "Execute a transaction", description = "Transfers an amount from one account to another. The sender account must have sufficient balance.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Transaction executed successfully",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = GlobalRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": true, \"data\": null}"))),
      @ApiResponse(responseCode = "400", description = "Invalid input - validation failed, null values, or insufficient balance",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorRestResponse.class),
              examples = {
                  @ExampleObject(name = "Validation errors", value = "{\"success\": false, \"data\": {\"fromAccount\": \"from must not be null\", \"toAccount\": \"to must not be null\", \"amount\": \"amount must not be null\"}, \"reason\": \"Invalid Fields Found\"}"),
                  @ExampleObject(name = "Insufficient balance", value = "{\"success\": false, \"data\": null, \"reason\": \"Sender account has not enough money\"}")
              })),
      @ApiResponse(responseCode = "404", description = "Account not found",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorRestResponse.class),
              examples = @ExampleObject(value = "{\"success\": false, \"data\": null, \"reason\": \"Account not found\"}")))
  })
  @PostMapping("")
  ResponseEntity<GlobalRestResponse<Void>> create(
      @Parameter(description = "Transaction details including fromAccount, toAccount, and amount", required = true)
      @Valid @RequestBody TransactionRequest request);



}