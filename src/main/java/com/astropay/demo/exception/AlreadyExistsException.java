package com.astropay.demo.exception;

public class AlreadyExistsException extends RuntimeException{

  public AlreadyExistsException(String message) {
    super(message);
  }
}
