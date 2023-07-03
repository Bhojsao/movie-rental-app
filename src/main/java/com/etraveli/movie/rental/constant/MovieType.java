package com.etraveli.movie.rental.constant;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MovieType {
  NEW,
  REGULAR,
  CHILDREN,
}
