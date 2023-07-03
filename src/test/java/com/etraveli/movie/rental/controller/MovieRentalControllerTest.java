package com.etraveli.movie.rental.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.etraveli.movie.rental.model.MovieRentData;
import com.etraveli.movie.rental.model.MovieRentDetail;
import com.etraveli.movie.rental.model.MovieRentalInfoSlip;
import com.etraveli.movie.rental.model.MovieRentalOrder;
import com.etraveli.movie.rental.service.MovieRentalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(MovieRentalController.class)
class MovieRentalControllerTest {
  @MockBean MovieRentalService movieRentalService;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  void processMovieRentalOrderTest() throws Exception {

    // Arrange
    MovieRentData rentData = new MovieRentData("F001", 3);
    MovieRentData rentData1 = new MovieRentData("F002", 1);
    String customerName = "C. U. Stomer";
    MovieRentalOrder movieRentalOrder = new MovieRentalOrder(List.of(rentData, rentData1));

    MovieRentDetail movieRentDetail = new MovieRentDetail("You've Got Mail", 3.5, 1);
    MovieRentDetail movieRentDetail1 = new MovieRentDetail("Matrix", 2, 1);
    MovieRentalInfoSlip movieRentalInfoSlip =
        new MovieRentalInfoSlip("C. U. Stomer", List.of(movieRentDetail, movieRentDetail1), 5.5, 2);

    when(movieRentalService.processMovieRentalOrder(customerName, movieRentalOrder))
        .thenReturn(movieRentalInfoSlip);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/rent")
                .header("customerName", customerName)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(movieRentalOrder)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalRentalAmount").exists())
        .andExpect(jsonPath("$.totalRentalAmount").value(5.5))
        .andExpect(jsonPath("$.totalRewardPoints").exists())
        .andExpect(jsonPath("$.totalRewardPoints").value(2));
  }
}
