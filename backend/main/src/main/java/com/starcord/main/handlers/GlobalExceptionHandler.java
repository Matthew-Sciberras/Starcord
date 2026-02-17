package com.starcord.main.handlers;

import com.starcord.main.dtos.General.ErrorResponse;
import com.starcord.main.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    // 400 Too Many Members
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

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(InternalServerException ex) {
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
        InternalServerException exception = new InternalServerException();
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
