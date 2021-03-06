package com.rjs.ratedmovieinfoservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class MovieRatingProvider {

    @Value("${movie.rating.service.url}")
    private String movieRatingsUrl;

    public Optional<Collection<MovieRating>> getMovieRatings(String userId){

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<MovieRating>> responseEntity =
                restTemplate.exchange(movieRatingsUrl + "/{userId}",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                }, userId);

        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            return Optional.empty();
        }
        else {
            return Optional.of(responseEntity.getBody());
        }
    }
}
