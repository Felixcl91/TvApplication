package com.example.tvapplication.data.repository

import com.example.tvapplication.data.api.*
import com.example.tvapplication.data.model.ResponseRecommendations
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getMovies(): Single<ListMovies> {
        return apiHelper.getMovies()
    }

    fun getMovie(json_id: String, id_ext: String): Single<MovieProfile> {
        return apiHelper.getMovie(json_id, id_ext)
    }

    fun getRecommendations(one: String, two: String, three: String,
                           four: String, five: Int, six: String, seven: String): Single<ResponseRecommendations> {
        return apiHelper.getRecommendations(one, two, three, four, five, six, seven)
    }
}