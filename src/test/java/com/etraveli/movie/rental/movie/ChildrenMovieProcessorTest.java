package com.etraveli.movie.rental.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.etraveli.movie.rental.model.MovieRentDetail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest(classes = ChildrenMovieProcessor.class)
class ChildrenMovieProcessorTest {

  @SpyBean ChildrenMovieProcessor movieProcessor;

  @Test
  void testGetMovieType() {
    assertEquals("CHILDREN", movieProcessor.getMovieType());
  }

  @Test
  void calculateRentAndRewardWithValidOrder() {
    // Arrange
    int noOfRentDays = 5;
    double expectedRentAmount = 4.5;
    int expectedRewardPoints = 1;

    // Act
    MovieRentDetail rentDetail = movieProcessor.calculateRentAndReward(noOfRentDays);

    // Assert
    assertEquals(expectedRentAmount, rentDetail.rentAmount());
    assertEquals(expectedRewardPoints, rentDetail.rewardPoints());
  }

  @Test
  void calculateRentAndReward_NoOfRentDays_LessThanThree() {
    // Arrange
    int noOfRentDays = 0;
    double expectedRentAmount = 1.5;
    int expectedRewardPoints = 1;

    // Act
    MovieRentDetail rentDetail = movieProcessor.calculateRentAndReward(noOfRentDays);

    // Assert
    assertEquals(expectedRentAmount, rentDetail.rentAmount());
    assertEquals(expectedRewardPoints, rentDetail.rewardPoints());
  }
}
