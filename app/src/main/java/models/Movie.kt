package com.example.movieexplorer.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String,
    val description: String
)
