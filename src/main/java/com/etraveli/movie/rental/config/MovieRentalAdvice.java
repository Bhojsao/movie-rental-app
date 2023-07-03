package com.etraveli.movie.rental.config;

import com.etraveli.movie.rental.exception.MovieRentalException;
import com.etraveli.movie.rental.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** Handles exceptions while process a REST call and produces a user/system friendly response. */
@ControllerAdvice
public class MovieRentalAdvice extends ResponseEntityExceptionHandler {
  private static final String TIMESTAMP = "timestamp";
  private static final String STATUS = "status";
  private static final String MESSAGE = "message";
  private static final String ERROR_OBJECT = "errors";
  private static final String BAD_REQ_MSG = "Some fields are not properly formed";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(TIMESTAMP, Instant.now());
    body.put(STATUS, HttpStatus.BAD_REQUEST.value());
    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();
    body.put(ERROR_OBJECT, errors);
    body.put(MESSAGE, BAD_REQ_MSG);
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MovieRentalException.class)
  public ResponseEntity<ErrorResponse> handleCongestionTaxException(
      RuntimeException e, HttpServletRequest request) {
    return buildErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(
      RuntimeException e, HttpServletRequest request) {
    return buildErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponseEntity(
      Exception exception, HttpStatus httpStatus) {
    ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, httpStatus);
  }
}
