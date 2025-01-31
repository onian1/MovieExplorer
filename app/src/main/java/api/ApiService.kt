package com.example.movieexplorer.network

import com.example.movieexplorer.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("movie/top_rated")
    fun getMovies(): Call<MovieResponse>
}