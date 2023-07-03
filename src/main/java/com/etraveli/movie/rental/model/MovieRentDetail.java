package com.etraveli.movie.rental.model;

import lombok.Builder;

@Builder
public record MovieRentDetail(String movieTitle, double rentAmount, int rewardPoints) {}
