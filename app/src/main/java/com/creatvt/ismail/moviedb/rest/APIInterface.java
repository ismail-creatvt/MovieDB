package com.creatvt.ismail.moviedb.rest;

import com.creatvt.ismail.moviedb.model.Movie;
import com.creatvt.ismail.moviedb.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("movie/top_rated")
    public Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apikey);

    @GET("movie/{id}")
    public Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search/movie")
    public Call<MoviesResponse> searchMovie(@Query("api_key") String api_key,@Query("query") String name);


}

