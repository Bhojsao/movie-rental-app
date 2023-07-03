package com.etraveli.movie.rental.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.etraveli.movie.rental.model.MovieRentalInfoSlip;
import com.etraveli.movie.rental.model.MovieRentalOrder;
import com.etraveli.movie.rental.service.MovieRentalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@Slf4j
public class MovieRentalController {

  private final MovieRentalService movieRentalService;

  @PostMapping(
      value = "/rent",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  ResponseEntity<MovieRentalInfoSlip> processMovieRentalOrder(
      @NotEmpty(message = "Customer Name is mandatory") @RequestHeader(name = "customerName")
          String customerName,
      @Valid @RequestBody MovieRentalOrder movieRentalOrder) {
    log.info("Inside processMovieRentalOrder Method :: movieRentalOrder {}", movieRentalOrder);
    return ResponseEntity.ok(
        movieRentalService.processMovieRentalOrder(customerName, movieRentalOrder));
  }
}
