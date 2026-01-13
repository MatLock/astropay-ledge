package com.astropay.demo.handler;

import com.astropay.demo.controller.response.ErrorRestResponse;
import com.astropay.demo.exception.AlreadyExistsException;
import com.astropay.demo.exception.BadRequestException;
import com.astropay.demo.exception.NotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String INVALID_FIELDS_ERROR_MSG = "Invalid Fields Found";

  @ExceptionHandler(value = {AccessDeniedException.class})
  protected ResponseEntity<ErrorRestResponse> handleForbiddenError(RuntimeException ex) {
    ErrorRestResponse response = new ErrorRestResponse(null, ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN_403).body(response);
  }


  @ExceptionHandler(value = {NotFoundException.class})
  protected ResponseEntity<ErrorRestResponse> handleNotFoundException(RuntimeException ex) {
    ErrorRestResponse response = new ErrorRestResponse(null, ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND_404).body(response);
  }

  @ExceptionHandler(value = {AlreadyExistsException.class, BadRequestException.class, IllegalArgumentException.class})
  protected ResponseEntity<ErrorRestResponse> handleDomainExceptions(RuntimeException ex) {
    ErrorRestResponse response = new ErrorRestResponse(null, ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST_400).body(response);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST_400)
        .body(new ErrorRestResponse(errors,INVALID_FIELDS_ERROR_MSG ));
  }


}
