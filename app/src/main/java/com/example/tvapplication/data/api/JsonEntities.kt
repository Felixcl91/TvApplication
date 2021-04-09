package com.example.tvapplication.data.api

import com.example.tvapplication.data.model.Movie
import com.example.tvapplication.data.model.Recommendations
import com.google.gson.annotations.SerializedName

data class ListMovies(
    @SerializedName("response")
    val movies: ArrayList<Movie>
)

data class MovieProfile(
    val response: Movie
)

data class ListRecommendations(
        @SerializedName("response")
        val recom: ArrayList<Recommendations>
)