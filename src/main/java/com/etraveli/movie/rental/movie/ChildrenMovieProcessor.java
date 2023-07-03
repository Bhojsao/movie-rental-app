package com.etraveli.movie.rental.movie;

import com.etraveli.movie.rental.constant.MovieRentalConstants;
import com.etraveli.movie.rental.constant.MovieType;
import com.etraveli.movie.rental.model.MovieRentDetail;
import org.springframework.stereotype.Service;

@Service
public class ChildrenMovieProcessor extends BaseMovieProcessor implements MovieProcessor {

  private static final String TYPE = MovieType.CHILDREN.name();

  public ChildrenMovieProcessor() {
    super(MovieRentalConstants.ONE_POINT_FIVE);
  }

  @Override
  public String getMovieType() {
    return TYPE;
  }

  @Override
  public MovieRentDetail calculateRentAndReward(int noOfRentDay) {
    double baseRentalAmount = getBaseMovieRentalAmount();
    if (noOfRentDay > MovieRentalConstants.THREE) {
      baseRentalAmount =
          ((noOfRentDay - MovieRentalConstants.THREE) * MovieRentalConstants.ONE_POINT_FIVE)
              + baseRentalAmount;
    }
    return MovieRentDetail.builder()
        .rentAmount(baseRentalAmount)
        .rewardPoints(getRewardPointsPoints(noOfRentDay))
        .build();
  }
}
