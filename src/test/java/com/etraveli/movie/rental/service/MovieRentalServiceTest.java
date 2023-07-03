package com.etraveli.movie.rental.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.etraveli.movie.rental.model.*;
import com.etraveli.movie.rental.model.MovieRentData;
import com.etraveli.movie.rental.movie.MovieProcessorFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
@RequiredArgsConstructor
class MovieRentalServiceTest {
  @SpyBean MovieRentalService movieRentalService;

  private Map<String, MovieDetailsDTO> movieDetailsMap;
  @Mock MovieProcessorFacade movieProcessorFacade;

  @BeforeEach
  void setUp() {
    movieDetailsMap = new HashMap<>();
    movieRentalService = new MovieRentalService(movieDetailsMap, movieProcessorFacade);
  }

  @Test
  void processMovieRentalOrderWithValidMovieOrder() {
    // Arrange
    MovieDetailsDTO movieDetailsDTO = new MovieDetailsDTO("F001", "You've Got Mail", "REGULAR");

    MovieRentData rentData = new MovieRentData("F001", 3);
    MovieRentalOrder movieRentalOrder = new MovieRentalOrder(List.of(rentData));
    String customerName = "C. U. Stomer";
    MovieRentDetail movieRentDetail = new MovieRentDetail("You've Got Mail", 3.5, 1);
    MovieRentalInfoSlip movieRentalInfoSlip =
        new MovieRentalInfoSlip("C. U. Stomer", List.of(movieRentDetail), 5.5, 2);

    movieDetailsMap.put("F001", movieDetailsDTO);
    when(movieProcessorFacade.processMovieRentalOrder(movieDetailsDTO, rentData.noOfRentDay()))
        .thenReturn(movieRentDetail);

    // Act
    MovieRentalInfoSlip infoSlip =
        movieRentalService.processMovieRentalOrder(customerName, movieRentalOrder);

    // Assert
    assertEquals(movieRentalInfoSlip.customerName(), infoSlip.customerName());
    assertEquals(movieRentDetail.movieTitle(), infoSlip.movieRentDetails().get(0).movieTitle());
    assertEquals(movieRentDetail.rentAmount(), infoSlip.movieRentDetails().get(0).rentAmount());
    assertEquals(movieRentDetail.rewardPoints(), infoSlip.movieRentDetails().get(0).rewardPoints());
    verify(movieProcessorFacade, times(1))
        .processMovieRentalOrder(movieDetailsDTO, rentData.noOfRentDay());
  }
}
