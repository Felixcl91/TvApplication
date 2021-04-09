package com.example.tvapplication.data.api

import com.example.tvapplication.data.model.ResponseRecommendations
import com.example.tvapplication.utils.Constants
import io.reactivex.Single
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    fun getMovies(): Single<ListMovies>

    @GET("rtv/v1/GetVideo")
    fun getMovie(
        @Query("client") json_id: String,
        @Query("external_id") id_ext: String

    ): Single<MovieProfile>
    //?client=json&type=all&subscription=false&filter_viewed_content=true&max_results=10&blend=ar_od_blend_2424video
    // &params=external_content_id:MFO_0000014003
    @GET("https://smarttv.orangetv.orange.es/stv/api/reco/v1/GetVideoRecommendationList")
    fun getRecommendations(
            @Query("client") one: String,
            @Query("type") two: String,
            @Query("subscription") three: String,
            @Query("filter_viewed_content") four: String,
            @Query("max_results") five: Int,
            @Query("blend") six: String,
            @Query("params") seven: String
    ): Single<ResponseRecommendations>


    companion object Factory {
        fun create(): ApiService {
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}