package com.etraveli.movie.rental.movie;

import com.etraveli.movie.rental.constant.MovieRentalConstants;
import com.etraveli.movie.rental.constant.MovieType;
import com.etraveli.movie.rental.model.MovieRentDetail;
import org.springframework.stereotype.Service;

@Service
public class NewMovieProcessor extends BaseMovieProcessor implements MovieProcessor {
  private static final String TYPE = MovieType.NEW.name();

  public NewMovieProcessor() {
    super(MovieRentalConstants.ZERO);
  }

  @Override
  public String getMovieType() {
    return TYPE;
  }

  @Override
  public MovieRentDetail calculateRentAndReward(int noOfRentDay) {
    double rentalAmount = (double) noOfRentDay * MovieRentalConstants.THREE;
    return MovieRentDetail.builder()
        .rentAmount(rentalAmount)
        .rewardPoints(getRewardPointsPoints(noOfRentDay))
        .build();
  }

  @Override
  public int getRewardPointsPoints(int noOfRentDay) {
    int rewardPoints = MovieRentalConstants.ONE;
    if (noOfRentDay > MovieRentalConstants.TWO) rewardPoints++;
    return rewardPoints;
  }
}
