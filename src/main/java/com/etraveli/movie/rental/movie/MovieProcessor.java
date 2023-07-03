package com.etraveli.movie.rental.movie;

import com.etraveli.movie.rental.model.MovieRentDetail;

public interface MovieProcessor {
  /**
   * Method to get Movie Type
   *
   * @return the Movie Type
   */
  String getMovieType();

  /**
   * Calculates the rent amount and reward for a given number of rental days.
   *
   * @param noOfRentDay the number of rental days
   * @return the rental reward amount
   */
  MovieRentDetail calculateRentAndReward(int noOfRentDay);

  /**
   * Calculates the reward point for a given number of rental days.
   *
   * @param noOfRentDay the number of rental days
   * @return the rental points
   */
  int getRewardPointsPoints(int noOfRentDay);
}
