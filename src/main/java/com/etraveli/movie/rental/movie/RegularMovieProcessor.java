package com.etraveli.movie.rental.movie;

import com.etraveli.movie.rental.constant.MovieRentalConstants;
import com.etraveli.movie.rental.constant.MovieType;
import com.etraveli.movie.rental.model.MovieRentDetail;
import org.springframework.stereotype.Service;

@Service
public class RegularMovieProcessor extends BaseMovieProcessor implements MovieProcessor {
  private static final String TYPE = MovieType.REGULAR.name();

  public RegularMovieProcessor() {
    super(MovieRentalConstants.TWO);
  }

  @Override
  public String getMovieType() {
    return TYPE;
  }

  @Override
  public MovieRentDetail calculateRentAndReward(int noOfRentDay) {
    double baseRentalAmount = getBaseMovieRentalAmount();
    if (noOfRentDay > MovieRentalConstants.TWO) {
      baseRentalAmount =
          ((noOfRentDay - MovieRentalConstants.TWO) * MovieRentalConstants.ONE_POINT_FIVE)
              + baseRentalAmount;
    }
    return MovieRentDetail.builder()
        .rentAmount(baseRentalAmount)
        .rewardPoints(getRewardPointsPoints(noOfRentDay))
        .build();
  }
}
