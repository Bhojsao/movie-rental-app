package com.etraveli.movie.rental.movie;

import com.etraveli.movie.rental.constant.MovieRentalConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseMovieProcessor implements MovieProcessor {

  private final double baseMovieRentalAmount;

  protected BaseMovieProcessor(double baseMovieRentalAmount) {
    this.baseMovieRentalAmount = baseMovieRentalAmount;
  }

  public double getBaseMovieRentalAmount() {
    return baseMovieRentalAmount;
  }

  @Override
  public int getRewardPointsPoints(int noOfRentDay) {
    return MovieRentalConstants.ONE;
  }
}
