package com.movieflix.movieApi.repository;

import com.movieflix.movieApi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
