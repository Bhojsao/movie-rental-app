package com.etraveli.movie.rental.movie;

import com.etraveli.movie.rental.model.MovieDetailsDTO;
import com.etraveli.movie.rental.model.MovieRentDetail;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieProcessorFacade {

  private final Map<String, MovieProcessor> movieProcessors;

  public MovieProcessorFacade(
      @Qualifier("movieProcessors") Map<String, MovieProcessor> movieProcessors) {
    if (Objects.requireNonNull(movieProcessors, "Movie processors must not be null").isEmpty()) {
      throw new IllegalArgumentException("Movie processors must not be empty");
    }
    this.movieProcessors = movieProcessors;
  }

  public MovieRentDetail processMovieRentalOrder(MovieDetailsDTO movieDetailsDTO, int noOfRentDay) {
    MovieProcessor movieProcessor = movieProcessors.get(movieDetailsDTO.movieType());
    MovieRentDetail movieRentDetail = null;
    if (movieProcessor == null) {
      log.info(
          "Skipping MovieRentalOrder with movieTittle={}  due to missing processor bean",
          movieDetailsDTO.movieTittle());
    } else {
      movieRentDetail = movieProcessor.calculateRentAndReward(noOfRentDay);
    }
    return movieRentDetail;
  }
}
