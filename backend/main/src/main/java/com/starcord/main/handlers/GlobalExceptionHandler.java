package com.starcord.main.handlers;

import com.starcord.main.dtos.General.ErrorResponse;
import com.starcord.main.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    Index:
    BadRequestException: 400 Bad Request
    TooManyMembersException: 400 Bad Request
    InvalidCredentialsEception: 401 Invalid Credentials
    UnauthorizedException: 401 Unauthorized
    ForbiddenException: 403 Forbidden
    NotFoundException: 404 Not Found
    EmailInUseException: 409 Conflict
    UsernameInUseException: 409 Conflict
    RateLimitException: 429 Ratelimit
    InternalServerErrorException: 500 Internal Server Error
     */

    // 400 Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponse);
    }

    // 400 Bad Request
    @ExceptionHandler(TooManyMembersException.class)
    public ResponseEntity<ErrorResponse> handleTooManyMembersException(TooManyMembersException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponse);
    }

    // 401 Invalid Credentials
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        ErrorResponse errorResponseDTO = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponseDTO);
    }

    // 401 Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse errorResponseDTO = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponseDTO);
    }

    // 403 Forbidden
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
        ErrorResponse errorResponseDTO = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponseDTO);
    }

    // 404 Not Found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponseDTO = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponseDTO);
    }

    // 409 Email In use
    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<ErrorResponse> handleEmailInUseException(EmailInUseException ex) {
        ErrorResponse errorResponseDTO = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponseDTO);
    }

    // 409 Username In use
    @ExceptionHandler(UsernameInUseException.class)
    public ResponseEntity<ErrorResponse> handleUsernameInUseException(UsernameInUseException ex) {
        ErrorResponse errorResponseDTO = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponseDTO);
    }

    // 429 Rate limited
    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<ErrorResponse> handleRateLimitException(RateLimitException ex) {
        ErrorResponse errorResponseDTO = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponseDTO);
    }

    // 500 Internal Server Error
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(InternalServerErrorException ex) {
        ErrorResponse errorResponseDTO = new ErrorResponse(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        System.out.println("Unknown error occurred: " + ex.getMessage());
        InternalServerErrorException exception = new InternalServerErrorException();
        ErrorResponse errorResponseDTO = new ErrorResponse(
                exception.getErrorCode(),
                exception.getMessage(),
                exception.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(errorResponseDTO);
    }
}
