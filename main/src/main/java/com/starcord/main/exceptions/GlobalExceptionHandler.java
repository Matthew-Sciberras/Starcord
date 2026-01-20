package com.starcord.main.exceptions;

import com.starcord.main.dtos.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
}
