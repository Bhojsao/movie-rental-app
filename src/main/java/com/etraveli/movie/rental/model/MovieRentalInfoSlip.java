package com.etraveli.movie.rental.model;

import java.util.List;
import lombok.Builder;

@Builder
public record MovieRentalInfoSlip(
    String customerName,
    List<MovieRentDetail> movieRentDetails,
    double totalRentalAmount,
    int totalRewardPoints) {}
