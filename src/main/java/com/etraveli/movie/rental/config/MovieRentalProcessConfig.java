package com.etraveli.movie.rental.config;

import com.etraveli.movie.rental.exception.MovieRentalException;
import com.etraveli.movie.rental.model.MovieDetailsDTO;
import com.etraveli.movie.rental.movie.MovieProcessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@RequiredArgsConstructor
public class MovieRentalProcessConfig {
  private static final String MOVIE_DETAILS_PATH = "classpath:/*.json";
  private final ObjectMapper objectMapper;

  @Bean
  public Map<String, MovieProcessor> movieProcessors(ApplicationContext context) {
    Collection<MovieProcessor> movieProcessorBeans =
        context.getBeansOfType(MovieProcessor.class).values();
    Map<String, MovieProcessor> movieProcessorMap = new HashMap<>();
    for (MovieProcessor movieProcessor : movieProcessorBeans) {
      movieProcessorMap.put(movieProcessor.getMovieType(), movieProcessor);
    }
    return movieProcessorMap;
  }

  @Bean
  public Map<String, MovieDetailsDTO> getMovieDetails() {
    Map<String, MovieDetailsDTO> movieDtoMap = new HashMap<>();
    try {
      Resource[] resources =
          new PathMatchingResourcePatternResolver().getResources(MOVIE_DETAILS_PATH);

      List<MovieDetailsDTO> movieDetails = new ArrayList<>();
      for (Resource resource : resources) {
        List<MovieDetailsDTO> movieDetailsDTOs =
            objectMapper.readValue(
                resource.getInputStream(), new TypeReference<List<MovieDetailsDTO>>() {});
        movieDetails.addAll(movieDetailsDTOs);
      }
      movieDtoMap =
          movieDetails.stream()
              .collect(Collectors.toMap(MovieDetailsDTO::movieId, person -> person));
    } catch (IOException e) {
      throw new MovieRentalException(e.getMessage());
    }
    return movieDtoMap;
  }
}
