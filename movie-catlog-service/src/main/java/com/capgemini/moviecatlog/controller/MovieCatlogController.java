package com.capgemini.moviecatlog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemini.moviecatlog.entity.Movie;
import com.capgemini.moviecatlog.entity.MovieCatlog;
import com.capgemini.moviecatlog.entity.Rating;
import com.capgemini.moviecatlog.entity.UserRating;

@RestController
@RequestMapping("/catlog")
public class MovieCatlogController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/{userId}")
	public ResponseEntity<List<MovieCatlog>> getMovieCatlog(@PathVariable String userId) {
		// get movie ratings for user
		UserRating userRating = restTemplate.getForEntity("http://MOVIE-RATING-SERVICE/ratings/" + userId, UserRating.class)
				.getBody();

		List<Rating> movieRatings = userRating.getUserRating();

		List<MovieCatlog> movieCatlog = new ArrayList<MovieCatlog>();

		// get movie info and creating movie catlog
		for (Rating rating : movieRatings) {

			Movie movie = restTemplate.getForEntity("http://MOVIE-INFO-SERVICE/movies/" + rating.getMovieId(), Movie.class)
					.getBody();

			movieCatlog.add(new MovieCatlog(movie.getMovieTitle(), movie.getMovieDescription(), rating.getRating()));
		}

		// return moviecatlog to user
		return new ResponseEntity<List<MovieCatlog>>(movieCatlog, HttpStatus.OK);

	}

}
