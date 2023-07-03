package com.etraveli.movie.rental.service;

import com.etraveli.movie.rental.constant.MovieRentalConstants;
import com.etraveli.movie.rental.model.*;
import com.etraveli.movie.rental.movie.MovieProcessorFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieRentalService {
  private final Map<String, MovieDetailsDTO> getMovieDetails;

  private final MovieProcessorFacade movieProcessorFacade;

  public MovieRentalInfoSlip processMovieRentalOrder(
      String customerName, MovieRentalOrder movieRentalOrder) {
    double totalRentAmount = 0;
    int totalRewardPoints = 0;
    List<MovieRentDetail> rentDetails = new ArrayList<>();

    List<MovieRentData> rentDataList = movieRentalOrder.movieRentData();
    for (MovieRentData rentData : rentDataList) {
      MovieDetailsDTO movieDetailsDTO = getMovieDetails.get(rentData.movieId());
      log.info("Inside processMovieRentalOrder Method :: movieDetailsDTO {}", movieDetailsDTO);
      if (movieDetailsDTO != null) {
        // If the number of rent days is less than one, set the default value to one.
        int noOfRentDay = Math.max(rentData.noOfRentDay(), MovieRentalConstants.ONE);
        MovieRentDetail movieRentDetail =
            movieProcessorFacade.processMovieRentalOrder(movieDetailsDTO, noOfRentDay);
        log.info("Inside processMovieRentalOrder Method :: movieRentDetail {}", movieRentDetail);
        rentDetails.add(
            MovieRentDetail.builder()
                .movieTitle(movieDetailsDTO.movieTittle())
                .rentAmount(movieRentDetail.rentAmount())
                .rewardPoints(movieRentDetail.rewardPoints())
                .build());
        totalRentAmount += movieRentDetail.rentAmount();
        totalRewardPoints += movieRentDetail.rewardPoints();
      }
    }
    return MovieRentalInfoSlip.builder()
        .customerName(customerName)
        .movieRentDetails(rentDetails)
        .totalRentalAmount(totalRentAmount)
        .totalRewardPoints(totalRewardPoints)
        .build();
  }
}
