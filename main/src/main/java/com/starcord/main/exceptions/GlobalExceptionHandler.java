package com.starcord.main.exceptions;

import com.starcord.main.dtos.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(BadRequestException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getStatusCode().value(),
                Instant.now()
        );
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponseDTO);
    }

    // 401 Invalid Credentials
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
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
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
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
    public ResponseEntity<ErrorResponseDTO> handleForbiddenException(ForbiddenException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
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
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(NotFoundException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
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
    public ResponseEntity<ErrorResponseDTO> handleEmailInUseException(EmailInUseException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
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
    public ResponseEntity<ErrorResponseDTO> handleUsernameInUseException(UsernameInUseException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
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
    public ResponseEntity<ErrorResponseDTO> handleRateLimitException(RateLimitException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
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
    public ResponseEntity<ErrorResponseDTO> handleInternalServerException(InternalServerException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
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
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        System.out.println("Unknown error occurred: " + ex.getMessage());
        InternalServerException exception = new InternalServerException();
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
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
