package com.etraveli.movie.rental.movie;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.etraveli.movie.rental.model.MovieDetailsDTO;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MovieProcessorFacadeTest {
  @Test
  void doesntAcceptEmptyProcessors() {
    assertThrows(Exception.class, () -> new MovieProcessorFacade(null));
    assertThrows(Exception.class, () -> new MovieProcessorFacade(Collections.emptyMap()));
  }

  @Test
  void skipProcessMovieRentalOrder_ForUnsupportedMovieType() {
    var underTest = new MovieProcessorFacade(Map.of("REGULAR", mockProcessor("REGULAR")));
    int noOfRentDays = 3;
    MovieDetailsDTO movieDetailsDTO = new MovieDetailsDTO("F001", "You've Got Mail", "ADULT");
    Assertions.assertDoesNotThrow(
        () -> underTest.processMovieRentalOrder(movieDetailsDTO, noOfRentDays));
  }

  @Test
  void redirectsProcessIVIRequestToProperProcessor() {
    MovieProcessor regular = mockProcessor("REGULAR");
    MovieProcessor adult = mockProcessor("ADULT");

    var underTest =
        new MovieProcessorFacade(
            Map.of(
                "REGULAR", regular,
                "ADULT", adult));

    int noOfRentDays = 3;
    MovieDetailsDTO movieDetailsDTO = new MovieDetailsDTO("F001", "You've Got Mail", "REGULAR");
    Assertions.assertDoesNotThrow(
        () -> underTest.processMovieRentalOrder(movieDetailsDTO, noOfRentDays));

    verify(regular, times(1)).calculateRentAndReward(noOfRentDays);
    verifyNoInteractions(adult);
  }

  private MovieProcessor mockProcessor(String movieType) {
    MovieProcessor processor = Mockito.mock(MovieProcessor.class);
    when(processor.getMovieType()).thenReturn(movieType);
    return processor;
  }
}
