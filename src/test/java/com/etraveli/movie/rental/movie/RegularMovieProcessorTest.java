package com.etraveli.movie.rental.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.etraveli.movie.rental.model.MovieRentDetail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest(classes = RegularMovieProcessor.class)
class RegularMovieProcessorTest {

  @SpyBean RegularMovieProcessor movieProcessor;

  @Test
  void testGetMovieType() {
    assertEquals("REGULAR", movieProcessor.getMovieType());
  }

  @Test
  void calculateRentAndRewardWithValidOrder() {
    // Arrange
    int noOfRentDays = 5;
    double expectedRentAmount = 6.5;
    int expectedRewardPoints = 1;

    // Act
    MovieRentDetail rentDetail = movieProcessor.calculateRentAndReward(noOfRentDays);

    // Assert
    assertEquals(expectedRentAmount, rentDetail.rentAmount());
    assertEquals(expectedRewardPoints, rentDetail.rewardPoints());
  }

  @Test
  void calculateRentAndReward_NoOfRentDays_LessThanTwo() {
    // Arrange
    int noOfRentDays = -1;
    double expectedRentAmount = 2;
    int expectedRewardPoints = 1;

    // Act
    MovieRentDetail rentDetail = movieProcessor.calculateRentAndReward(noOfRentDays);

    // Assert
    assertEquals(expectedRentAmount, rentDetail.rentAmount());
    assertEquals(expectedRewardPoints, rentDetail.rewardPoints());
  }
}
