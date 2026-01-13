package com.astropay.demo.exception;

public class NotFoundException  extends  RuntimeException{

  public NotFoundException(String reason){
    super(reason);
  }

}
