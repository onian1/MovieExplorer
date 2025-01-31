package com.example.movieexplorer.network

import com.example.movieexplorer.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("movie/popular")
    fun getMovies(): Call<MovieResponse>
}