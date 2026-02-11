package com.starcord.main.exceptions;

import org.springframework.http.HttpStatus;

public class TooManyMembersException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "You have already hit the maximum number of members.";
  private String errorCode = "BAD_REQUEST";
  private final HttpStatus statusCode = HttpStatus.BAD_REQUEST;

  public TooManyMembersException() { super (DEFAULT_MESSAGE); }

  public TooManyMembersException(String message) {
    super(message);
  }

  public TooManyMembersException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public HttpStatus getStatusCode() {
    return statusCode;
  }
}

