package com.example.movieexplorer.network

import MovieSearchResponse
import com.example.movieexplorer.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    fun getMovies(): Call<MovieResponse>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") movieName: String,
        @Query("api_key") apiKey: String
    ): Call<MovieSearchResponse>
}