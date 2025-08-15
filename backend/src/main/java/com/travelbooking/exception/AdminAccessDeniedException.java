package com.travelbooking.exception;

public class AdminAccessDeniedException extends RuntimeException {
  public AdminAccessDeniedException(String message) {
    super(message);
  }
}
