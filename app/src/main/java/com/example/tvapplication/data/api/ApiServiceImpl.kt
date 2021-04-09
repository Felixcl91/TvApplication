package com.example.tvapplication.data.api

import com.example.tvapplication.data.model.ResponseRecommendations
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImpl : ApiService {

    override fun getMovies(): Single<ListMovies> {
        return Rx2AndroidNetworking.get("https://smarttv.orangetv.orange.es/stv/api/rtv/v1/GetUnifiedList?client=json&statuses=published&definitions=SD;HD;4K&external_category_id=SED_3880&filter_empty_categories=true")
            .build()
            .getObjectSingle(ListMovies::class.java)
            //.getObjectListSingle(ListMovies::class.java)

    }

    override fun getMovie(json_id: String, id_ext: String): Single<MovieProfile> {
        return ApiService.create().getMovie(json_id, id_ext)
    }

    override fun getRecommendations(one: String, two: String, three: String, four: String, five: Int, six: String, seven: String): Single<ResponseRecommendations> {
        return ApiService.create().getRecommendations(one, two, three, four, five, six, seven)
    }


}