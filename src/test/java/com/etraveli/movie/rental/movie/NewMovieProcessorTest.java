package com.etraveli.movie.rental.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.etraveli.movie.rental.model.MovieRentDetail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest(classes = NewMovieProcessor.class)
class NewMovieProcessorTest {

  @SpyBean NewMovieProcessor movieProcessor;

  @Test
  void testGetMovieType() {
    assertEquals("NEW", movieProcessor.getMovieType());
  }

  @Test
  void calculateRentAndRewardWithValidOrder() {
    // Arrange
    int noOfRentDays = 3;
    double expectedRentAmount = 9;
    int expectedRewardPoints = 2;

    // Act
    MovieRentDetail rentDetail = movieProcessor.calculateRentAndReward(noOfRentDays);

    // Assert
    assertEquals(expectedRentAmount, rentDetail.rentAmount());
    assertEquals(expectedRewardPoints, rentDetail.rewardPoints());
  }
}
