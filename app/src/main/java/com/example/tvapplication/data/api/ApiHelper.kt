package com.example.tvapplication.data.api

import retrofit2.http.Query


class ApiHelper(private val apiService: ApiService) {

    fun getMovies() = apiService.getMovies()

    fun getMovie(json_id: String, id_ext: String) = apiService.getMovie(json_id, id_ext)

    fun getRecommendations(one: String, two: String, three: String,
                           four: String, five: Int, six: String, seven: String)
     = apiService.getRecommendations(one, two, three, four, five, six, seven)

}